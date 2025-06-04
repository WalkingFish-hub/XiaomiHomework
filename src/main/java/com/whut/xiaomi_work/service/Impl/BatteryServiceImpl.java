package com.whut.xiaomi_work.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whut.xiaomi_work.entity.AlertInfo;
import com.whut.xiaomi_work.entity.Battery;
import com.whut.xiaomi_work.entity.Car;
import com.whut.xiaomi_work.entity.Rule;
import com.whut.xiaomi_work.mapper.BatteryMapper;
import com.whut.xiaomi_work.mapper.CarMapper;
import com.whut.xiaomi_work.mapper.RuleMapper;
import com.whut.xiaomi_work.service.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class BatteryServiceImpl implements BatteryService {

    @Autowired
    private BatteryMapper batteryMapper;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private RuleMapper ruleMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 缓存键前缀
    private final String BATTERY_KEY_PREFIX = "battery:";
    private final String CAR_KEY_PREFIX = "car:";

    private final Long VOLTAGE_ID = 1L;
    private final Long ELECTRIC_CURRENT_ID = 2L;

    @Override
    @Transactional
    public boolean insertBattery(Car car, Battery battery) {
        // 1. 先写数据库
        int insertCar = carMapper.insertCar(car.getVid(), car.getCid(), car.getBatteryId(), car.getTotalMileage());
        int insertBattery = batteryMapper.insertBattery(battery.getId(), battery.getHealth(),
                battery.getType(), battery.getMx(), battery.getMi(), battery.getIx(), battery.getIi());

        if (insertCar == 1 && insertBattery == 1) {
            // 2. 成功后写缓存
            redisTemplate.opsForValue().set(BATTERY_KEY_PREFIX + battery.getId(), battery, 1, TimeUnit.HOURS);
            redisTemplate.opsForValue().set(CAR_KEY_PREFIX + car.getCid(), car, 1, TimeUnit.HOURS);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteBattery(Long batteryId, Long id) {
        // 1. 先删数据库
        int deleteBattery = batteryMapper.deleteBattery(batteryId);
        int deleteCar = carMapper.deleteCar(id);

        if (deleteCar == 1 && deleteBattery == 1) {
            // 2. 成功后删缓存
            redisTemplate.delete(BATTERY_KEY_PREFIX + batteryId);
            redisTemplate.delete(CAR_KEY_PREFIX + id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateBattery(Battery battery) {
        // 1. 先更新数据库
        int result = batteryMapper.updateBattery(battery.getId(), battery.getHealth(), battery.getType(),
                battery.getMx(), battery.getMi(), battery.getIx(), battery.getIi());

        if (result == 1) {
            // 2. 成功后更新缓存
            redisTemplate.opsForValue().set(BATTERY_KEY_PREFIX + battery.getId(), battery, 1, TimeUnit.HOURS);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Battery selectBattery(Long id) {
        // 1. 先查缓存
        String cacheKey = "battery:" + id;
        Battery battery = (Battery) redisTemplate.opsForValue().get(cacheKey);

        // 2. 如果缓存命中，直接返回
        if (battery != null) {
            return battery;
        }

        // 3. 缓存未命中，查询数据库
        battery = batteryMapper.selectById(id); // 需要先在 BatteryMapper 中添加此方法

        // 4. 如果数据库中存在，写入缓存
        if (battery != null) {
            redisTemplate.opsForValue().set(cacheKey, battery, 1, TimeUnit.HOURS);
        }

        return battery;
    }

    @Override
    public List<Map> alertMessage(List<AlertInfo> list) {
        List<Map> alertMessages = new ArrayList<>(list.size());
        for(AlertInfo info : list){
            Map<String,Object> map = new HashMap<>();
            Long batteryId = carMapper.selectBatteryById(info.getCarId());
            if(batteryId == null){
                map.put("信息",info.getCarId()+"号车不存在");
                continue;
            }
            Battery battery = batteryMapper.selectById(batteryId);
            if(info.getWarnId() == null){
                alertMessages.add(getRules(ELECTRIC_CURRENT_ID, info, battery.getType()));
                alertMessages.add(getRules(VOLTAGE_ID, info, battery.getType()));
            }else if(info.getWarnId().equals(ELECTRIC_CURRENT_ID)){
                alertMessages.add(getRules(ELECTRIC_CURRENT_ID, info, battery.getType()));
            } else if (info.getWarnId().equals(VOLTAGE_ID)) {
                alertMessages.add(getRules(VOLTAGE_ID, info, battery.getType()));
            } else{
                Map<String,Object> error = new HashMap<>(1);
                error.put("信息","规则编号出错");
                alertMessages.add(error);
            }
        }
        return alertMessages;
    }

    private Map getRules(Long RULE_ID, AlertInfo info, String type){
        Long carId = info.getCarId();
        String signal = info.getSignal();
        Map<String,Object> res = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        double lowerValue,upperValue;
        try{
            Map<String, Double> map = objectMapper.readValue(signal, Map.class);
            if(RULE_ID.equals(ELECTRIC_CURRENT_ID)){
                upperValue = map.get("Ix");  // 获取 Ix
                lowerValue = map.get("Ii");  // 获取 Ii
            } else if(RULE_ID.equals(VOLTAGE_ID)){
                upperValue = map.get("Mx");
                lowerValue = map.get("Mi");
            } else{
                res.put("信息","规则编号出错");
                return res;
            }
            double value = upperValue - lowerValue;
            List<Rule> rules = ruleMapper.selectRules(RULE_ID, type);
            for(Rule rule : rules){
                if(value >= rule.getLowerValue() && value <rule.getUpperValue()){
                    if(rule.getAlertLevel() != -1){
                        res.put("车架编号",carId);
                        res.put("电池类型",type);
                        res.put("warnName",rule.getName());
                        res.put("warnLevel",rule.getAlertLevel());
                        return res;
                    } else{
                        res.put("车架编号",carId);
                        res.put("电池类型",type);
                        res.put("warnLevel","车辆正常");
                        return res;
                    }
                }
            }
            return null;
        } catch (JsonProcessingException e) {
            res.put("message",carId+"出现异常"+e.getMessage());
            return res;
        }
    }


}