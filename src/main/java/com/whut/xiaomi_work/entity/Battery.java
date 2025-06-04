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
    private Double mx;
    private Double mi;
    private Double ix;
    private Double ii;
}
