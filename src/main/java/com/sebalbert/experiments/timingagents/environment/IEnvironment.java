package com.sebalbert.experiments.timingagents.environment;

import com.sebalbert.experiments.timingagents.agents.IEnvironmentAgent;
import org.lightjason.agentspeak.language.ILiteral;

import java.time.Instant;
import java.util.stream.Stream;


/**
 * interface to represent
 * an environment
 */
public interface IEnvironment
{


    /**
     * get the current time instant
     * @return the current time instant
     */
    Instant currentTime();


    /**
     * set the current time
     * @param p_currenttime the new current time
     * @return this environment (for chaining)
     */
    IEnvironment currentTime( final Instant p_currenttime );


    /**
     * is called if an agent is generated
     * (before the first agent cycle)
     *
     * @param p_agent agent
     * @return agent
     * @tparam T agent type
     */
    <T extends IEnvironmentAgent<?>> T initializeagent( final T p_agent );


    /**
     * returns the literal structure of
     * the environment based on the calling agent
     *
     * @param p_agent calling agent
     * @return literal stream
     */
    Stream<ILiteral> literal( final IEnvironmentAgent<?> p_agent );



}
