package com.mybatis.tkmybatis;

import com.mybatis.tkmybatis.entity.User;
import com.mybatis.tkmybatis.test.Aestest;
import com.mybatis.tkmybatis.test.LambdaTest;
import net.minidev.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class TkmybatisApplicationTests {

    @Test
    void contextLoads() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            StringBuffer params = new StringBuffer();
            JSONObject param = new JSONObject();
            param.put("name", "liuzpsd");
            // 创建Post请求
            HttpPost httpPost = new HttpPost("http://localhost:8082/httpclienttest");
            StringEntity entity = null;
            entity = new StringEntity(param.toString(),"UTF-8");
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

   @Test
   public void test() {
       System.out.println(filterUser(list));
   }

   @Test
   public void testaes() throws Exception {
       Aestest aestest = new Aestest("ysyj-20190710234");
       System.out.println(aestest.aesEncrypt("fdsafhakdf"));
   }

}
