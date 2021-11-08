package com.ldg.cloud.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class User {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "年龄")
    private int age;

    private int status;

}

