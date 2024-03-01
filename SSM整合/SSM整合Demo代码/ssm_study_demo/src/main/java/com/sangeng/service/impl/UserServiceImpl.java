package com.sangeng.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sangeng.common.PageInfoResult;
import com.sangeng.mapper.UserMapper;
import com.sangeng.pojo.User;
import com.sangeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据id查询用户信息
     * @param id
     * @return 查询到的用户信息
     */
    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    /**
     * 查询所有用户信息
     * @return 所有用户信息
     */
    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

    /**
     * 分页查询数据
     * @param pageNum 页码
     * @param pageSize 一页包含多少条数据
     * @return 这一页的分页信息以及查询到的数据封装成的PageInfoResult对象
     */
    @Override
    public PageInfoResult getByPage(int pageNum, int pageSize) {
        // 开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        // 查询数据
        // 由于开启了分页查询 下面mybatis会查询返回对应页的数据
        List<User> users = userMapper.getAll();
        // 获取分页信息
        PageInfo pageInfo = new PageInfo(users);
        // 封装PageInfoResult结果
        PageInfoResult pageInfoResult = new PageInfoResult(pageInfo.getPageNum(), pageInfo.getPageSize(), (int) pageInfo.getTotal(), users);
        return pageInfoResult;
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Transactional // 开启事务
    @Override
    public void testTransaction() {
        userMapper.addUser(new User(null, "test1", 1, "testAAA"));
        System.out.println(1 / 0);
        userMapper.addUser(new User(null, "test2", 2, "testBBB"));
    }
}
