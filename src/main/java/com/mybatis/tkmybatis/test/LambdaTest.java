package com.mybatis.tkmybatis.test;

import com.mybatis.tkmybatis.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LambdaTest {

    List<User> list  = Arrays.asList(
            new User(1,"liuzhuopeng1",18),
            new User(2,"liuzhuopeng1",28),
            new User(3,"liuzhuopeng1",38),
            new User(4,"liuzhuopeng1",48)
    );

    public List<User> filterUser(List<User> list){
        List<User> us = new ArrayList<>();
        for (User u :list){
            if(u.getAge()>=30){
                list.add(u);
            }
        }
        return us;
    }

    public static void main(String[] args) {
       // System.out.println(filterUser(list));
    }
}
