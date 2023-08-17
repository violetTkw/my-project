package com.example.myprojectbackend.controller.exception;

import com.example.myprojectbackend.entity.RestBean;
import jakarta.validation.ValidationException;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName:ValidationController
 * Package:com.example.myprojectbackend.controller.exception
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/16 23:01
 * @Version 1.0
 */
@Slf4j
//处理全局异常
@RestControllerAdvice
public class ValidationController {

    @ExceptionHandler(ValidationException.class)
    public RestBean<Void> validateException(ValidationException exception){
        log.warn("Reslove [{}: {}]",exception.getClass().getName(),exception.getMessage());
        return RestBean.failure(400,"请求参数有误");
    }
}
