package com.example.questionnaire.vo;

import java.util.List;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;

//存前端來的東西  稱作"請求"
public class QuizReq extends QuizVo{

	public QuizReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuizReq(Questionnaire questionnaire, List<Question> questionList) {
		super(questionnaire, questionList);
		// TODO Auto-generated constructor stub
	}

	public QuizReq(Questionnaire questionnaire) {
		super(questionnaire);
		// TODO Auto-generated constructor stub
	}




	
}
