package com.sangeng.mapper;

import com.sangeng.pojo.User;

import java.util.List;

public interface UserMapper {
    User getById(Integer id);

    List<User> getAll();

    int addUser(User user);

    int deleteById(Integer id);

    int updateUser(User user);
}
