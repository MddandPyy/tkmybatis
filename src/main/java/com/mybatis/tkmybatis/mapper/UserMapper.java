package com.mybatis.tkmybatis.mapper;

import com.mybatis.tkmybatis.entity.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
//@Mapper
public interface UserMapper extends Mapper<User> {

}
