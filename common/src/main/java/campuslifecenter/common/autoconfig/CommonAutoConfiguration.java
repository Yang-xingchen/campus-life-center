package campuslifecenter.common.autoconfig;

import brave.Tracer;
import campuslifecenter.common.component.TracerUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.sleuth.zipkin2.ZipkinAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@AutoConfigureAfter(ZipkinAutoConfiguration.class)
public class CommonAutoConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Bean
    public TracerUtil tracerUtil(Tracer tracer) {
        return new TracerUtil(tracer);
    }

}
