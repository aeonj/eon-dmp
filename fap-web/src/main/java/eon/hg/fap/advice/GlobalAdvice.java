package eon.hg.fap.advice;

import eon.hg.fap.core.body.ResultBody;
import eon.hg.fap.core.exception.ResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 说明：Controller切面，捕获全局异常并返回统一错误码，
 */
@ControllerAdvice
@ResponseBody
public class GlobalAdvice {
    public static Logger logger = LoggerFactory.getLogger(GlobalAdvice.class);

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ResultException.class)
    public ResultBody resultException(ResultException exception) {
        logger.error(exception.getMsg(),exception);
        return exception.getBody();
    }

    /**
     * 说明：400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultBody handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("json 参数解析失败", e);
        return  ResultBody.failed("请求参数解析失败!");
    }


    /**
     * 405 - Method Not Allowed
     */
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultBody handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法", e);
        return  ResultBody.failed(HttpStatus.METHOD_NOT_ALLOWED.value(),"请求方法错误!");
    }

    /**
     * 415 - Unsupported Media Type
     */
//    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResultBody handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.error("不支持当前媒体类型", e);
        return  ResultBody.failed(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),"不支持当前媒体类型!");
    }

}
