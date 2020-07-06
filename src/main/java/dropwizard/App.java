package dropwizard;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
//import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<ServerConfiguration> {

    private GuiceBundle<ServerConfiguration> guiceBundle;

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void initialize(Bootstrap<ServerConfiguration> b) {
        guiceBundle = GuiceBundle.<ServerConfiguration>newBuilder()
                .addModule(new ServerModule())
                .setConfigClass(ServerConfiguration.class)
                .enableAutoConfig(getClass().getPackage().getName())
                .build();

        b.addBundle(guiceBundle);
    }

    @Override
    public void run(ServerConfiguration c, Environment e) throws Exception {
//        LOGGER.info("Registering REST resources");
//        e.jersey().register(new EmployeeRESTController());
        final FilterRegistration.Dynamic cors =
                e.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}