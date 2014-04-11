package tvweb2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tvweb2.strategy.render.RenderData;
import tvweb2.strategy.*;
import tvweb2.strategy.render.*;

@SuppressWarnings("serial")
public class SingleServlet extends BaseServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
		return;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
		return;
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (!initBase(req, resp))
			return;

		try {
			BaseStgy strategy = null;
			System.out.println("request: " + action);
			if (action == null)
				action = "home";

			if (action.compareToIgnoreCase("home") == 0) {
				strategy = new HomePage(this);
			}

			if (action.compareToIgnoreCase("login") == 0) {
				strategy = new Login(this);
			}

			if (action.compareToIgnoreCase("logout") == 0) {
				strategy = new Logout(this);
			}

			if (action.compareToIgnoreCase("exp") == 0) {
				strategy = new ExpPage(this);
			}

			if (action.compareToIgnoreCase("test") == 0) {
				strategy = new TestPage(this);
			}

			if (action.compareToIgnoreCase("next") == 0) {
				strategy = new Next(this);
			}

			if (action.compareToIgnoreCase("view") == 0) {
				strategy = new ViewPage(this);
			}
			
			if (action.compareToIgnoreCase("get") == 0) {
				strategy = new Get(this);
			}

			if (action.compareToIgnoreCase("answer") == 0) {
				strategy = new Answer(this);
			}

			if (action.compareToIgnoreCase("export") == 0) {
				strategy = new Export(this);
			}

			if (action.compareToIgnoreCase("questions") == 0) {
				strategy = new QuestionsPage(this);
			}

			if (action.compareToIgnoreCase("render") == 0) {
				strategy = new RenderData(this);
			}
			
			if (strategy != null) {
				if (!strategy.authenticate()) {
					this.redirect("/home");
					this.close();
					return;
				}

				strategy.execute();
				this.close();
				return;
			}

			this.redirect("/home");
			this.close();
		} catch (Exception e) {
			this.close();
			e.printStackTrace(this.getResponse().getWriter());
		}
		return;

	}

}
