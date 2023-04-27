package com.stamptourproject.stamptour.entity;

import java.util.List;

import com.stamptourproject.stamptour.security.PrincipalUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	
	private int userId;
	private String username;
	private String password;
	private String name;
	
	private List<Authority> authorities;
	
	public PrincipalUser toPrincipal() {
		return PrincipalUser.builder()
				.userId(userId)
				.username(username)
				.password(password)
				.authorities(authorities)
				.build();
	}
	
	
}
