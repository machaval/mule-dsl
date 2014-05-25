package org.mule.module.core.builder;

import org.mule.api.MuleContext;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.config.dsl.Builder;
import org.mule.module.core.processor.WhenMessageProcessor;


//when(bla)
//  .then(log()).then(echo())
//.otherwise().then(log())
public class WhenBuilder implements MessageProcessorBuilder<WhenMessageProcessor>
{

    private Filter condition;
    private ChainBuilder whenMessageProcessors;
    private ChainBuilder otherwiseMessageProcessors;
    private boolean otherwise = false;

    public WhenBuilder(Filter condition)
    {
        this.condition = condition;
        this.whenMessageProcessors = new ChainBuilder();
        this.otherwiseMessageProcessors = new ChainBuilder();
    }

    public WhenBuilder then(Builder<MessageProcessor>... messageProcessorBuilders)
    {
        if (otherwise)
        {
            otherwiseMessageProcessors.chain(messageProcessorBuilders);
        }
        else
        {
            whenMessageProcessors.chain(messageProcessorBuilders);
        }
        return this;
    }

    public WhenBuilder otherwise(Builder<MessageProcessor>... messageProcessorBuilders)
    {
        otherwise = true;
        otherwiseMessageProcessors.chain(messageProcessorBuilders);
        return this;
    }

    @Override
    public WhenMessageProcessor create(MuleContext muleContext)
    {
        final WhenMessageProcessor result = new WhenMessageProcessor(condition);
        result.setWhen(whenMessageProcessors.create(muleContext));
        result.setOtherWise(otherwiseMessageProcessors.create(muleContext));
        return result;
    }

}
