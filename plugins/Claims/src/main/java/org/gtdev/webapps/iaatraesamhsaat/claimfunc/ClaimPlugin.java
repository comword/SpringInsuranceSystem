package org.gtdev.webapps.iaatraesamhsaat.claimfunc;
import org.gtdev.webapps.iaatraesamhsaat.api.IController;
import org.pf4j.Extension;
import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPlugin;
import org.pf4j.spring.SpringPluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class ClaimPlugin extends SpringPlugin {

    public ClaimPlugin (PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    protected ApplicationContext createApplicationContext() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        SpringPluginManager pluginManager = (SpringPluginManager) getWrapper().getPluginManager();
        applicationContext.setParent(pluginManager.getApplicationContext());
        applicationContext.setId(pluginManager.getApplicationContext().getId() + ":ClaimsPlugin");
        applicationContext.setClassLoader(getWrapper().getPluginClassLoader());
        //applicationContext.register(CustomerLRController.class);
        applicationContext.refresh();
        return applicationContext;
    }

    @Extension(ordinal = 1)
    public static class LRControllers implements IController {

        @Override
        public List<?> reactiveRoutes() {
            return new ArrayList<>();
        }

        @Override
        public List<Object> mvcControllers() {
            return new ArrayList<Object>() {{
                add(new NewClaimController());
                add(new ClaimResponseController());
            }};
        }
    }
}
