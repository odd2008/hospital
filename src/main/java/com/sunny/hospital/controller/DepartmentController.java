package com.sunny.hospital.controller;


import com.sunny.hospital.entity.Department;
import com.sunny.hospital.entity.Result;
import com.sunny.hospital.service.DepartmentService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/16 16:21
 * @Description: TODO 科室控制器
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "department")
public class DepartmentController {

    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalController.class);

    @Autowired
    private DepartmentService departmentService;


    /**
     * 添加科室信息
     */
    @PostMapping("addDepartment")
    @ResponseBody
    public Result addDepartment(@RequestBody Department department) {
        return departmentService.addDepartment(department);
    }


    /**
     * 修改科室信息
     */
    @PostMapping("updateDepartment")
    @ResponseBody
    public Result updateDepartment(@RequestBody Department department) {
        return departmentService.updateDepartment(department);
    }


    /**
     * 删除科室信息
     */
    @GetMapping("deleteById")
    @ResponseBody
    public Result deleteById(Integer id) {
        return departmentService.deleteById(id);
    }


    /**
     * @param jsonObject 筛选条件对象
     * @deprecated 筛选查询科室接口
     */
    @PostMapping("queryDepartment")
    @ResponseBody
    public Result queryDepartment(@RequestBody JSONObject jsonObject) {
        return departmentService.queryDepartment(jsonObject);
    }

}
