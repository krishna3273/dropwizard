package dropwizard;

import io.dropwizard.Configuration;

public class ServerConfiguration extends Configuration {
    private String message;
    public String getMessage() {
        return message;
    }
}
