package com.yolo.demo;

import com.yolo.annotations.LazyBean;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@LazyBean
public class Token {

	private String accessToken;
	private String refreshToken;

	public static Token dummy() {
		return new Token()
				.setAccessToken("123abc")
				.setRefreshToken("abc123");
	}

}
