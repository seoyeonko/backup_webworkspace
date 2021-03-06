package com.example.seanPrj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.seanPrj.model.BookEntity;
import com.example.seanPrj.persistence.BookRepository;

import lombok.extern.slf4j.Slf4j;


// 서비스 레이어: 비즈니스 로직 수행
@Slf4j
@Service // 서비스 레이어임을 알려주는 어노테이션
public class BookService {
	
	@Autowired
	private BookRepository repository;
	
//	public String testService() {
//		// BookEntity 생성
//		BookEntity entity = BookEntity.builder().title("My first book item").build();
//		
//		// BookEntity 저장
//		repository.save(entity);
//		
//		// BookEntity 검색
//		BookEntity savedEntity = repository.findById(entity.getId()).get();
//		return savedEntity.getTitle();		
//	}
	
	// CREATE 
	public List<BookEntity> create(final BookEntity entity) {
		// Validations
		validate(entity);
		
		repository.save(entity); // DB 저장 
		log.info("Entity Id : {} is saved.", entity.getId());
		
		return repository.findByUserId(entity.getUserId()); // 저장된 엔티티를 포함하는 새 리스트 리턴 
	}
	
	// READ
	public List<BookEntity> retrieve(final String title) {
		return repository.findByTitle(title); // title 속성 값으로 검색 
	}
	
	// READ - all
	public List<BookEntity> retrieveAll() {
		return repository.findAll();  
	}
	
	// UPDATE - origin
	public List<BookEntity> update(final BookEntity entity) {
		// Validations
		validate(entity);
		
		final Optional<BookEntity> original = repository.findById(entity.getId()); // Optional: null pointer exception 방지

		original.ifPresent(book -> {
			book.setTitle(entity.getTitle());
			book.setAuthor(entity.getAuthor());
			book.setPublisher(entity.getPublisher());

			repository.save(book);
		});
		
//		return retrieve(entity.getUserId()); // repository.findAll() - List<> 반 -> 자동 생성? 
		return repository.findAll(); // List<> 객체를 반환 (수정된 아이템이 담긴 리스트)
	}
	
	// DELETE
	public List<BookEntity> delete(final BookEntity entity) {
		validate(entity);
		
		try {
//			repository.delete(entity); // id, tit, author, publsher, userid  값을 다 가져야 삭제가 되는 것
			repository.deleteByTitle(entity.getTitle());
		} catch(Exception e) {
			log.error("error deleting entity ", entity.getId(), e);
			throw new RuntimeException("error deleting entity " + entity.getId());
		}
		
//		return retrieve(entity.getUserId());
		return repository.findAll(); 
	}
	
	private void validate(final BookEntity entity) {
		if(entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}
		
		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}
}
