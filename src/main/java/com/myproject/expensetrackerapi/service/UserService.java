package com.myproject.expensetrackerapi.service;

import com.myproject.expensetrackerapi.entity.User;
import com.myproject.expensetrackerapi.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

   User create(UserModel uModel);

   User read(Long id);

   User update(User user,Long id);

   void delete(Long id);

   User getLoggedInUser();

   User readUser();

   User updateUser(UserModel user);

   void deleteUser();












}
