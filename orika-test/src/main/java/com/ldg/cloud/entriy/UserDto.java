package com.ldg.cloud.entriy;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserDto {
    private String id;
    private String name;
    private String userName;
    private Integer age;
    List<User> userList;
    List<Role> roleList;
}