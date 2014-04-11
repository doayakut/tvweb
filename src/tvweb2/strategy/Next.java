package tvweb2.strategy;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import tvweb2.BaseServlet;
import tvweb2.jpa.User;
import tvweb2.jpa.enums.Method;
import tvweb2.jpa.enums.Task;
import tvweb2.jpa.service.UserService;

public class Next implements BaseStgy {
	public static ArrayList<Class<? extends User>> auths;
	public BaseServlet servlet;

	public Next(BaseServlet s) {
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
		User user = servlet.getUser();

		UserService userservice = servlet.getUserservice();

		User.State state = user.getState();
		
		int curr = user.getCurr();
		int nextCurr = curr + 1;
		User.State nextState;
		
		switch (state) {
		case TRAINING:
			nextState = User.State.TRAINING;
			if (curr == 5) { // Reached end of training? (count = 6 where max will be 5)
				nextCurr = 0;
				nextState = User.State.INFO;
			}

			userservice.setUserTask(user, nextCurr);
			userservice.setUserState(user, nextState);
			servlet.getEntityManager().merge(user);
			break;
			
		case INFO:
			userservice.setUserState(user, User.State.TASKS);
			servlet.getEntityManager().merge(user);
			break;

		case TASKS:
			String url = servlet.getRequest().getParameter("url");

			if (url != null && !url.isEmpty()) {
				System.out.println("URL: " + url);

				if (user.getCurr() % 2 == 1)
					userservice
							.setUserState(user, User.State.QUESTIONNAIRE);

				float elapsed = Float.parseFloat(servlet.getRequest()
						.getParameter("elapsed")) / 1000;

				BigDecimal bd = new BigDecimal(elapsed);
				BigDecimal rounded = bd.setScale(3,
						BigDecimal.ROUND_HALF_UP);
				elapsed = rounded.floatValue();

				Task task = servlet.getUserservice().getCurrTask(user);
				System.out.println(task);

				
				// PERSONALIZED MIMIC LEARNING
				// if learning; increase learning or learned (3)
				
				if(user.getState() != User.State.TRAINING) {
					
					
					Method currM = servlet.getUserservice().getCurrMethod(user);
					int learnIndex = user.getLearning();
					
					
					if(currM == Method.PERSONALIZED){
						if(learnIndex < 2){
							servlet.getUserservice().setUserLearning(user, learnIndex + 1);
						}
						else {
							servlet.getUserservice().setUserTask(user, nextCurr);
							userservice.setUserElapsed(user, task, elapsed);
							if(user.getCurr() % 2 == 0){
								servlet.getUserservice().setUserLearning(user, 0);	
							}
						}
					}
					if(currM != Method.PERSONALIZED){
						userservice.setUserElapsed(user, task, elapsed);
						servlet.getUserservice().setUserTask(user, nextCurr);
					}

					
				}
				servlet.getEntityManager().merge(user);

			}
			break;

		case QUESTIONNAIRE:
			userservice.setUserState(user, User.State.INFO);
			if (user.getCurr() == Task.values().length) {
				servlet.redirect("/home");
				return;
			}
			servlet.getEntityManager().merge(user);
			break;
		}

		this.servlet.close();
		this.servlet.redirect("/home?action=exp");
		/*
		 * RequestDispatcher dispatcher =
		 * servlet.getRequest().getRequestDispatcher("/home?action=exp"); try {
		 * dispatcher.forward(servlet.getRequest(), servlet.getResponse()); }
		 * catch (ServletException e) { e.printStackTrace(); }
		 */

		return;
	}

}
