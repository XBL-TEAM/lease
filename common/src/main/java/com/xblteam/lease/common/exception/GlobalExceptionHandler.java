package com.xblteam.lease.common.exception;

import com.xblteam.lease.common.exception.LeaseException.LeaseException;
import com.xblteam.lease.common.result.Result;
import com.xblteam.lease.common.result.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 最终异常处理
     * @param e 异常对象
     * @return 异常信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e) {
        return Result.build(e.getMessage(), ResultCodeEnum.FAIL);
    }

    @ExceptionHandler(LeaseException.class)
    @ResponseBody
    public Result error(LeaseException e){
        e.printStackTrace();
        return Result.fail(e.getCode(), e.getMessage());
    }

}
