package com.whut.xiaomi_work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertInfo {
    private Long carId;
    private Long warnId;//1为电压差报警，2为电流差报警
    private String signal;
}
