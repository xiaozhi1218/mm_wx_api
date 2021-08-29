package com.itheima.mm.dao;

import com.itheima.mm.pojo.Company;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.itheima.mm.dao
 *
 * @author Leevi
 * 日期2020-11-07  09:27
 */
public interface CompanyDao {
    List<Map> findCompanyList(Map parameterMap);

    Company findById(Integer categoryId);
}
