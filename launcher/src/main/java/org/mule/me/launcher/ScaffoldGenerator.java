package org.mule.me.launcher;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.Response;
import org.raml.parser.visitor.RamlDocumentBuilder;

public class ScaffoldGenerator
{

    public void generateScaffold(File ramlFile) throws FileNotFoundException
    {
        final Raml raml = new RamlDocumentBuilder().build(new FileInputStream(ramlFile), ramlFile.getParentFile().getAbsolutePath());
        final GroovyScaffolding groovyScaffolding = new GroovyScaffolding();
        groovyScaffolding.init();
        groovyScaffolding.api(ramlFile.getName());
        addResourceMap(groovyScaffolding, raml.getResources());
        groovyScaffolding.end();
        groovyScaffolding.generate(new File(ramlFile.getParent(), FilenameUtils.getBaseName(ramlFile.getName()) + ".groovy"));

    }

    private void addResourceMap(GroovyScaffolding groovyScaffolding, Map<String, Resource> resources)
    {
        for (Map.Entry<String, Resource> resourceEntry : resources.entrySet())
        {
            Resource resource = resourceEntry.getValue();
            addResource(groovyScaffolding, resource);
            Map<String, Resource> childrenResource = resource.getResources();
            if (childrenResource != null)
            {
                addResourceMap(groovyScaffolding, childrenResource);
            }
        }
    }

    private void addResource(GroovyScaffolding groovyScaffolding, Resource resource)
    {
        Map<ActionType, Action> actions = resource.getActions();
        for (Map.Entry<ActionType, Action> actionTypeActionEntry : actions.entrySet())
        {
            final String description = actionTypeActionEntry.getValue().getDescription();
            final String resourceName = resource.getUri();
            final ActionType actionType = actionTypeActionEntry.getKey();
            groovyScaffolding.declareResourceAction(description, resourceName, actionType);
        }
    }

}
