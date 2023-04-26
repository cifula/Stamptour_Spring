package com.stamptourproject.stamptour.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stamptourproject.stamptour.aop.annotation.ValidAspect;
import com.stamptourproject.stamptour.dto.auth.SignupDto;
import com.stamptourproject.stamptour.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@ValidAspect
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignupDto signupDto, BindingResult bindingResult) {
		authenticationService.checkDuplicatedUsername(signupDto.getUsername());
		authenticationService.signup(signupDto);
		return ResponseEntity.ok().body(true);
	}

}
