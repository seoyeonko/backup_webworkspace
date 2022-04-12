package com.example.seanPrj.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.seanPrj.dto.BookDTO;
import com.example.seanPrj.dto.ResponseDTO;
import com.example.seanPrj.model.BookEntity;
import com.example.seanPrj.service.BookService;

@RestController
@RequestMapping("book")
public class BookController {
	@Autowired
	private BookService service;
	
//	@GetMapping("/test")
//	public ResponseEntity<?> testBook() {
//		String str = service.testService(); 
//		List<String> list = new ArrayList<>();
//		list.add(str);
//		
//		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
//		return ResponseEntity.ok().body(response);		
//	}
	
	@PostMapping
	public ResponseEntity<?> createBook(@RequestBody BookDTO dto) {
		try {
//			String userId = "Seoyeon Ko"; // my name
			
			BookEntity entity = BookDTO.toEntity(dto);
			entity.setId(null); // 생성 당시 id는 없어야 하므로 id를 null로 초기화
//			entity.setUserId(userId);
			List<BookEntity> entities = service.create(entity);
			List<BookDTO> dtos =entities.stream().map(BookDTO::new).collect(Collectors.toList()); // entity -> dto 변
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> retrieveBookList(@RequestBody BookDTO dto) {
//		String userId = "Seoyeon Ko"; // my name
		
		BookEntity entity = BookDTO.toEntity(dto);
		
		List<BookEntity> entities = service.retrieve(entity.getTitle());
		List<BookDTO> dtos =entities.stream().map(BookDTO::new).collect(Collectors.toList()); // entity -> dto 변
		ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateBook(@RequestBody BookDTO dto) {
//		String userId = "Seoyeon Ko";
		
		BookEntity entity = BookDTO.toEntity(dto); // dto -> entity (id 외의 속성들 null)
//		entity.setUserId(userId);
		List<BookEntity> entities = service.update(entity);
		List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());
		ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);	
	}
	
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody BookDTO dto) {
		try {
//			String userId = "Seoyeon Ko";
			
			BookEntity entity = BookDTO.toEntity(dto); 
//			entity.setUserId(userId);
			List<BookEntity> entities = service.delete(entity);
			List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().error(error).build();
			
			return ResponseEntity.badRequest().body(response);
		}
	}
};

