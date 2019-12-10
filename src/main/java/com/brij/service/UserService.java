package com.brij.service;

import com.brij.model.User;

import java.util.Set;

public interface UserService {

    public User createUser(int id, String name);

    public User getUser(int userId);

    Set<User> getUsers();
}