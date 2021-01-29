package campuslifecenter.common.component;

import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.Response;
import campuslifecenter.common.model.RestWarpController;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.*;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice, Ordered {

    private static final Class<? extends Annotation> RESPONSE_WARP_CLASS = RestWarpController.class;

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), RESPONSE_WARP_CLASS)
                || returnType.hasMethodAnnotation(RESPONSE_WARP_CLASS);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Response) {
            return body;
        }
        return Response.withData(body);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Response<?>> exceptionHandler(Throwable throwable) {
        Response<?> response;
        throwable.printStackTrace();
        if (throwable instanceof ResponseException) {
            ResponseException e = (ResponseException) throwable;
            response = new Response<>()
                    .setSuccess(false)
                    .setMessage(e.getMessage())
                    .setCode(e.getCode());
        } else {
            response = new Response<>()
                    .setSuccess(false)
                    .setMessage(throwable.getMessage())
                    .setCode(200);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
