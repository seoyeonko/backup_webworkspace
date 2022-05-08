package com.example.seanPrj.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.seanPrj.model.BookEntity;

// JpaRepository 상속 받은 BookRepository 인터페이스 
// JpaRepository: save, findById, findAll 등이 기본 제공되는 인터페이스에 해당
@Transactional
@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {
	List<BookEntity> findByUserId(String userId);
	
	@Query("select b from BookEntity b where b.title = ?1")
	List<BookEntity> findByTitle(String title);
	
	void deleteByTitle(String title); 
	
	// deleteByTitle
	// findById
	// findAll 이미 있음
}
