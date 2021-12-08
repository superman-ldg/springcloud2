package com.ldg.cloud.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@ToString
public class MyEntriy implements Serializable {
    private int id;
    private String context;
}
