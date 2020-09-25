package com.mybatis.tkmybatis.controller;


import com.google.gson.Gson;
import com.mybatis.tkmybatis.entity.User;
import com.mybatis.tkmybatis.service.UserService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    public String getUser(int id,Model model){
        User user = userService.getUser(id);
        model.addAttribute("user",user);
        return "updateuser";
    }

    @GetMapping(value = "/put")
    public void putUser(){
        User user = new User();
        user.setName("liuzhuopeng");
        user.setAge(18);
        userService.insertUser(user);

    }

    @PostMapping("/adduser")
    public String addUser(User user){
        userService.insertUser(user);
        return "success";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }

    @GetMapping("/getAll")
    public String showAllUser(Model model){
        List<User> list = userService.getAllUser();
        model.addAttribute("list",list);
        return "showalluser";
    }

    @PostMapping("/updateuser")
    public String updateUser(User user){
        userService.updateUser(user);
        return "success";
    }

    @RequestMapping("/deleteuser")
    public String deleteUser(int id){
        userService.deleteUser(id);
        return "redirect:/getAll";
    }

    @PostMapping("/httpclienttest")
    @ResponseBody
    public String test(@RequestBody User user){
        return "httpclient刘"+user.getName();
    }


    @GetMapping("/testh")
    public void testh(){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            User user = new User();
            user.setName("liuzp");
            Gson gson = new Gson();
            String json = gson.toJson(user);
            // 创建Post请求
            HttpPost httpPost = new HttpPost("http://localhost:8082/httpclienttest");
            StringEntity entity = null;
            entity = new StringEntity(json,"UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json");
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            String s = EntityUtils.toString(responseEntity, "UTF-8");
            System.out.println(s);


        }catch (Exception e) {

        }finally {
            HttpClientUtils.closeQuietly(httpClient);
        }
    }

    @GetMapping("/testUpdate")
    public void testUpdate(){
        User user1 = userService.getUser(5);
        user1.setName("liuzhuopeng");
        userService.updateUser(user1);
        User user2 = userService.getUser(5);
        System.out.println(user2.toString());
    }

}
