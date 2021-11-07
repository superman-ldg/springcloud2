package com.ldg.cloud;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileNoValidator implements ConstraintValidator<MobileNo,String> {

    private boolean nullable;
    private boolean blankable;
    private String message;

    public MobileNoValidator(){}

    @Override
    public void initialize(MobileNo constraintAnnotation) {
        this.nullable=constraintAnnotation.nullable();
        this.blankable=constraintAnnotation.blankable();
        this.message=constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null){
            if(!this.nullable){
                //禁止默认消息返回
                context.disableDefaultConstraintViolation();
                //自定义返回消息
                context.buildConstraintViolationWithTemplate("手机号不能为空").addConstraintViolation();

                return false;
            }else{
                return true;
            }
        }else if(value.length()==0){
            if(!this.blankable){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("手机号不能为空白").addConstraintViolation();
                return  false;
            }else{
                return  true;
            }
        }else if(value.equals("18320426376")){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("手机号格式不对").addConstraintViolation();
            return false;
        }else {
            return true;
        }
    }
}
