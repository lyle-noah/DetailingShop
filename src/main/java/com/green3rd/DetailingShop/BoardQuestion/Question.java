package com.green3rd.DetailingShop.BoardQuestion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.green3rd.DetailingShop.BoardAnswer.Answer;
import com.green3rd.DetailingShop.User.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 200)
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createDate;

	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;

	@ManyToOne
	private User author;

	private LocalDateTime modifyDate;

	@ManyToMany
	Set<User> voter;
//	조회 수
	@NotNull
	@Column(columnDefinition = "integer default 0")
	private int view;
}