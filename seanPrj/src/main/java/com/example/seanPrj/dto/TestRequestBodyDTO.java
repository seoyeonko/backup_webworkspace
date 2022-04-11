package com.example.seanPrj.dto;

import lombok.Data;

@Data
// 요청시 전달된 값을 담는데 사용할 객체 
public class TestRequestBodyDTO {
	private int id;
	private String message;
}
