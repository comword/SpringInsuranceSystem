package org.gtdev.webapps.iaatraesamhsaat.empadmport;
import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPlugin;
import org.pf4j.spring.SpringPluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EmployeeAdminPlugin extends SpringPlugin {
    private Logger Log = LoggerFactory.getLogger(EmployeeAdminPlugin.class);

    public EmployeeAdminPlugin (PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    protected ApplicationContext createApplicationContext() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        SpringPluginManager pluginManager = (SpringPluginManager) getWrapper().getPluginManager();
        applicationContext.setParent(pluginManager.getApplicationContext());
        applicationContext.setClassLoader(getWrapper().getPluginClassLoader());
        //applicationContext.register(CustomerLRController.class);
        applicationContext.refresh();
        return applicationContext;
    }
}
