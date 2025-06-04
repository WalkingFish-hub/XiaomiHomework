package com.whut.xiaomi_work.service.Impl;

import com.whut.xiaomi_work.entity.Battery;
import com.whut.xiaomi_work.entity.Car;
import com.whut.xiaomi_work.mapper.BatteryMapper;
import com.whut.xiaomi_work.mapper.CarMapper;
import com.whut.xiaomi_work.service.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatteryServiceImpl implements BatteryService {
    @Autowired
    private BatteryMapper batteryMapper;

    @Autowired
    private CarMapper carMapper;

    @Override
    public boolean insertBattery(Car car, Battery battery) {
        int insertCar=carMapper.insertCar(car.getVid(),car.getCid(),car.getBatteryId(),car.getTotalMileage());
        int insertBattery=batteryMapper.insertBattery(battery.getId(),battery.getHealth(),battery.getType(),battery.getMx(),battery.getMi(),battery.getIx(),battery.getIi());
        return (insertCar==1 && insertBattery==1);
    }
}
