package com.smileshark.common;

import com.smileshark.code.ResultCode;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Result {
    private int code;
    private boolean success;
    private String msg;
    private Object data;

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public Result setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    public static Result success() {
        return new Result() {{
            setSuccess(true);
            setCode(ResultCode.SUCCESS);
            setMsg("success");
        }};
    }

    public static Result success(String msg) {
        return new Result() {{
            setSuccess(true);
            setCode(ResultCode.SUCCESS);
            setMsg(msg);
        }};
    }

    public static Result success(String msg,Object data) {
        return new Result() {{
            setSuccess(true);
            setCode(ResultCode.SUCCESS);
            setMsg(msg);
            setData(data);
        }};
    }
    public static Result success(Object data) {
        return new Result() {{
            setSuccess(true);
            setCode(ResultCode.SUCCESS);
            setMsg("success");
            setData(data);
        }};
    }

    public static Result error() {
        return new Result() {{
            setSuccess(false);
            setCode(ResultCode.ERROR);
            setMsg("error");
        }};
    }
}
