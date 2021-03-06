package com.sunny.hospital.controller;


import com.sunny.hospital.entity.Department;
import com.sunny.hospital.entity.Hospital;
import com.sunny.hospital.entity.Result;
import com.sunny.hospital.service.DepartmentService;
import com.sunny.hospital.service.HospitalService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/16 16:21
 * @Description: TODO 科室控制器
 * @Version 1.0
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "department")
public class DepartmentController {

    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private HospitalService hospitalService;

    /**
     * 根据医院查科室
     * */
    @GetMapping("findAllByHospitalName")
    @ResponseBody
    public Result findAllByHospitalName(String hospitalName){
        if (StringUtils.isEmpty(hospitalName)){
            return new Result(-1,"医院名称不能为空");
        }
        List<Department> allByHospitalName = departmentService.findAllByHospitalName(hospitalName);
        if (allByHospitalName==null || allByHospitalName.size()==0){
            return new Result(-1,"未查询到科室",new ArrayList<Department>());
        }
        return new Result(allByHospitalName);
    }

    /**
     * 管理医院页面
     * */
    @GetMapping("/departmentManagePage")
    public String departmentManagePage(Model model){
        List<Hospital> all = hospitalService.findAll();
        model.addAttribute("hospitals",all);
        return "department/departmentManage";
    }

    /**
     * @deprecated 添加或修改科室信息页面
     * */
    @GetMapping("updateOrAddDepartment")
    public String updateOrAddDepartment(Integer id, ModelMap modelMap){
        List<Hospital> all = hospitalService.findAll();
        modelMap.addAttribute("hospitals",all);
        if (id!=null){
            Department byId = departmentService.findById(id);
            modelMap.addAttribute("dept",byId);
        }else {
            modelMap.addAttribute("dept",new Department());
        }
        return "department/updateOrAddDepartment";
    }


    /**
     * 添加科室信息
     */
    @PostMapping("addDepartment")
    @ResponseBody
    public Result addDepartment(@RequestBody Department department){
        return departmentService.addDepartment(department);
    }


    /**
     *修改科室信息
     */
    @PostMapping("updateDepartment")
    @ResponseBody
    public Result updateDepartment(@RequestBody Department department){
        return  departmentService.updateDepartment(department);
    }


    /**
     * 删除科室信息
     */
    @GetMapping("deleteById")
    @ResponseBody
    public Result deleteById(Integer id){
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
