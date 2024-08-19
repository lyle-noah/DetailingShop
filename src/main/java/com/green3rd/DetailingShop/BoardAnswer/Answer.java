package com.green3rd.DetailingShop.BoardAnswer;

import java.time.LocalDateTime;
import java.util.Set;

import com.green3rd.DetailingShop.BoardQuestion.Question;
import com.green3rd.DetailingShop.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createDate;

	@ManyToOne
	private Question question;

	@ManyToOne
	private User author;

	private LocalDateTime modifyDate;

	@ManyToMany
	Set<User> voter;
}