package com.example.seanPrj.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class BookEntity {
	@Id // pk 지정
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	
	private String id; // 자동 생성 
	private String title; // 제목 
	private String author; // 작가 
	private String publisher; // 출판사 
	private String userId; // 아이디 
}
