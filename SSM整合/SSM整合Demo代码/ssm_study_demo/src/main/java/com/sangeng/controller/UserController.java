package com.sangeng.controller;

import com.sangeng.common.PageInfoResult;
import com.sangeng.common.ResponseResult;
import com.sangeng.pojo.User;
import com.sangeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据id查询用户信息
     * @param id
     * @return 用户信息
     */
    @GetMapping("/user/{id}")
    public ResponseResult getById(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        return new ResponseResult(200, "操作成功", user);
    }

    /**
     * 查询所有用户信息
     * @return 所有用户信息
     */
    @GetMapping("/user")
    public ResponseResult getAll() {
        List<User> users = userService.getAll();
        // 测试统一异常处理
        // System.out.println(1/0);
        return new ResponseResult(200, "操作成功", users);
    }

    @GetMapping("/user/{pageNum}/{pageSize}")
    public ResponseResult getByPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        PageInfoResult pageInfoResult = userService.getByPage(pageNum, pageSize);
        return new ResponseResult(200, "操作成功", pageInfoResult);
    }

    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody User user) {
        int count = userService.addUser(user);
        return new ResponseResult(200, "成功插入" + count + "条数据");
    }

    @DeleteMapping("/user/{id}")
    public ResponseResult deleteById(@PathVariable("id") Integer id) {
        int count = userService.deleteById(id);
        return new ResponseResult(200, "成功删除" + count + "条数据");
    }

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody User user) {
        int count = userService.updateUser(user);
        return new ResponseResult(200, "成功更新" + count + "条数据");
    }

    @GetMapping("/testTransaction")
    public ResponseResult testTransaction() {
        userService.testTransaction();
        return new ResponseResult(200, "操作成功");
    }
}
