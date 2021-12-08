package com.ldg.cloud.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     *  AUTO(0),自增
     *  NONE(1),没有主键
     *  INPUT(2),手动
     *  ASSIGN_ID(3),
     *  ASSIGN_UUID(4),
     *  ID_WORKER(3), 全局唯一id，默认
     *  ID_WORKER_STR(3), 字符串表示
      * UUID(4); uuid
     */
    private Long id;
    private String name;
    private Integer age;
    private String email;
    @TableField(fill = FieldFill.INSERT)
    private Date createtime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;
    @Version
    private int version;
    @TableLogic
    private int deleted;
}

