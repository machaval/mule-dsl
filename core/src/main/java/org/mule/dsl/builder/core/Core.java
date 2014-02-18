package org.mule.dsl.builder.core;


import groovy.lang.Closure;
import org.mule.api.processor.MessageProcessor;

public class Core
{

    public static LoggerBuilder log(String message)
    {
        return new LoggerBuilder(message);
    }

    public static ForeachBuilder foreach()
    {
        return new ForeachBuilder();
    }

    public static FlowBuilder flow(String name)
    {
        return new FlowBuilderImpl(name);
    }

    public static InboundEndpointBuilder endpoint(String address)
    {
        return new InboundEndpointBuilder(address);
    }

    //Todo add Poll and Choice

    public static <T extends MessageProcessor> CustomMessageProcessorBuilder<T> invoke(Class<T> clazz)
    {
        return new CustomMessageProcessorBuilderImpl<T>(clazz);
    }

    public static GroovyBuilder call(Closure closure){
        return new GroovyBuilder(closure);
    }

}
