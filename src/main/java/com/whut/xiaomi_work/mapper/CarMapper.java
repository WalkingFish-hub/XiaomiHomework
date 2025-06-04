package com.whut.xiaomi_work.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CarMapper {
    @Insert("insert into car_info (vid, id, battery_id, total_mileage) value (#{vid},#{cid},#{batteryId},#{totalMileage})")
    int insertCar(String vid, Long cid, Long batteryId, Long totalMileage);

    @Delete("delete from car_info where id = #{id}")
    int deleteCar(Long id);

    @Select("select battery_id from car_info where id = #{carId}")
    Long selectBatteryById(Long carId);
}
