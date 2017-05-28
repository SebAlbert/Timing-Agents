package com.sebalbert.experiments.timingagents.environment;

import com.sebalbert.experiments.timingagents.agents.IEnvironmentAgent;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.stream.Stream;


/**
 * environment class
 */
public final class CEnvironment implements IEnvironment
{


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
