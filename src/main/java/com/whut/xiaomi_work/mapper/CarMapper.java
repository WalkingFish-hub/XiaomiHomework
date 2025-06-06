package com.whut.xiaomi_work.mapper;


import com.whut.xiaomi_work.entity.Car;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CarMapper {
    @Insert("insert into car_info (vid, id, battery_id, total_mileage) value (#{vid},#{id},#{batteryId},#{totalMileage})")
    int insertCar(String vid, Long id, Long batteryId, Long totalMileage);

    @Delete("delete from car_info where id = #{id}")
    int deleteCar(Long id);

    @Select("select battery_id from car_info where id = #{carId}")
    Long selectBatteryById(Long carId);

    @Select("select vid, id, battery_id, total_mileage from car_info")
    List<Car> selectCars();
}
