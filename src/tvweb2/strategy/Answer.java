package tvweb2.strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import tvweb2.BaseServlet;
import tvweb2.jpa.User;
import tvweb2.jpa.enums.Page;
import tvweb2.jpa.enums.Task;
import tvweb2.jpa.service.EvaluationService;

public class Answer implements BaseStgy {
	public static ArrayList<Class<? extends User>> auths;
	public BaseServlet servlet;
	private EvaluationService es;

	private User user;

	public Answer(BaseServlet s) {
		super();
		servlet = s;
		es = servlet.getEvalservice();
	}

	@Override
	public boolean authenticate() {
		return true;
	}

	@Override
	public void execute() throws IOException {
		// Get username check if the user is created
		// userid
		user = getUser();
		System.out.println(servlet.getQuery());
		// sendAnswers();
		servlet.close();
		
		servlet.open();
		submitPreStudyAnswers(user);
		servlet.close();
		System.out.println("Pre-study committed");
		
		servlet.open();
		submitFamiliarityAnswers(user);
		servlet.close();
		System.out.println("Familiarity committed");
		
		servlet.open();
		submitTaskAnswers(user);
		servlet.close();
		System.out.println("Task committed");
		
		servlet.open();
		submitQuestionnaireAnswers(user);
		servlet.close();
		System.out.println("Questionnaire committed");
		
		servlet.open();
		submitExperienceAnswers(user);
		servlet.close();
		System.out.println("Experinece committed");
		
		servlet.open();
		submitScreenshotAnswers(user);
		servlet.close();
		System.out.println("Screenshots committed");
		servlet.open();
		
		servlet.getResponse().getWriter().print("success");
		

	}

	private void submitScreenshotAnswers(User user) {
		// pref[1-9]_[1-3] (1-7)
		for (int i = 1; i <= Page.length; i++) {
			String val1 = getAnswer("pref" + i + "_1");
			String val2 = getAnswer("pref" + i + "_2");
			String val3 = getAnswer("pref" + i + "_3");
			es.setScreenshotValues(user.getScreenshots(), i, val1, val2, val3);
			servlet.getEntityManager().merge(user.getScreenshots());
		}
		return;
	}

	private void submitExperienceAnswers(User user) {
		es.setExperienceAnswers(user.getExperience(),
				getAnswer("experience_1"), getAnswer("experience_2"));

		servlet.getEntityManager().merge(user.getExperience());
	}

	private void submitQuestionnaireAnswers(User user) {
		int question_count = 17;
		// questionnaire[1-6]_[1-17] (1-7)
		String answer;
		for (int j = 1; j <= question_count; j++) {
			answer = getAnswer("questionnaire1_" + j);
			if (answer.isEmpty())
				answer = "-1";
			es.setQuesAnswerValues(user.getQuestionnaire1(), j,
					Float.valueOf(answer));
		}
		answer = getAnswer("questionnaire1_feedback");
		es.setQuesFeedback( user.getQuestionnaire1(), answer); 
		
		servlet.getEntityManager().merge(user.getQuestionnaire1());
		for (int j = 1; j <= question_count; j++) {
			answer = getAnswer("questionnaire2_" + j);
			if (answer.isEmpty())
				answer = "-1";
			es.setQuesAnswerValues(user.getQuestionnaire2(), j,
					Float.valueOf(answer));
		}
		answer = getAnswer("questionnaire2_feedback");
		es.setQuesFeedback( user.getQuestionnaire2(), answer);
		
		servlet.getEntityManager().merge(user.getQuestionnaire2());
		for (int j = 1; j <= question_count; j++) {
			answer = getAnswer("questionnaire3_" + j);
			if (answer.isEmpty())
				answer = "-1";
			es.setQuesAnswerValues(user.getQuestionnaire3(), j,
					Float.valueOf(answer));
		}
		answer = getAnswer("questionnaire3_feedback");
		es.setQuesFeedback( user.getQuestionnaire3(), answer);
		
		servlet.getEntityManager().merge(user.getQuestionnaire3());
		for (int j = 1; j <= question_count; j++) {
			answer = getAnswer("questionnaire4_" + j);
			if (answer.isEmpty())
				answer = "-1";
			es.setQuesAnswerValues(user.getQuestionnaire4(), j,
					Float.valueOf(answer));
		}
		answer = getAnswer("questionnaire4_feedback");
		es.setQuesFeedback( user.getQuestionnaire4(), answer);
		
		servlet.getEntityManager().merge(user.getQuestionnaire4());
		for (int j = 1; j <= question_count; j++) {
			answer = getAnswer("questionnaire5_" + j);
			if (answer.isEmpty())
				answer = "-1";
			es.setQuesAnswerValues(user.getQuestionnaire5(), j,
					Float.valueOf(answer));
		}
		answer = getAnswer("questionnaire5_feedback");
		es.setQuesFeedback( user.getQuestionnaire5(), answer);
		
		servlet.getEntityManager().merge(user.getQuestionnaire5());
		for (int j = 1; j <= question_count; j++) {
			answer = getAnswer("questionnaire6_" + j);
			if (answer.isEmpty())
				answer = "-1";
			es.setQuesAnswerValues(user.getQuestionnaire6(), j,
					Float.valueOf(answer));
		}
		answer = getAnswer("questionnaire6_feedback");
		es.setQuesFeedback( user.getQuestionnaire6(), answer);
		
		
		servlet.getEntityManager().merge(user.getQuestionnaire6());
		
		

	}

	private void submitTaskAnswers(User user) {
		// familiarity_[1-18] (string)
		for (int i = 1; i <= Task.length; i++) {
			es.setTaskAnswerAnswer(user.getTaskAnswers(), i,
					getAnswer("answer_" + i));
		}
		servlet.getEntityManager().merge(user.getTaskAnswers());
	}

	private void submitFamiliarityAnswers(User user) {
		// familiarity_[1-9] (1-7)
		for (int i = 1; i <= 6; i++) {

			String answer = getAnswer("familiarity_" + i);
			if (answer.isEmpty())
				answer = "-1";
			es.setFamiliarityValue(user.getFamiliarity(), i,
					Float.valueOf(answer));
		}

		servlet.getEntityManager().merge(user.getFamiliarity());
	}

	private void submitPreStudyAnswers(User user) {
		String answer, answer1, answer2, answer3;

		servlet.getResponse();
		// Get prestudy answers
		// age (1-5)
		answer = getAnswer("age");
		es.setPreStudyAge(user.getPrestudy(), answer);

		// gender (1-2)
		answer = getAnswer("gender");
		es.setPreStudyGender(user.getPrestudy(), answer);
		// education (1-4 or string)
		answer = getAnswer("edu");
		if (answer.equals("0"))
			answer = getAnswer("edu_str");
		es.setPreStudyEdu(user.getPrestudy(), answer);

		// experience (1-5)
		answer = getAnswer("exp");
		es.setPreStudyExp(user.getPrestudy(), answer);

		// owner (1-2)
		answer = getAnswer("owner");
		es.setPreStudyOwner(user.getPrestudy(), answer);

		// frequency (1-5)
		answer = getAnswer("fre");
		es.setPreStudyFreq(user.getPrestudy(), answer);

		// aims : 1 (1-5 or string)
		answer1 = getAnswer("aim1");
		if (answer1.equals("0"))
			answer1 = getAnswer("aim1_str");
		// aims : 2 (1-5 or string)
		answer2 = getAnswer("aim2");
		if (answer2.equals("0"))
			answer2 = getAnswer("aim2_str");
		// aims : 3 (1-5 or string)
		answer3 = getAnswer("aim3");
		if (answer3.equals("0"))
			answer3 = getAnswer("aim3_str");
		es.setPreStudyAim(user.getPrestudy(), answer1, answer2, answer3);

		// tool (1-3)
		answer = getAnswer("tool");
		es.setPreStudyTools(user.getPrestudy(), answer);

		// problems (string)
		answer = getAnswer("probs");
		es.setPreStudyProbs(user.getPrestudy(), answer);

		// solutions (string)
		answer = getAnswer("sols");
		es.setPreStudySols(user.getPrestudy(), answer);

		// disabilities (string)
		answer = getAnswer("diss");
		es.setPreStudyDisabilities(user.getPrestudy(), answer);
		
		// previous (string)
		answer = getAnswer("when");
		es.setPreStudyBlindSince(user.getPrestudy(), answer);

		servlet.getEntityManager().merge(user.getPrestudy());

	}

	private User getUser() {
		String user_str = getAnswer("userid").toLowerCase();

		String jpql = "select u from User u where u.name = :name";
		Query q = servlet.getEntityManager().createQuery(jpql);

		q.setParameter("name", user_str);

		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) q.getResultList();

		if (!users.isEmpty())
			return users.get(0);
		else
			return null;
	}

	private String getAnswer(String str) {
		String answer = servlet.getRequest().getParameter("tvweb_input_" + str);
		if (answer == null)
			answer = new String("");
		return answer;
	}

}
