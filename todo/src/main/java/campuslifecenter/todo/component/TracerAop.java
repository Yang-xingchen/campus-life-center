package campuslifecenter.todo.component;

import brave.ScopedSpan;
import brave.Tracer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class TracerAop {

    @Autowired
    private Tracer tracer;

    @Around("execution(public * *..*Impl.*(..))")
    public Object tracer(ProceedingJoinPoint point) throws Throwable{
        String name = point.getSignature().getDeclaringType().getSimpleName() +
                "." + point.getSignature().getName();
        if (point.getArgs().length == 1) {
            name += ": " + point.getArgs()[0];
        }
        ScopedSpan span = tracer.startScopedSpan(name);
        span.annotate("start");
        span.tag("args", Arrays.toString(point.getArgs()));
        try {
            return point.proceed();
        } finally {
            span.annotate("finish");
            span.finish();
        }
    }

}
