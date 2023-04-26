package com.stamptourproject.stamptour.repository;

import org.apache.ibatis.annotations.Mapper;

import com.stamptourproject.stamptour.entity.Authority;
import com.stamptourproject.stamptour.entity.User;

@Mapper
public interface UserRepository {
	
	public User findUserByUsername(String username);
	public int saveUser(User user);
	public int saveAuthority(Authority authority);
}
