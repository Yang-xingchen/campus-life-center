package campuslifecenter.usercenter.actuator;

import campuslifecenter.usercenter.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class AccountActuator extends AbstractHealthIndicator {
    @Autowired
    private AccountService accountService;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.up()
                .withDetail("account", accountService.actuatorAccount());
    }
}
