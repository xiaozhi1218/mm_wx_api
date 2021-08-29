package com.itheima.mm.service;

import com.itheima.mm.dao.DictDao;
import com.itheima.mm.pojo.Dict;
import com.itheima.mm.utils.LocationUtil;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包名:com.itheima.mm.service
 *
 * @author Leevi
 * 日期2020-11-05  10:12
 */
public class DictService {

    public Map findCityList(Map parameterMap) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        DictDao dictDao = sqlSession.getMapper(DictDao.class);
        //1. 获取当前城市信息
        //1.1 获取当前城市的经纬度
        String location = (String) parameterMap.get("location");
        //1.2 根据当前城市的经纬度获取当前城市名,调用LocationUtil的方法使用高德地图API进行逆地理解析查询
        String cityName = LocationUtil.parseLocation(location);
        //1.3 根据cityName到t_dict表查询城市的名字和城市的id
        Dict dict = dictDao.findByCityName(cityName);

        //2. 获取热门城市列表
        List<Dict> dictList = dictDao.findList(parameterMap);
        //将数据封装到Map中返回
        Map resultMap = new HashMap();
        resultMap.put("location",dict);
        resultMap.put("citys",dictList);

        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return resultMap;
    }
}
