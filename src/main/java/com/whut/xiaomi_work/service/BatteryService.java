package com.whut.xiaomi_work.service;

import com.whut.xiaomi_work.entity.Battery;
import com.whut.xiaomi_work.entity.Car;

public interface BatteryService {
    boolean insertBattery(Car car, Battery battery);
}
