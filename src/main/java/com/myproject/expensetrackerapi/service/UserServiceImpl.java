package com.myproject.expensetrackerapi.service;

import com.myproject.expensetrackerapi.entity.User;
import com.myproject.expensetrackerapi.exceptions.ItemAlreadyExistsException;
import com.myproject.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.myproject.expensetrackerapi.model.UserModel;
import com.myproject.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public User create(UserModel user) {

        if(userRepo.existsByEmail(user.getEmail()))
        {
            throw new ItemAlreadyExistsException("User is already registered with email :" +user.getEmail());
        }

        User newUser=new User();
        BeanUtils.copyProperties(user,newUser);
        user.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        return userRepo.save(newUser);
    }

    @Override
    public User read(Long id) throws ResourceNotFoundException {
        return userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found for the id :"+id));
    }

    @Override
    public User update(User user, Long id) {

        User oldUser=read(id);

        oldUser.setName(user.getName() !=null ? user.getName() : oldUser.getName());
        oldUser.setEmail(user.getEmail() !=null ? user.getEmail() : oldUser.getEmail());
        oldUser.setPassword(user.getPassword() !=null ? bcryptEncoder.encode(user.getPassword()) : oldUser.getPassword());
        oldUser.setAge(user.getAge() !=null ? user.getAge() : oldUser.getAge());

        return userRepo.save(oldUser);
    }

    @Override
    public void delete(Long id) {
        User user=read(id);
        userRepo.delete(user);
    }

    @Override
    public User getLoggedInUser() {

       Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

       String email = authentication.getName();
      return userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found for the email "+email));
    }

    @Override
    public User readUser() {
        Long id=getLoggedInUser().getId();
        return userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found for the id :"+id));

    }

    @Override
    public User updateUser(UserModel user) {

        User oldUser=readUser();

        oldUser.setName(user.getName() !=null ? user.getName() : oldUser.getName());
        oldUser.setEmail(user.getEmail() !=null ? user.getEmail() : oldUser.getEmail());
        oldUser.setPassword(user.getPassword() !=null ? bcryptEncoder.encode(user.getPassword()) : oldUser.getPassword());
        oldUser.setAge(user.getAge() !=null ? user.getAge() : oldUser.getAge());

        return userRepo.save(oldUser);
    }

    @Override
    public void deleteUser() {
        User user=readUser();
        userRepo.delete(user);
    }
}


