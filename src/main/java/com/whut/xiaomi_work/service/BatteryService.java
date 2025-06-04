package com.whut.xiaomi_work.service;

import com.whut.xiaomi_work.entity.AlertInfo;
import com.whut.xiaomi_work.entity.Battery;
import com.whut.xiaomi_work.entity.Car;

import java.util.List;
import java.util.Map;

public interface BatteryService {
    boolean insertBattery(Car car, Battery battery);

    boolean deleteBattery(Long batteryId, Long id);

    boolean updateBattery(Battery battery);

    Battery selectBattery(Long id);

    List<Map> alertMessage(List<AlertInfo> list);
}
