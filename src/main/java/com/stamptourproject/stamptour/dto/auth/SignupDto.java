package com.stamptourproject.stamptour.dto.auth;

import javax.validation.constraints.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.stamptourproject.stamptour.entity.User;

import lombok.Data;

@Data
public class SignupDto {
	
	private String username;
	
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$", 
			message = "비밀번호는 영문자, 숫자, 특수문자를 포함하여 8 ~ 16자로 작성하세요.")
	private String password;
	
	@Pattern(regexp = "^[가-힣]{2,7}$",
			message = "한글이름만 작성 가능합니다.")
	private String name;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(new BCryptPasswordEncoder().encode(password))
				.name(name)
				.build();
	}
}
