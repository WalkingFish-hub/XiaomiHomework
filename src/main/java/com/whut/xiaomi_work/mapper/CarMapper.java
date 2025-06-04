package com.whut.xiaomi_work.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CarMapper {
    @Insert("insert into car_info (vid, id, battery_id, total_mileage) value (#{vid},#{cid},#{batteryId},#{totalMileage})")
    int insertCar(String vid, Long cid, Long batteryId, Long totalMileage);
}
