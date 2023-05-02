package com.stamptourproject.stamptour.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stamptourproject.stamptour.aop.annotation.ValidAspect;
import com.stamptourproject.stamptour.dto.auth.LoginReqDto;
import com.stamptourproject.stamptour.dto.auth.SignupReqDto;
import com.stamptourproject.stamptour.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@ValidAspect
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDto signupDto, BindingResult bindingResult) {
		authenticationService.checkDuplicatedUsername(signupDto.getUsername());
		authenticationService.signup(signupDto);
		return ResponseEntity.ok().body(true);
	}
	
	@ValidAspect
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginReqDto loginReqDto, BindingResult bindingResult) {
		return ResponseEntity.ok(authenticationService.signin(loginReqDto));
	}
	
	@GetMapping("/authenticated")
	public ResponseEntity<?> authenticated(String accessToken) {
		return ResponseEntity.ok(authenticationService.authenticated(accessToken));
	}
	
	@GetMapping("/principal")
	public ResponseEntity<?> getPrincipal(String accessToken) {
		return ResponseEntity.ok(authenticationService.getPrincipal(accessToken));
	}
	

}
