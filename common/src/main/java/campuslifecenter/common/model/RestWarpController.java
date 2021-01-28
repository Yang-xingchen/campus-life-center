package campuslifecenter.common.model;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ResponseBody
@Controller
public @interface RestWarpController {

    @AliasFor(annotation = Controller.class)
    String value() default "";

}
