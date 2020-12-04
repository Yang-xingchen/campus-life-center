package campuslifecenter.usercenter.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

public class Response<T> implements Serializable {

    @ApiModelProperty("代码")
    private int code;
    @ApiModelProperty("是否成功")
    private boolean success;
    @ApiModelProperty("消息")
    private String message;
    @ApiModelProperty("数据")
    private T data;

    public int getCode() {
        return code;
    }

    public Response<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public Response<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Response<?> response = (Response<?>) o;
        return success == response.success &&
                Objects.equals(message, response.message) &&
                Objects.equals(data, response.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, data);
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
