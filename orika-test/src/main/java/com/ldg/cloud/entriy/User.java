package com.ldg.cloud.entriy;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class User {
    private String id;
    private String name;
    private Integer age;
    List<User> userList;
    List<Role> roles;
}
