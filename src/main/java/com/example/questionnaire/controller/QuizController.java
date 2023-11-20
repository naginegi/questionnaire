package com.example.questionnaire.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	// 請求的方法
	// ResquestBody 把外部傳進來的 JSON 格式 轉成class  ??
	// PostMapping 新增 post 提供 http methods
	@PostMapping(value = "api/quiz/create")  
	public QuizRes create(@RequestBody QuizReq req) {
		return quizService.create(req);
	}
/*
	@GetMapping(value = "api/quiz/search")
	public QuizRes search(@RequestBody  QuizSearchReq req) {
		System.out.println(req.getTitle()+req.getStartDate()+req.getEndDate());
		return quizService.search(req.getTitle(),req.getStartDate(),req.getEndDate());	
	}
	*/
	@GetMapping(value = "api/quiz/search")
	public QuizRes search(@RequestParam(name = "title", required = false) String title,
	                      @RequestParam(name = "startDate", required = false) LocalDate startDate,
	                      @RequestParam(name = "endDate", required = false) LocalDate endDate) {
		
	    return quizService.search(title, startDate, endDate);    
	}

}
