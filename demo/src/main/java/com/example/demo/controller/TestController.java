package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;

@RestController
@RequestMapping("test")
public class TestController {
	
	@GetMapping
	public String testController() {
		return "Hello World";
	}

	@GetMapping("/{id}")
	public String testControllerWithPAthVarilable(@PathVariable int id) {
		return "Hello World! ID " + id;
	}
	
	@GetMapping("/testRequestParam")
	public String testControllerWithRequestParam(@RequestParam int id) {
		return "Hello World! ID " + id;
	}
	
	@GetMapping("/testRequestBody")
	public String testControllerWithRequestBody(@RequestBody TestRequestBodyDTO dto) {
		return "Hello World! ID " + dto.getId() + dto.getMessage();
	}
	
	@GetMapping("/testResponseBody")
	public ResponseDTO<String> testControllerResponseBody() {
		List<String> list = new ArrayList<>();
		list.add("sean");
		list.add("yeji");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).error(null).build();
		// response 변수: new 예약어 없이 빌더 패턴으로 ResponseDTO 객체를 생성 data, error 명시)
		return response;
	}
	
	@GetMapping("/testResponseEntitiy") 
		public ResponseEntity<?> testControllerREsponseEntity() {
			List<String> list = new ArrayList<>();
			list.add("Hello");
			ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
			// http response를 400으로 설정
			return ResponseEntity.badRequest().body(response);
	}
	
}
