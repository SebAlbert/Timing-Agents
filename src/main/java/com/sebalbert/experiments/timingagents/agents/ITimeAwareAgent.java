package com.sebalbert.experiments.timingagents.agents;

import com.sebalbert.experiments.timingagents.environment.IEnvironment;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * Extension of LightJason agents to include the notion of simulated time
 * Simulation will run in "Bullet Time (tm)" / "Frozen Reality":
 * - one point in time can have many cycles
 * - only when all agents have become passive (no running plans, no triggers) can the time advance
 * - agents have lists of scheduled triggers for the "future" (w.r.t. simulated time)
 * - when time advances, it advances to the minimum point in time over all scheduled triggers in all agents
 * - before the scheduled triggers for a given point in time are injected into the respective agents,
 *   all agents are run with the belief-trigger "+simtime/elapsed( T )", with T being the amount of time advance
 *   that just happened, so they can calculate their new state, e.g. according to continuous physical processes,
 *   before any interaction happens (the plan for +simtime/elapsed( T ) should therefore not call immediate triggers on
 *   other agents, neither directly nor indirectly through other immediately triggered plans)
 *
 * @author Sebastian Albert
 */
@IAgentAction
public abstract class ITimeAwareAgent<T extends IEnvironmentAgent<?>> extends IEnvironmentAgent<T> {


    private final PriorityQueue<ImmutablePair<Instant, ITrigger>> m_scheduled = new PriorityQueue<>(
            Comparator.comparing(ImmutablePair::getLeft));

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment   environment reference
     * @param p_name          agent name
     */
    protected ITimeAwareAgent(IAgentConfiguration<IEnvironmentAgent<T>> p_configuration, IEnvironment p_environment, String p_name) {
        super(p_configuration, p_environment, p_name);
    }

    @IAgentActionFilter
    @IAgentActionName( name = "simtime/current" )
    protected ZonedDateTime currentTime() {
        return m_environment.currentTime().atZone( ZoneId.systemDefault() );
    }

    @IAgentActionFilter
    @IAgentActionName( name = "schedule/addgoal" )
    protected void schedule( ZonedDateTime p_datetime, String p_literal ) throws Exception {
        final Instant l_instant = p_datetime == null ? null : p_datetime.toInstant();
        final ILiteral l_literal = CLiteral.parse(p_literal);
        final ITrigger l_trigger = CTrigger.from( CTrigger.EType.ADDGOAL, l_literal );
        if (l_instant != null && l_instant.compareTo( m_environment.currentTime() ) > 0) {
            synchronized (m_scheduled) {
                m_scheduled.add( new ImmutablePair<>(l_instant, l_trigger ) );
            }
        } else {
            this.trigger( l_trigger, false );
        }
    }

    @IAgentActionFilter
    @IAgentActionName( name = "schedule/length" )
    protected int scheduleLength() {
        synchronized (m_scheduled) {
            return m_scheduled.size();
        }
    }


    /**
     * Get the next time this agent will become active
     * @return current time if agent is still active, next scheduled trigger time instant, or Instant.MAX if none
     */
    public Instant nextActivation() {
        if (!runningplans().isEmpty()) return m_environment.currentTime();
        if (m_scheduled.size() < 1) return Instant.MAX;
        return m_scheduled.peek().getLeft();
    }

}
