package com.example.seanPrj.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
// HTTP 응답으로 사용할 DTO
public class ResponseDTO<T> {
	private String error;
	private List<T> data;
}
