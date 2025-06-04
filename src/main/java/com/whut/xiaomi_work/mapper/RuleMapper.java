package com.whut.xiaomi_work.mapper;

import com.whut.xiaomi_work.entity.Rule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RuleMapper {

    @Select("select id, warn_id, name, battery_type, lower_value, upper_value, alert_level from rule where warn_id = #{warnId} and battery_type = #{type}")
    List<Rule> selectRules(Long warnId, String type);
}
