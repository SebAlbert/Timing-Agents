package com.sebalbert.experiments.timingagents.agents;

import com.sebalbert.experiments.timingagents.environment.IEnvironment;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;


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


    private Instant m_nextactivation = Instant.MAX;
    private ZoneId m_timezone = ZoneId.systemDefault();

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
        return m_environment.currentTime().atZone( m_timezone );
    }

    @IAgentActionFilter
    @IAgentActionName( name = "nextactivation/get" )
    protected ZonedDateTime getNextActivation() {
        return m_nextactivation.atZone( m_timezone );
    }

    @IAgentActionFilter
    @IAgentActionName( name = "nextactivation/set" )
    protected void setNextActivation( ZonedDateTime p_datetime ) throws Exception {
        if ( p_datetime == null ) throw new IllegalArgumentException( "next activation time must not be null" );
        final Instant l_instant = p_datetime.toInstant();
        if ( l_instant.compareTo( m_environment.currentTime() ) <= 0 )
            throw new IllegalArgumentException( "next activation time must be in the future" );
        m_nextactivation = l_instant;
    }

    /**
     * Get the next time this agent will become active
     * @return current time if agent is still active, next scheduled trigger time instant, or Instant.MAX if none
     */
    public Instant nextActivation() {
        if (!runningplans().isEmpty()) return m_environment.currentTime();
        return m_nextactivation;
    }

}
