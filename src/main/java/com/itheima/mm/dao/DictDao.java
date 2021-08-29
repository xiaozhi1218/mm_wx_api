package com.itheima.mm.dao;

import com.itheima.mm.pojo.Dict;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.itheima.mm.dao
 *
 * @author Leevi
 * 日期2020-11-05  10:22
 */
public interface DictDao {
    List<Dict> findList(Map parameterMap);

    Dict findByCityName(String cityName);

}
