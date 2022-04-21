package com.zyj.myzhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyj.myzhxy.pojo.Admin;
import com.zyj.myzhxy.pojo.LoginForm;
import com.zyj.myzhxy.pojo.Student;
import com.zyj.myzhxy.pojo.Teacher;
import com.zyj.myzhxy.service.AdminService;
import com.zyj.myzhxy.service.StudentService;
import com.zyj.myzhxy.service.TeacherService;
import com.zyj.myzhxy.util.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author zhang
 * @Date 2022/4/18 - 16:16
 * @Version 1.0
 */
@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    /**
     * 验证码功能的实现
     *
     * @param request
     * @param response
     */
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        // 获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();

        // 获取图片上的验证码
        char[] verifiCodeChars = CreateVerifiCodeImage.getVerifiCode();
        String verifiCode = new String(verifiCodeChars);

        // 将验证码文本放入session域，为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode", verifiCode);

        try {
            // 将验证码图片响应给浏览器
            ServletOutputStream outputStream = response.getOutputStream();
            // 将verifiCodeImage以JPEG的格式写到outputStream流中
            ImageIO.write(verifiCodeImage, "JPEG", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录验证，查询提交的表单是否有效
     * @param loginForm 登录页提交的表单
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        // 验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode();
        if ("".equals(sessionVerifiCode) || null == sessionVerifiCode) {
            return Result.fail().message("验证码失效，请刷新后重试");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(loginVerifiCode)) {
            return Result.fail().message("验证码错误");
        }

        // 从session中移除现有验证码记录
        session.removeAttribute("verifiCode");

        // 对用户类型进行校验
        Map<String, Object> map = new LinkedHashMap<>();  // 用于存放响应的数据
        switch (loginForm.getUserType()) {
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if (null != admin) {
                        // 在数据库找到该用户，将用户类型和用户id转换为密文，以token向客户端返回信息
                        map.put("token", JwtHelper.createToken(admin.getId().longValue(), 1));
                    } else {
                        throw new RuntimeException("用户名或密码错误");
                    }
                    // 若没有异常，说明可以登录，返回结果
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    // 产生异常，返回出错信息
                    return Result.fail().message(e.getMessage());
                }

            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if (null != student) {
                        // 在数据库找到该用户，将用户类型和用户id转换为密文，以token向客户端返回信息
                        map.put("token", JwtHelper.createToken(student.getId().longValue(), 2));
                    } else {
                        throw new RuntimeException("用户名或密码错误");
                    }
                    // 若没有异常，说明可以登录，返回结果
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    // 产生异常，返回出错信息
                    return Result.fail().message(e.getMessage());
                }

            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if (null != teacher) {
                        // 在数据库找到该用户，将用户类型和用户id转换为密文，以token向客户端返回信息
                        map.put("token", JwtHelper.createToken(teacher.getId().longValue(), 3));
                    } else {
                        throw new RuntimeException("用户名或密码错误");
                    }
                    // 若没有异常，说明可以登录，返回结果
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    // 产生异常，返回出错信息
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("查无此用户");
    }

    /**
     * 根据登录第二次请求发送来的token，传送用户信息回去
     * @param token
     * @return
     */
    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader("token") String token){
        // 判断token是否过期
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            // 过期，返回错误信息
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        // token没有过期，解析出用户id和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        // 向map中传入数据
        Map<String, Object> map = new LinkedHashMap<>();
        switch (userType){
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType", userType);
                map.put("user", admin);
                break;
            case 2:
                Student student = studentService.getStdentById(userId);
                map.put("userType", userType);
                map.put("user", student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType", userType);
                map.put("user", teacher);
                break;
        }
        return Result.ok(map);
    }

    /**
     * 文件上传统一入口
     * @param multipartFile 上传的头像文件
     * @return
     */
    @ApiOperation("文件上传统一入口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
            // 接收图片
            @ApiParam("上传的头像文件") @RequestPart("multipartFile") MultipartFile multipartFile
            ){
        // 生成文件名
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String newFileName = uuid.concat(originalFilename.substring(index));

        // 保存文件 将文件发送到第三方/独立的图片服务器上，这里保存到本机
        String portraitPath = "D:/Java/尚硅谷毕设项目/myzhxy/target/classes/public/upload/".concat(newFileName);
        try {
            multipartFile.transferTo(new File(portraitPath)); // 将图片传输到指定位置
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 响应图片路径
        String path = "upload/".concat(newFileName);
        return Result.ok(path);
    }

    /**
     * 修改用户密码
     * @param token
     * @param olePwd
     * @param newPwd
     * @return
     */
    @ApiOperation("修改用户密码")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
            @ApiParam("蕴含用户信息的token口令") @RequestHeader("token") String token,
            @ApiParam("原密码") @PathVariable("oldPwd") String olePwd,
            @ApiParam("新密码") @PathVariable("newPwd") String newPwd
    ){
        // 判断token是否在有效期内
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            return Result.fail().message("token失效，请重新登录");
        }

        // 获取用户id和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        olePwd = MD5.encrypt(olePwd);
        newPwd = MD5.encrypt(newPwd);

        switch (userType){
            case 1:
                QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
                adminQueryWrapper.eq("id", userId.intValue())
                        .eq("password", olePwd);
                Admin admin = adminService.getOne(adminQueryWrapper);
                if(admin != null){
                    // 找到该用户，修改密码
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                }else{
                    // 没有找到该用户，返回错误信息
                    return Result.fail().message("原密码错误");
                }
                break;

            case 2:
                QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                studentQueryWrapper.eq("id", userId.intValue())
                        .eq("password", olePwd);
                Student student = studentService.getOne(studentQueryWrapper);
                if(student != null){
                    // 找到该用户，修改密码
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                }else{
                    // 没有找到该用户，返回错误信息
                    return Result.fail().message("原密码错误");
                }
                break;

            case 3:
                QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
                teacherQueryWrapper.eq("id", userId.intValue())
                        .eq("password", olePwd);
                Teacher teacher = teacherService.getOne(teacherQueryWrapper);
                if(teacher != null){
                    // 找到该用户，修改密码
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                }else{
                    // 没有找到该用户，返回错误信息
                    return Result.fail().message("原密码错误");
                }
                break;
        }
        return  Result.ok();
    }

}
