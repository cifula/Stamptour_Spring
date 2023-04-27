package com.stamptourproject.stamptour.dto.auth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PrincipalRespDto {
	private int userId;
	private String username;
	private String name;
	private String authorities;
	
}
