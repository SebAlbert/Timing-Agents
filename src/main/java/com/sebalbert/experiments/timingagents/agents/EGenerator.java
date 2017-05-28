package com.sebalbert.experiments.timingagents.agents;

import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.agent.IAgent;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import com.sebalbert.experiments.timingagents.environment.IEnvironment;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.Collectors;


/**
 * enum of agent generators
 */
public enum EGenerator
{
    DEFAULT;

    /**
     * creates a generator instance
     *
     * @param p_stream asl input stream
     * @param p_environment environment reference
     * @param p_defaultaction stream with default actions
     * @param p_agents map with agents
     * @return generator
     * @throws Exception is thrown on any error
     */
    public final IBaseAgentGenerator<?> generate( final InputStream p_stream, final IEnvironment p_environment,
                                                  final Stream<IAction> p_defaultaction, final Map<String, IAgent<?>> p_agents 
    ) throws Exception
    {
        switch ( this )
        {
            case DEFAULT : return new CDefaultAgent.CGenerator( p_stream, p_defaultaction, p_environment, p_agents );

            default :
                throw new RuntimeException( MessageFormat.format( "generator [{0}] not exists", this ) );
        }
    }

    /**
     * returns a generate enum on
     * case-insensitive ignore string name
     *
     * @param p_name string name
     * @return generator enum
     */
    public static EGenerator from( final String p_name )
    {
        return EGenerator.valueOf( p_name.toUpperCase( Locale.ROOT ) );
    }

    /**
     * returns a string with a
     * comma-separated list of enum elements
     *
     * @return string list
     */
    public static String list()
    {
        return Arrays.stream( EGenerator.values() )
                     .map( i -> i.name() )
                     .map( i -> i.toLowerCase( Locale.ROOT ) )
                     .collect( Collectors.joining( ", ") );
    }

}
