package org.mule.dsl.builder.apikit;

import org.mule.dsl.builder.core.Core;
import org.mule.dsl.builder.core.PrivateFlowBuilder;

import org.raml.model.ActionType;


public class Apikit
{

    public static RestRouterBuilder api(String ramlPath)
    {
        return new RestRouterBuilderImpl(ramlPath);
    }

    public static PrivateFlowBuilder request(String resource, ActionType action)
    {
        return Core.flow(action.name().toLowerCase() + ":" + resource);
    }


}