package com.stamptourproject.stamptour.dto.auth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtRespDto {
	private String grantType;
	private String accessToken;
}
