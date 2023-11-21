package com.example.questionnaire.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionnaire.Service.ifs.QuizService;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizSearchReq;

@RestController
@CrossOrigin
public class QuizController {

	@Autowired
	private QuizService quizService;

	// �ШD����k
	// ResquestBody ��~���Ƕi�Ӫ� JSON �榡 �নclass ??
	// PostMapping �s�W post ���� http methods
	@PostMapping(value = "api/quiz/create")
	public QuizRes create(@RequestBody QuizReq req) {
		return quizService.create(req);
	}

	@GetMapping(value = "api/quiz/search")
	public QuizRes search(String title, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		System.out.println(title + ":" + startDate + ":" + endDate);
//		return null;
		return quizService.search(title, startDate, endDate);
	}
	
	/*
	@GetMapping(value = "api/quiz/search")
	public QuizRes search(@RequestBody QuizSearchReq req) {
		System.out.println(req.getTitle()+":"+req.getStartDate()+":"+req.getEndDate());
		return quizService.search(req.getTitle(),req.getStartDate(),req.getEndDate());
	}
	*/

}
