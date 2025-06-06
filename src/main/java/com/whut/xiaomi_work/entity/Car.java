package com.whut.xiaomi_work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private String vid;
    private Long id;
    private Long batteryId;
    private Long totalMileage;
}
