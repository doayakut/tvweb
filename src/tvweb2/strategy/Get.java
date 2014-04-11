package tvweb2.strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import tvweb2.BaseServlet;
import tvweb2.jpa.User;
import tvweb2.jpa.enums.Method;
import tvweb2.jpa.enums.Task;
import tvweb2.strategy.render.Template;

public class Get implements BaseStgy {
	public static ArrayList<Class<? extends User>> auths;
	public BaseServlet servlet;

	private User user = null;
	private String what;

	public Get(BaseServlet s) {
		super();
		servlet = s;
	}

	@Override
	public boolean authenticate() {
		return true;
	}

	@Override
	public void execute() throws IOException {

		what = servlet.getRequest().getParameter("what");
		if (what == null)
			what = "question";

		if (what.equalsIgnoreCase("question")) {


			Template t = new Template("vmfiles/Exppage.vm", servlet);
			/* fetching person hashtables */
			
			User u = servlet.getUser();

			if(u.isCompleted())
				servlet.redirect("/home");
			
			User.State s = u.getState();

			String question = "";
			String definition = "";

			Method m = servlet.getUserservice().getCurrMethod(u);
			Task task = servlet.getUserservice().getCurrTask(u);
			
			
			int learnIndex = u.getLearning();
			if (s != User.State.TRAINING) {

				if (learnIndex < 2 && m == Method.PERSONALIZED) {
					task = servlet.getUserservice().getTask(u,
							u.getCurr() + learnIndex, u.getState());
				}
			}

			switch (s) {
			case TRAINING:

				question = "";
				definition = "";

				if (m == Method.REGULAR) {
					question = task.getQuestionNormal();
					definition = task.getDefNormal();
				}
				if (m == Method.TREEVIEW) {
					question = task.getQuestionTraing();
					definition = task.getDefPerson();
				}
				if (m == Method.PERSONALIZED) {
					question = task.getQuestionPerson();
					definition = task.getDefPerson();
				}


				t.put("question", question);
				t.put("definition", definition);
				
				break;
			case TASKS:
				// Three variables:
				// Which task are we on?
				// Which type of viewing method are we using?
				// - What is the question?
				// - What is the filename?

				question = "";
				definition = "";

				if (m == Method.REGULAR) {
					question = task.getQuestionNormal();
					definition = task.getDefNormal();
				}
				if (m == Method.TREEVIEW) {
					question = task.getQuestionNormal();
					definition = task.getDefNormal();
				}
				if (m == Method.PERSONALIZED) {
					if (learnIndex < 2) {
						question = task.getQuestionTraing();
						definition = task.getDefPerson();
					} else {
						question = task.getQuestionPerson();
						definition = task.getDefPerson();
					}
				}


				t.put("question", question);
				t.put("definition", definition);

				break;
			default:
				break;

			}
			t.put("state", "QUESTION");
			servlet.print(t.render());

		}
		if (what.equalsIgnoreCase("user")) {

			String name = servlet.getRequest().getParameter("username");
			String type = servlet.getRequest().getParameter("type");
			user = getUserFromDB(name, type);
			if (user == null)
				servlet.getResponse().getWriter()
						.write("Not a user:" + name + "\n");
			else {
				if (user.getPrestudy().getAge() != null) {
					servlet.getResponse().getWriter()
							.write("questionnaire exists");
				} else {
					servlet.getResponse().getWriter().write("success");
				}
			}

		}
	}

	private User getUserFromDB(String name, String type) {

		String jpql = "select u from User u where u.name = :name and u.type = :type";
		Query q = servlet.getEntityManager().createQuery(jpql);

		q.setParameter("name", name);
		q.setParameter("type", User.Type.valueOf(type.toUpperCase()));

		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) q.getResultList();

		if (!users.isEmpty())
			return users.get(0);
		else
			return null;
	}
}
