package com.example.demo.pojo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ApiModel 实体类添加说明
 * */
@Api
@ApiModel("用户实体类")
@NoArgsConstructor
@Data
public class User {
    @ApiModelProperty("用户名")
    public String username;
    @ApiModelProperty("用户密码")
    public String password;
}
