package com.whut.xiaomi_work.mapper;

import com.whut.xiaomi_work.entity.Battery;
import org.apache.ibatis.annotations.*;

@Mapper
public interface BatteryMapper {
    @Insert("insert into battery_info (id, health, type, mx, mi, ix, ii) VALUES (#{id},#{health},#{type},#{mx},#{mi},#{ix},#{ii})")
    int insertBattery(Long id, Double health, String type, Double mx, Double mi, Double ix, Double ii);

    @Delete("delete from battery_info where id = #{batteryId}")
    int deleteBattery(Long batteryId);

    @Update("update battery_info set health = #{health}, type = #{type}, mx = #{mx}, mi = #{mi}, ix = #{ix}, ii = #{ii} where id = #{id}")
    int updateBattery(Long id, Double health, String type, Double mx, Double mi, Double ix, Double ii);

    @Select("select id, health, type, mx, mi, ix,ii from battery_info where id = #{id}")
    Battery selectById(Long id);
}
