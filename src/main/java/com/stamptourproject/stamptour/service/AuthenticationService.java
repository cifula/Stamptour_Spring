package com.stamptourproject.stamptour.service;

import org.springframework.stereotype.Service;

import com.stamptourproject.stamptour.dto.auth.SignupDto;
import com.stamptourproject.stamptour.entity.User;
import com.stamptourproject.stamptour.exception.CustomException;
import com.stamptourproject.stamptour.exception.ErrorMap;
import com.stamptourproject.stamptour.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository userRepository;
	
	public void checkDuplicatedUsername(String username) {
		if(userRepository.findUserByUsername(username) != null) {
			throw new CustomException("Duplicated Email", 
					ErrorMap.builder().put("email", "사용중인 이메일입니다").build());
		}
	}
	
	public void signup(SignupDto signupDto) {
		User userEntity = signupDto.toEntity();
		
		userRepository.saveUser(userEntity);
	}

}
