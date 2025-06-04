package com.whut.xiaomi_work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Battery {
    private Long id;
    private Double health;
    private String type;
    private Double mx;  // 改为小写
    private Double mi;  // 改为小写
    private Double ix;  // 改为小写
    private Double ii;  // 改为小写
}
