package eon.hg.fap.core.body;

import lombok.*;

import java.io.Serializable;

/**
 * 统一前端返回对象
 * @author eonook
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"success","data"})
public class ResultBody<T> implements Serializable {
    //设置统一的返回码
    @NonNull private Integer code;
    private Boolean success;
    private String msg;
    private T data;

    public ResultBody(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    /**
     * 业务处理成功，专用于数据增删改等数据处理
     * @return
     */
    public static <T> ResultBody<T> success() {
        ResultBody<T> resultBody = new ResultBody<T>(ResultCode.SUCCESS);
        resultBody.setSuccess(true);
        return resultBody;
    }

    public static <T> ResultBody<T> success(String message) {
        ResultBody<T> resultBody = new ResultBody<T>(ResultCode.SUCCESS.getCode());
        resultBody.setSuccess(true);
        resultBody.setMsg(message);
        return resultBody;
    }

    /**
     * 业务处理成功，并返回内容
     * @param data
     * @return
     */
    public static <T> ResultBody<T> success(T data) {
        ResultBody<T> resultBody = new ResultBody<T>(ResultCode.SUCCESS.getCode());
        resultBody.setSuccess(true);
        return resultBody.addObject(data);
    }

    /**
     * 业务数据失败
     * @return
     */
    public static <T> ResultBody<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 业务数据失败，返回预定义的错误类型
     * @param resultCode
     * @return
     */
    public static <T> ResultBody<T> failed(ResultCode resultCode) {
        ResultBody<T> resultBody = new ResultBody<T>(resultCode);
        resultBody.setSuccess(false);
        return resultBody;
    }

    /**
     * 业务数据失败，返回自定义的错误类型，错误提示自行提供
     * @param reason
     * @return
     */
    public static <T> ResultBody<T> failed(String reason) {
        ResultBody<T> resultBody = new ResultBody<T>(ResultCode.CUSTOMIZE_FAIL.getCode());
        resultBody.setSuccess(false);
        resultBody.setMsg(reason);
        return resultBody;
    }

    /**
     * 业务数据失败，返回自定义的错误类型，错误提示自行提供
     * @param reason
     * @return
     */
    public static <T> ResultBody<T> failed(Integer code, String reason) {
        ResultBody<T> resultBody = new ResultBody<T>(code);
        resultBody.setSuccess(false);
        resultBody.setMsg(reason);
        return resultBody;
    }

    public ResultBody<T> addObject(T o) {
        setData(o);
        return this;
    }

    public ResultBody<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
