package com.sangeng.service;

import com.sangeng.common.PageInfoResult;
import com.sangeng.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    User getById(Integer id);

    List<User> getAll();

    PageInfoResult getByPage(int pageNum, int pageSize);

    int addUser(User user);

    int deleteById(Integer id);

    int updateUser(User user);

    void testTransaction();
}
