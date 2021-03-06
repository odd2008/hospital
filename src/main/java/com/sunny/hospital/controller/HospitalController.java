package com.sunny.hospital.controller;

import com.sunny.hospital.entity.Doctor;
import com.sunny.hospital.entity.Hospital;
import com.sunny.hospital.entity.Result;
import com.sunny.hospital.entity.RotationPicture;
import com.sunny.hospital.permission.bean.UserInfo;
import com.sunny.hospital.service.DoctorService;
import com.sunny.hospital.service.HospitalService;
import com.sunny.hospital.service.RotationPictureService;
import com.sunny.hospital.service.UserInfoService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/16 16:21
 * @Description: TODO 医院控制器
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "hospital")
public class HospitalController {

    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalController.class);

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private RotationPictureService rotationPictureService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * @deprecated 首页
     */
    @GetMapping("/index")
    public String blogIndex() {
        return "hospital/index";
    }

    /**
     * 医院展示页
     */
    @GetMapping("hospitalShow")
    public String HospitalShow(Model model) {
        List<RotationPicture> allRotationPicture = rotationPictureService.findAllRotationPicture();
        List<Hospital> hot = hospitalService.findHot();
        model.addAttribute("images",allRotationPicture);
        model.addAttribute("hots",hot);
        return "hospital/hospitalShow";
    }

    /**
     * 医院管理页面
     */
    @GetMapping("/hospitalManagePage")
    public String hospitalManagePage() {
        return "hospital/hospitalManage";
    }

    /**
     * @deprecated 添加或修改医院信息页面
     * */
    @GetMapping("updateOrAddHospital")
    public String updateOrAddHospital(Integer id, ModelMap modelMap){
        if (id!=null){
            Hospital findbyid = hospitalService.findbyId(id);
            modelMap.addAttribute("hospital",findbyid);
        }else {
            modelMap.addAttribute("hospital",new Hospital());
        }
        return "hospital/updateOrAddHospital";
    }

    /**
     * @param jsonObject 筛选条件对象
     * @deprecated 筛选查询医院接口
     */
    @PostMapping("queryHospital")
    @ResponseBody
    public Result queryHospital(@RequestBody JSONObject jsonObject) {
        return hospitalService.queryHospital(jsonObject);
    }

    /**
     * 添加医院信息
     */
    @PostMapping("addHospital")
    @ResponseBody
    public Result addHospital(@RequestBody Hospital hospital) {
        return hospitalService.addHospital(hospital);
    }

    /**
     * 修改医院信息
     */
    @PostMapping("updateHospital")
    @ResponseBody
    public Result updateHospital(@RequestBody Hospital hospital) {
        return hospitalService.updateHospital(hospital);
    }


    /**
     * 删除医院信息
     */
    @GetMapping("deleteHospital")
    @ResponseBody
    public Result deleteHospital(Integer id) {
        return hospitalService.deleteHospital(id);
    }

    /**
     * 查询所有医院
     * */
    @GetMapping("findAll")
    @ResponseBody
    public Result findAll(){
        //获取当前登录用户的信息
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取登录用户名
        String username = user.getUsername();
        //查询用户的权限

        UserInfo userInfo = userInfoService.findByUsername(username);
        String role = userInfo.getRoles().get(0).getName();
        List<Hospital> all=new ArrayList<>();
        if (role.equals("admin") || role.equals("user")){
            all = hospitalService.findAll();
        }else {
            Doctor doctor = doctorService.findByUsername(username);
            Hospital hospital=new Hospital();
            hospital.setHospitalName(doctor.getHospitalName());
            all.add(hospital);
        }
        return new Result(all);
    }
}
