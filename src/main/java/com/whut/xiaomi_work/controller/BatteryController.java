package com.whut.xiaomi_work.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.whut.xiaomi_work.entity.Battery;
import com.whut.xiaomi_work.entity.Car;
import com.whut.xiaomi_work.entity.Response;
import com.whut.xiaomi_work.service.BatteryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("/battery")
@Slf4j
public class BatteryController {
    @Autowired
    private BatteryService batteryService;

    @PostMapping("/insert")
    public Response insertBattery(@RequestBody Map<String, Map<String,String>> map){
        log.info("插入电池信息{}", map);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String,String> carInfo = map.get("car");
            Map<String,String> batteryInfo = map.get("battery");
            Car car = objectMapper.convertValue(carInfo,Car.class);
            Battery battery = objectMapper.convertValue(batteryInfo, Battery.class);
            System.out.println(car.toString()+battery.toString());
            if(batteryService.insertBattery(car,battery)) {
                return Response.success();
            } else {
                return Response.error("插入出错");
            }
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }
}
