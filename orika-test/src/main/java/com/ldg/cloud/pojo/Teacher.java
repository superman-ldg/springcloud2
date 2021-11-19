package com.ldg.cloud.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class Teacher implements Serializable {
    private int employeeId;
    private String name;
    private int age;
    private String card;
    private float salary;

}
