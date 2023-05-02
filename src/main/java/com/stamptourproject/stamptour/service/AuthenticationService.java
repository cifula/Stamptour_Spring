package com.stamptourproject.stamptour.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stamptourproject.stamptour.dto.auth.JwtRespDto;
import com.stamptourproject.stamptour.dto.auth.LoginReqDto;
import com.stamptourproject.stamptour.dto.auth.PrincipalRespDto;
import com.stamptourproject.stamptour.dto.auth.SignupReqDto;
import com.stamptourproject.stamptour.entity.Authority;
import com.stamptourproject.stamptour.entity.User;
import com.stamptourproject.stamptour.exception.CustomException;
import com.stamptourproject.stamptour.exception.ErrorMap;
import com.stamptourproject.stamptour.repository.UserRepository;
import com.stamptourproject.stamptour.security.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
	
	private final UserRepository userRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	
	public void checkDuplicatedUsername(String username) {
		if(userRepository.findUserByUsername(username) != null) {
			throw new CustomException("Duplicated Email", 
					ErrorMap.builder().put("email", "사용중인 이메일입니다").build());
		}
	}
	
	public void signup(SignupReqDto signupDto) {
		User userEntity = signupDto.toEntity();
		
		userRepository.saveUser(userEntity);
		userRepository.saveAuthority(Authority.builder()
				.userId(userEntity.getUserId())
				.roleId(2)
				.build());
	}
	
	public JwtRespDto signin(LoginReqDto loginReqDto) {
		
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(loginReqDto.getUsername(), loginReqDto.getPassword());
		
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		
		return jwtTokenProvider.generateToken(authentication);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User userEntity = userRepository.findUserByUsername(username);
		 
		if(userEntity == null) {
			throw new CustomException("로그인 실패", ErrorMap.builder().put("email", "사용자 정보를 확인하세요").build());
		}
		
		return userEntity.toPrincipal();
	}
	
	public boolean authenticated(String accessToken) {
		return jwtTokenProvider.validateToken(jwtTokenProvider.getToken(accessToken));
	}
	
	public PrincipalRespDto getPrincipal(String accessToken) {
		Claims claims = jwtTokenProvider.getClaims(jwtTokenProvider.getToken(accessToken));
		User userEntity = userRepository.findUserByUsername(claims.getSubject());
		
		return PrincipalRespDto.builder()
				.userId(userEntity.getUserId())
				.username(userEntity.getUsername())
				.name(userEntity.getName())
				.authorities((String) claims.get("auth"))
				.build();
	}

}
