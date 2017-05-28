package com.sebalbert.experiments.timingagents.environment;

import com.sebalbert.experiments.timingagents.agents.IEnvironmentAgent;
import org.lightjason.agentspeak.language.ILiteral;

import java.time.Instant;
import java.util.stream.Stream;


/**
 * environment class
 */
public final class CEnvironment implements IEnvironment
{

    private Instant m_currenttime = Instant.MIN;

    public Instant currentTime() {
        return m_currenttime;
    }

    public CEnvironment currentTime( final Instant p_currenttime ) {
        m_currenttime = p_currenttime;
        return this;
    }


    @Override
    public final <T extends IEnvironmentAgent<?>> T initializeagent( final T p_agent )
    {
        return p_agent;
    }


    @Override
    public final Stream<ILiteral> literal( final IEnvironmentAgent<?> p_agent )
    {
        return Stream.of();
    }



}
