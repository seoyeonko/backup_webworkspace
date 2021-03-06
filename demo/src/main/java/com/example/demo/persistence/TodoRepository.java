package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TodoEntity;

//JpaRepository 상속 받은 RodoRepository 인터페이
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
//	@Query("select * from Todo t where t.title = ?1")
	List<TodoEntity> findByUserId(String userId);

	
}