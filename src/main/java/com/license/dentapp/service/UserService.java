package com.license.dentapp.service;

import com.license.dentapp.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User findById(int theId);
    User findByName(String theName);
    User findByEmail(String theMail);
    User save(User theUser);
    void deleteById(int theId);
}
