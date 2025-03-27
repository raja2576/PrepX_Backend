package com.prepXBackend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthResponse {

	private String jwt;
    private String message;
    private Long userId;
    private String nameString;
	public String getNameString() {
		return nameString;
	}
	public void setNameString(String nameString) {
		this.nameString = nameString;
	}
	public String getJwt() {
		return jwt;
	}
	public AuthResponse(String jwt, Long userId) {
		super();
		this.jwt = jwt;
		this.userId = userId;
	}
	public AuthResponse(String jwt, String message, Long userId,String namString) {
		super();
		this.jwt = jwt;
		this.message = message;
		this.userId = userId;
		this.nameString=namString;
	}
	public AuthResponse() {
		// TODO Auto-generated constructor stub
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
