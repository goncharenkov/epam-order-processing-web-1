package epam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceHealth implements HealthIndicator {

    @Autowired
    private ServiceProperties configuration;

    @Override
    public Health health() {
        return Health.up().withDetail("details", "{ 'profile' : '"
                + this.configuration.getName() + "' }").status("OK").build();
    }

}
