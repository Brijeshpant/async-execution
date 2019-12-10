package com.brij.service;

import com.brij.model.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserServiceImpl implements UserService {

    Set<User> users = new HashSet<User>();

    @Override
    public User createUser(int id, String name) {
        final User u = new User(id);
        u.setName(name);
        users.add(u);
        return u;
    }

    @Override
    public User getUser(int userId) {
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Optional<User> optionalUser = users.stream().filter(user -> (user.getId() == userId)).findFirst();
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public Set<User> getUsers() {
        return this.users;
    }

}