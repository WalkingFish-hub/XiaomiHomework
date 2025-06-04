package com.whut.xiaomi_work.entity;

import lombok.Data;

@Data
public class Rule {
    private Long id;
    private Long warnId;
    private String name;
    private String batteryType;
    private Double lowerValue;
    private Double upperValue;
    private Integer alertLevel;
}
