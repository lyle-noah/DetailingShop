package com.green3rd.DetailingShop.Security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


// 어플리케이션에서 데이터베이스나 다른 소스에서 원하는 데이터를 찾지 못했을때 발생하는 예외를 정의함.
// @ResponseStatus 어노테이션은 특정 http 상태 코드와 메시지를 반환하도록 설정할 수 있음.
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException{
	private static final long serialVersionUID =1L;

	/**
	 * DataNotFoundException 생성자
	 * @param message 예외 메시지
	 */
	public DataNotFoundException(String message) {
		// 부모 클래스의 생성자를 호출하여 메시지를 전달합니다.
		super(message);
	}
}