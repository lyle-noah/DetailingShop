package com.green3rd.DetailingShop.User;

import com.green3rd.DetailingShop.UserCreate.UserRole;
import com.green3rd.DetailingShop.UserLike.UserLikes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
@Entity
@ToString
@Table(name = "user", indexes = @Index(name = "idx_username", columnList = "username"))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String email;
	private String password;

	@Column(nullable = false, unique = true)
	private String username;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UserLikes> likes;

	// 새로운 필드 추가
	private String securityQuestion;
	private String securityAnswer;

	// 프로필 이미지 경로 필드 추가
	private String profileImagePath;

	//마지막 로그인 날짜 필드 추가
	private LocalDate lastLoginDate;

}


