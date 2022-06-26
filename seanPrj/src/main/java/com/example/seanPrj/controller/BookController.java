package com.example.seanPrj.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
	
	@PostMapping
	public ResponseEntity<?> createBook(@AuthenticationPrincipal String userId, @RequestBody BookDTO dto) {
		try {			
			BookEntity entity = BookDTO.toEntity(dto); 
			entity.setId(null); // 생성 당시 id는 없어야 하므로 id를 null로 초기화
			entity.setUserId(userId); // 임시 사용자 대신 아이디 설정 
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
	
	// 전체 책을 조회
	@GetMapping
	public ResponseEntity<?> retrieveBookListAll(@AuthenticationPrincipal String userId, @RequestBody(required=false) BookDTO dto) {
		List<BookEntity> entities = service.retrieveAll(); // 전체 책 조회
		List<BookDTO> dtos =entities.stream().map(BookDTO::new).collect(Collectors.toList()); // entity -> dto 변환 
		ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	// 한 권 책을 조회 
	@PostMapping("read")
	public ResponseEntity<?> retrieveBookList(@AuthenticationPrincipal String userId, @RequestBody BookDTO dto) {
		BookEntity entity = BookDTO.toEntity(dto); // dto -> entity (id 외의 속성들 null) 
		System.out.print(entity);
		List<BookEntity> entities = service.retrieve(userId); // 서비스 메서드의 Retrieve 메서드를 이용해 todo 리스트를 가져옴
		List<BookDTO> dtos =entities.stream().map(BookDTO::new).collect(Collectors.toList()); // entity -> dto 변환 
		ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
		System.out.print(response);

		return ResponseEntity.ok().body(response);
	}
	
	
	@PutMapping
	public ResponseEntity<?> updateBook(@AuthenticationPrincipal String userId, @RequestBody BookDTO dto) {
		BookEntity entity = BookDTO.toEntity(dto);  // 
		entity.setUserId(userId); // id를 userId로 초기화 
		List<BookEntity> entities = service.update(entity);
		List<BookDTO> dtos = entities.stream().map(BookDTO::new).collect(Collectors.toList());
		ResponseDTO<BookDTO> response = ResponseDTO.<BookDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);	
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteBook(@AuthenticationPrincipal String userId, @RequestBody BookDTO dto) {
		try {
			BookEntity entity = BookDTO.toEntity(dto);  //
			entity.setUserId(userId); // 임시 사용자 아이디를 설정
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

