package tvweb2.strategy;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import tvweb2.BaseServlet;
import tvweb2.jpa.User;

public class Logout implements BaseStgy {
	public static ArrayList<Class<? extends User>> auths;
	public BaseServlet servlet;


	public Logout(BaseServlet s) {
		super();
		servlet = s;
	}

	@Override
	public boolean authenticate() {
		return true;
	}


	@Override
	public void execute() throws IOException {
		HttpSession session = servlet.getRequest().getSession();
		session.removeAttribute("user");
	    servlet.redirect("/home");

	}

}
