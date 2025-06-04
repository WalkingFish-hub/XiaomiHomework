package com.whut.xiaomi_work.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BatteryMapper {
    @Insert("insert into battery_info (id, health, type, mx, mi, ix, ii) VALUES (#{id},#{health},#{type},#{mx},#{mi},#{ix},#{ii})")
    int insertBattery(Long id, Double health, String type, Double mx, Double mi, Double ix, Double ii);
}
