package eon.hg.fap.advice;

import eon.hg.fap.core.body.ResultBody;
import eon.hg.fap.core.exception.ResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalAdvice {

    @ResponseBody
    @ExceptionHandler(ResultException.class)
    public ResponseEntity<ResultBody> ResultErrorHandler(ResultException exception) {
        if (exception.getBody()==null) {
            return new ResponseEntity(ResultBody.failed(exception.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity(exception.getBody(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
