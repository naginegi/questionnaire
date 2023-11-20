package com.example.questionnaire;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.questionnaire.Service.ifs.QuizService;
import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizVo;

@SpringBootTest
public class QuizServiceTest {

	@Autowired
	private QuizService quizService;

	@Test
	public void CreateTest1() {
		///////////////////
		// qn�Gtitle���~�B�������~�B�}�l�ɶ��B�����ɶ��B�}�l>����
		Questionnaire questionnaire = new Questionnaire();

		// qn ���~
		// qn�Gtitle���~
		questionnaire = new Questionnaire("", true, "ABCDE", LocalDate.of(2023, 11, 19), LocalDate.of(2023, 12, 1));
		QuizRes result = quizService.create(new QuizReq(questionnaire));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "title error");
		// qn �������~
		questionnaire = new Questionnaire("Quiz", true, "", LocalDate.of(2023, 11, 19), LocalDate.of(2023, 12, 1));
		result = quizService.create(new QuizReq(questionnaire));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "Description error");
		// qn�G�}�l�ɶ����~
		questionnaire = new Questionnaire("Quiz", true, "ABCDE", null, LocalDate.of(2023, 12, 1));
		result = quizService.create(new QuizReq(questionnaire));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "start error");
		// qn�G�����ɶ����~
		questionnaire = new Questionnaire("Quiz", true, "ABCDE", LocalDate.of(2023, 11, 19), null);
		result = quizService.create(new QuizReq(questionnaire));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "end error");
		// qn�G�}�l>����
		questionnaire = new Questionnaire("Quiz", true, "ABCDE", LocalDate.of(2023, 12, 19),
				LocalDate.of(2023, 11, 19));
		result = quizService.create(new QuizReq(questionnaire));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "s>e error");
		///////////////////
		// �i�H�u�s�W�@�i�ݨ��S���D�ءA����u���D�بS���ݨ�
		// �s�W�@�i�ݨ�
		questionnaire = new Questionnaire("Quiz", true, "ABCDE", LocalDate.of(2023, 11, 19),
				LocalDate.of(2023, 11, 20));
		// qu ���~
		List<Question> questionList = new ArrayList<>();
		Question qu = new Question();
		// qu�Gid�Bqtitle�B�ﶵ�����B�ﶵ����
		// id���~
		qu = new Question(0, 1, "qtitle", "radio", true, "option1;option2;option3");
		questionList.add(qu);
		result = quizService.create(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "id error");
		// qtitle ���~
		qu = new Question(1, 1, "", "radio", true, "option1;option2;option3");
		questionList.add(qu);
		result = quizService.create(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "qtitle error");

		// options_type ���~
		qu = new Question(1, 1, "qtitle", "", true, "option1;option2;option3");
		questionList.add(qu);
		result = quizService.create(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "options_type error");

		// options���~
		qu = new Question(1, 1, "qtitle", "radio", true, "");
		questionList.add(qu);
		result = quizService.create(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "options error");

		///////////////////

		// ���T�s�W
		questionList = new ArrayList<>();
		questionnaire = new Questionnaire("Quiz", true, "ABCDE", LocalDate.of(2023, 11, 19), LocalDate.of(2023, 12, 1));
		for (int i = 1; i <= 5; i++) {
			qu = new Question(i, 1, "QuTitle" + i, "Text" + i, true, "Option" + i);
			questionList.add(qu);
		}
		result = quizService.create(new QuizReq(questionnaire, questionList));
		System.out.println(result.getRtnCode());
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "create error");

	}

	@Test
	public void updateTest() {
		Questionnaire questionnaire = new Questionnaire();
		List<Question> questionList = new ArrayList<>();

		// ���~
		// ��諸�ɾ� �G ���o��+�w�o���|���}�l ( ��e�ɶ� < �}�l�ɶ� )
		// �w�o���w�}�l�����
		// qn�Gid�Btitle���~�B�������~�B�}�l�ɶ��B�����ɶ��B�}�l>����
		// qu�Gid�Bquid == id �B qtitle�B�ﶵ�����B�ﶵ����

		//////////////////////

		// �w�o���w�}�l
		questionnaire = new Questionnaire(28, "publishedTest", true, "publishedAndStarted", LocalDate.of(2023, 11, 10),
				LocalDate.of(2024, 1, 10));
		QuizRes result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "Date error");
		//////////////////////

		// qn�Gid <=0 || �S�o��ID
		questionnaire = new Questionnaire(0, "idText", true, "idText", LocalDate.of(2023, 11, 20),
				LocalDate.of(2024, 1, 10));
		result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "id error");

		// qn id not fount
		questionnaire = new Questionnaire(50, "idText", true, "idText", LocalDate.of(2023, 11, 20),
				LocalDate.of(2024, 1, 10));
		result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "id not found");
		// qn title ���~
		questionnaire = new Questionnaire(30, "", true, "TitleText", LocalDate.of(2023, 11, 20),
				LocalDate.of(2024, 1, 10));
		result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "title error");

		// qn �������~
		questionnaire = new Questionnaire(30, "descriptionTest", true, "", LocalDate.of(2023, 11, 20),
				LocalDate.of(2024, 1, 10));
		result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "description error");

		// qn �}�l�ɶ�
		questionnaire = new Questionnaire(30, "startTest", true, "startTestdesc", null, LocalDate.of(2024, 1, 10));
		result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "startDate error");
		// qn �����ɶ�
		questionnaire = new Questionnaire(30, "endTest", true, "endTestdesc", LocalDate.of(2023, 11, 20), null);
		result = quizService.update(new QuizReq(questionnaire, questionList));
		result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "endDate error");

		// qn �}�l�ɶ�>�����ɶ�
		questionnaire = new Questionnaire(30, "DateTest", true, "DateTestDesc", LocalDate.of(2025, 11, 10),
				LocalDate.of(2024, 1, 10));
		result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "endDate error");

		//////////////////////
		// qu id 1.id<=0 2.id���s�b
		// 1.id<=0
		questionnaire = new Questionnaire(25, "questionTest", false, "QuestionIdTestDesc", LocalDate.of(2023, 11, 30),
				LocalDate.of(2024, 1, 10));
		Question question1 = new Question(0, 25, "qtitleXX", "textX", false, "OPOPTIONA");
		questionList = new ArrayList<>(Arrays.asList(question1));
		result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "qu id error");

		// 2.id���s�b
		questionnaire = new Questionnaire(25, "questionTest", false, "QuestionIdTestDesc", LocalDate.of(2023, 11, 30),
				LocalDate.of(2024, 1, 10));
		question1 = new Question(15, 25, "qtitleXX", "textX", false, "OPOPTIONA");
		questionList = new ArrayList<>(Arrays.asList(question1));
		result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "qu id not found");

		// qu qnid != id
		questionnaire = new Questionnaire(25, "questionTest", false, "QuestionIdTestDesc", LocalDate.of(2023, 11, 30),
				LocalDate.of(2024, 1, 10));
		question1 = new Question(1, 20, "qtitleXX", "textX", false, "OPOPTIONA");
		questionList = new ArrayList<>(Arrays.asList(question1));
		result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "qnid != id error");

		// qtitle ���~
		questionnaire = new Questionnaire(25, "questionTest", false, "QuestionIdTestDesc", LocalDate.of(2023, 11, 30),
				LocalDate.of(2024, 1, 10));
		question1 = new Question(1, 25, "", "textX", false, "OPOPTIONA");
		questionList = new ArrayList<>(Arrays.asList(question1));
		result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "qu title error");

		// option_type ���~
		questionnaire = new Questionnaire(25, "questionTest", false, "QuestionIdTestDesc", LocalDate.of(2023, 11, 30),
				LocalDate.of(2024, 1, 10));
		question1 = new Question(1, 25, "qtitle", "", false, "OPOPTIONA");
		questionList = new ArrayList<>(Arrays.asList(question1));
		result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "qu option_type error");

		// option ���~
		questionnaire = new Questionnaire(25, "questionTest", false, "QuestionIdTestDesc", LocalDate.of(2023, 11, 30),
				LocalDate.of(2024, 1, 10));
		question1 = new Question(1, 25, "qtitle", "qoptiontype", false, "");
		questionList = new ArrayList<>(Arrays.asList(question1));
		result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "qu option error");

		//////////////////////

		// ���`��� ��e����Ƥ@�ˤ]�i���`���

		questionnaire = new Questionnaire(29, "QuizXX", false, "WXYZ", LocalDate.of(2023, 11, 30),
				LocalDate.of(2024, 1, 10));
		question1 = new Question(1, 29, "qtitleXXX", "textX", false, "OPOPTIONA");
		Question question2 = new Question(2, 29, "qtitleYYY", "textY", false, "OPOPTIONB");
		Question question3 = new Question(3, 29, "qtitleZZZ", "textZ", false, "OPOPTIONC");
		questionList = new ArrayList<>(Arrays.asList(question1, question2, question3));
		result = quizService.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "update error");
		System.out.println(result.getRtnCode());

	}

	@Test
	public void deleQuestionnaireTest() {
		// �R�h�i�ݨ� �u�� �|���o�� + �|���}��(��e��� < �}�l���) �i�R
		// �o�G�N���i�R
		// 1. �w�o�����}�� 2.�w�o���w�}��
		// ���@��int ��List

		// �R����Ʈw�̨S����
		ArrayList<Integer> deleIdList = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
		QuizRes result = quizService.deleQuestionnaire(deleIdList);
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "id not found");
		// �R����Ʈw�� �w�}��
		deleIdList = new ArrayList<Integer>(Arrays.asList(26, 28, 30));
		result = quizService.deleQuestionnaire(deleIdList);
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "id not found");
	}

	@Test
	public void deleQuestionTest() {
		// �n�R���ݨ� int deleqnid �n�R���D�� List<int> delequid

		// �R���ݨ��S�����D��
		int deleQnId = 25;
		ArrayList<Integer> deleQuIdList = new ArrayList<Integer>(Arrays.asList(1, 5));
		QuizRes result = quizService.deleQuestion(deleQnId, deleQuIdList);
		System.out.println(result.getRtnCode());
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "id not found");

		// �R�����ݨ��w�}��
		deleQnId = 26;
		deleQuIdList = new ArrayList<Integer>(Arrays.asList(1, 5));
		result = quizService.deleQuestion(deleQnId, deleQuIdList);
		System.out.println(result.getRtnCode());
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "not published");

		// ���`�R��
//		deleQnId = 25;
//		deleQuIdList = new ArrayList<Integer>(Arrays.asList(1,5));
//		result = quizService.deleQuestion(deleQnId, deleQuIdList);
//		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL,"deleQuestion error");

	}

	@Test
	public void searchTest() {
		// �j ���D �}�l�ɶ� �����ɶ� �i�U�ۿW�߷j
		String title;
		LocalDate start;
		LocalDate end;
		QuizRes result;
		List<QuizVo> resList;
		////////////////////////////////////////////

		// �L�j�M���G
		title = "ABC";
		start = LocalDate.of(2023, 11, 1);
		end = LocalDate.of(2024, 1, 1);
		result = quizService.search(title, start, end);
		resList = result.getQuizVoList();
		System.out.println("==");
		for (QuizVo vo : resList) {
			System.out.println(vo.getQuestionnaire().getId());
		}
		System.out.println("==");
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");
//		System.out.println(result.getRtnCode());

		////////////////////////////////////////////
		// ���`�j�M
		// ���D �}�l�ɶ� �����ɶ�
		title = "u";
		start = LocalDate.of(2023, 11, 10);
		end = LocalDate.of(2024, 1, 10);
		result = quizService.search(title, start, end);
		resList = result.getQuizVoList();
		for (QuizVo vo : resList) {
			System.out.println(vo.getQuestionnaire().getId());
		}
		System.out.println("");
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");

		// ���D
		title = "Quiz";
		start = null;
		end = null;
		result = quizService.search(title, start, end);
		resList = result.getQuizVoList();
		for (QuizVo vo : resList) {
			System.out.println(vo.getQuestionnaire().getId());
		}
		System.out.println("");
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");

		// ���D �}�l�ɶ�
		title = "Quiz";
		start = LocalDate.of(2023, 11, 1);
		end = null;
		result = quizService.search(title, start, end);
		resList = result.getQuizVoList();
		for (QuizVo vo : resList) {
			System.out.println(vo.getQuestionnaire().getId());
		}
		System.out.println("");
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");

		// ���D �����ɶ�
		title = "Quiz";
		start = null;
		end = LocalDate.of(2024, 1, 1);
		result = quizService.search(title, start, end);
		resList = result.getQuizVoList();
		for (QuizVo vo : resList) {
			System.out.println(vo.getQuestionnaire().getId());
		}
		System.out.println("");
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");

		// �}�l�ɶ� �����ɶ�
		title = "";
		start = LocalDate.of(2023, 11, 1);
		end = LocalDate.of(2024, 1, 1);
		result = quizService.search(title, start, end);
		resList = result.getQuizVoList();
		for (QuizVo vo : resList) {
			System.out.println(vo.getQuestionnaire().getId());
		}
		System.out.println("");
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");

		// �L��
		title = "";
		start = null;
		end = null;
		result = quizService.search(title, start, end);
		resList = result.getQuizVoList();
		for (QuizVo vo : resList) {
			System.out.println(vo.getQuestionnaire().getId());
		}
		System.out.println("");
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");

	}

	@Test
	public void searchQuestionnaireTest() {
		// �e�x�j�M �u�j��w�}��
		QuestionnaireRes result = quizService.searchQuestionnaireList("", null, null, true);
		for (Questionnaire qu : result.getQuestionnaire()) {
			System.out.println(qu.getId());
		}
		System.out.println(result.getRtnCode());
	}

	@Test
	public void searchQuestionTest() {
		// �e�x�j�M �ݨ��̪��Ҧ��D�� �u�j��w�}��

		// ���`�j�M
		QuestionRes result = quizService.searchQuestionList(30);
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL,"search error");
		for (Question qu : result.getQuestionList()) {
			System.out.println(qu.getQuId() + " " + qu.getqnId() + " " + qu.getqTitle());
		}
//		System.out.println(result.getRtnCode());

		// �j�M���bDB�̪��ݨ�  --> ���\�j�M�A�L���G���
		result = quizService.searchQuestionList(3);
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL,"search error");
		for (Question qu : result.getQuestionList()) {
			System.out.println(qu.getQuId() + " " + qu.getqnId() + " " + qu.getqTitle());
		}
//		System.out.println(result.getRtnCode());
		
		// id��J���~
		result = quizService.searchQuestionList(0);
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL,"id error");
//		for (Question qu : result.getQuestionList()) {
//			System.out.println(qu.getQuId() + " " + qu.getqnId() + " " + qu.getqTitle());
//		}
//		System.out.println(result.getRtnCode());
	}
}
