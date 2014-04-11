package tvweb2.strategy.render;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import tvweb2.BaseServlet;
import tvweb2.jpa.User;
import tvweb2.jpa.enums.Method;
import tvweb2.jpa.enums.Task;
import tvweb2.jpa.service.UserService;
import tvweb2.strategy.BaseStgy;

public class ExpPage implements BaseStgy {
	public static ArrayList<Class<? extends User>> auths;
	public BaseServlet servlet;

	public ExpPage(BaseServlet s) {
		super();
		servlet = s;
		auths = new ArrayList<Class<? extends User>>();
		auths.add(User.class);
	}

	@Override
	public boolean authenticate() {
		if (servlet.getUser() != null)
			return true;
		else
			return false;
	}

	@Override
	public void execute() throws IOException {
		if (servlet.getUser().isCompleted()) {
			servlet.redirect("/home");
			return;
		}
		// Check if it wants url if yes give it to him
		// else render start page
		String url = servlet.getRequest().getParameter("url");
		if (url != null && !url.isEmpty()) {
			servlet.redirect(url);
		} else {

			Template t = new Template("vmfiles/Exppage.vm", servlet);
			/* fetching person hashtables */
			Hashtable<String, Object> ht_u = User.getHashtable((User) servlet
					.getUser());
			User u = servlet.getUser();

			if (u.isCompleted())
				servlet.redirect("/home");

			User.State s = u.getState();

			String filepath = "";
			String question = "";
			String definition = "";
			String pageinfo = "";

			UserService us = servlet.getUserservice();

			Method m = us.getCurrMethod(u);
			Task task = us.getCurrTask(u);

			
			t.put("method", m);
			System.out.println("Method : " + m);


			int learnIndex = u.getLearning();
			if (s != User.State.TRAINING) {

				if (learnIndex < 2 && m == Method.PERSONALIZED) {
					task = us.getTask(u, u.getCurr() + learnIndex, u.getState());
				}
			}

			int curr = u.getCurr();

			switch (s) {
			case TRAINING:

				filepath = "";
				question = "";
				definition = "";

				if (m == Method.REGULAR) {
					filepath = task.getParent().getFolder() + "/index.html";
					question = task.getQuestionNormal();
					definition = task.getDefNormal();
				}
				if (m == Method.TREEVIEW) {
					filepath = task.getParent().getFolder() + "/treeview.html";
					question = task.getQuestionTraing();
					definition = task.getDefNormal();
				}
				if (m == Method.PERSONALIZED) {
					filepath = task.getParent().getFolder() + "/personalized.html";
					question = task.getQuestionPerson();
					definition = task.getDefPerson();
				}
				filepath = u.getType().toString().substring(0, 1).toLowerCase()
						+ "-t" + filepath;

				pageinfo = "Method: " + m.toString() + "<br/>";
				pageinfo += "Complexity: " + task.getParent().getType().toString()
						+ "<br/>";

				t.put("state", s);

				t.put("user", ht_u);
				t.put("question", question);
				t.put("definition", definition);
				t.put("filepath", filepath);

				t.put("pageinfo", pageinfo);
				break;
			case INFO:
				t.put("state", s);
				if (curr == 0)
					t.put("text", "Mobile Web System 1");
				if (curr == 2)
					t.put("text", "Mobile Web System 2");
				if (curr == 4)
					t.put("text", "Mobile Web System 3");
				if (curr == 6)
					t.put("text", "Mobile Web System 4");
				if (curr == 8)
					t.put("text", "Mobile Web System 5");
				if (curr == 10)
					t.put("text", "Mobile Web System 6");
				break;

			case TASKS:
				// Three variables:
				// Which task are we on?
				// Which type of viewing method are we using?
				// - What is the question?
				// - What is the filename?

				filepath = "";
				question = "";
				definition = "";

				if (m == Method.REGULAR) {
					filepath = task.getParent().getFolder() + "/index.html";
					question = task.getQuestionNormal();
					definition = task.getDefNormal();
				}
				else if (m == Method.TREEVIEW) {
					filepath = task.getParent().getFolder() + "/treeview.html";
					question = task.getQuestionNormal();
					definition = task.getDefNormal();
				
				}
				else if (m == Method.PERSONALIZED) {
					if(learnIndex < 2){
						filepath = task.getParent().getFolder() + "t/treeview.html";
						question = task.getQuestionTraing();
						definition = task.getDefPerson();
					}
					else {
						filepath = task.getParent().getFolder() + "/personalized.html";
						question = task.getQuestionPerson();
						definition = task.getDefPerson();
					}
				}
				filepath = u.getType().toString().substring(0, 1).toLowerCase()
						+ "-" + filepath;

				pageinfo = "Method: " + m.toString() + "<br/>";
				pageinfo += "Complexity: " + task.getParent().getType().toString()
						+ "<br/>";

				t.put("state", s);

				t.put("user", ht_u);
				t.put("question", question);
				t.put("definition", definition);
				t.put("filepath", filepath);

				t.put("pageinfo", pageinfo);
				break;
			case QUESTIONNAIRE:
				t.put("state", s);
				t.put("text", "Fill out questionnaire before continuing");
				break;
			default:
				break;

			}
			servlet.print(t.render());
		}

	}

}
