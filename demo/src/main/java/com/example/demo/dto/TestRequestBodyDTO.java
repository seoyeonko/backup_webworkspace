package com.example.demo.dto;

import lombok.Data;


// 데이터를 전달하는 Class
@Data
public class TestRequestBodyDTO {
	private int id;
	private String message;
}
