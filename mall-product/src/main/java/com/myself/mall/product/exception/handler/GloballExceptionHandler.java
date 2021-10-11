package com.myself.mall.product.exception.handler;


import com.myself.common.exception.ExceptionCodeEnum;
import com.myself.common.utils.R;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class GloballExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        Map errorMap=new HashMap();
        bindingResult.getFieldErrors().forEach(item->{
          errorMap.put(item.getField(),item.getDefaultMessage());
        });
        return R.error(ExceptionCodeEnum.DATA_VALID_EXCEPTION,errorMap);
    }

    @ExceptionHandler(value = Exception.class)
    public R handleException(Exception ex){
        return R.error(ExceptionCodeEnum.DATA_VALID_EXCEPTION);
    }
}
