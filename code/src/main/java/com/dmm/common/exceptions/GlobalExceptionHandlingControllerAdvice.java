package com.dmm.common.exceptions;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dmm.common.core.BackResult;
import com.dmm.common.constants.ResponseCodeEnum;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by cremin on 2017/8/18.
 */
@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {

    protected Logger logger  = LoggerFactory.getLogger(getClass());



    /**
     * 处理数据库异常类
     *
     * @param exception
     * @return
     */
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler({ DataIntegrityViolationException.class,SQLException.class,DataAccessException.class})
    @ResponseBody
    public BackResult<String> handleDatabaseError(Exception exception) {

        logger.error("Request raised " + exception.getClass().getSimpleName());


        BackResult<String> backResult = new BackResult<>();
        backResult.setCode(ResponseCodeEnum.BACK_CODE_FAIL.value);

        Throwable cause = exception.getCause();
        if (cause instanceof SQLException){
            SQLException sqlEx= (SQLException) cause;
            backResult.setExceptions(String.format("msg:%s,SQLState:%s,cause:%s",sqlEx.getMessage(),sqlEx.getSQLState(),cause.getClass().getSimpleName()));
        }else{
            backResult.setExceptions(cause.getLocalizedMessage());
        }

        return backResult;
    }


    /**
     * 处理数据格式转换异常
     * @param req
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({HttpMessageNotReadableException.class,JsonMappingException.class})
    @ResponseBody
    BackResult<String> handleHttpMessageNotReadableException(HttpServletRequest req, Exception ex) {

        BackResult<String> backResult = new BackResult<>();
        backResult.setCode(ResponseCodeEnum.BACK_CODE_FAIL.value);

        Throwable cause = ex.getCause();
        if (cause instanceof JsonMappingException){
            JsonMappingException jsonEx= (JsonMappingException) cause;
            backResult.setExceptions(jsonEx.getLocalizedMessage());
        }else {
            backResult.setExceptions(ex.getMessage());
        }

        logger.error("Request raised " + ex.getClass().getSimpleName());

        return backResult;
    }

    /**
     * 处理 找不到 相关异常
     * @param req
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    BackResult<String> handleBadRequest(HttpServletRequest req, Exception ex) {

        BackResult<String> backResult = new BackResult<>();
        backResult.setCode(ResponseCodeEnum.BACK_CODE_FAIL.value);
        backResult.setExceptions(ex.getMessage());

        logger.error("Request raised " + ex.getClass().getSimpleName());
        return backResult;
    }


    /**
     *
     * 处理其他异常
     * @param req
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody BackResult<String>  handleException(HttpServletRequest req, Exception ex) {

        BackResult<String> backResult = new BackResult<>();
        backResult.setCode(ResponseCodeEnum.BACK_CODE_FAIL.value);
        backResult.setExceptions(ex.getMessage());

        logger.error("Request raised " + ex.getClass().getSimpleName());

        return backResult;
    }

}
