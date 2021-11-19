package com.ldg.cloud.entriy;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Role {
    private Integer id;
    private String name;
}

