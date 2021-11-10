package com.myself.mall.product.exception.handler;


import com.myself.common.exception.ExceptionCodeEnum;
import com.myself.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GloballExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("error",e.getMessage());
        e.printStackTrace();
        BindingResult bindingResult = e.getBindingResult();
        Map<String,String> errorMap=new HashMap<>();
        bindingResult.getFieldErrors().forEach(item->{
          errorMap.put(item.getField(),item.getDefaultMessage());
        });
        Map<String,Object> error=new HashMap<>();
        error.put("msg",ExceptionCodeEnum.DATA_VALID_EXCEPTION.getMsg());
        error.put("data",errorMap);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handleException(Exception ex){
        log.error("error",ex.getMessage());
        Map<String,Object> error=new HashMap<>();
        error.put("msg",ExceptionCodeEnum.DATA_VALID_EXCEPTION.getMsg());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
