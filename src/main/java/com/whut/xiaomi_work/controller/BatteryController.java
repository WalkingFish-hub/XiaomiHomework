package com.whut.xiaomi_work.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whut.xiaomi_work.entity.AlertInfo;
import com.whut.xiaomi_work.entity.Battery;
import com.whut.xiaomi_work.entity.Car;
import com.whut.xiaomi_work.entity.Response;
import com.whut.xiaomi_work.service.BatteryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController()
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
            return batteryService.insertBattery(car,battery)?Response.success():Response.error("插入出错");
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public Response deleteBattery(@RequestBody Map<String, Long> map){
        log.info("删除电池信息{}",map);
        try{
            Long batteryId = map.get("batteryId");
            Long id = map.get("id");
            return batteryService.deleteBattery(batteryId,id)?Response.success():Response.error("删除出错");
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Response updateBattery(@RequestBody Map<String, Map<String,String>> map){
        log.info("修改电池信息{}",map);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String,String> batteryInfo = map.get("battery");
            Battery battery = objectMapper.convertValue(batteryInfo, Battery.class);
            return batteryService.updateBattery(battery)?Response.success():Response.error("修改出错");
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }

    @GetMapping("/select")
    public Response selectBattery(@RequestBody Map<String, Long> map){
        log.info("查询电池信息{}",map);
        try{
            Long id = map.get("id");
            Battery battery = batteryService.selectBattery(id);
            return battery == null ? Response.error("电池信息不存在") : Response.success(battery);
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }

    @PostMapping("/alert")
    private Response alertMessage(@RequestBody Map<String, List<AlertInfo>> map){
        log.info("预警信息{}",map);
        try{
            List<AlertInfo> list = map.get("data");
            List<Map> res = batteryService.alertMessage(list);
            return res == null ? Response.error("预警出错") : Response.success(res);
        }catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }
}
