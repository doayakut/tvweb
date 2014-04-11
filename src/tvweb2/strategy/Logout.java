package tvweb2.strategy;

import java.io.IOException;
import java.util.ArrayList;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
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
		String type = servlet.getRequest().getParameter("type") ;
		
		if (type != null && type.equalsIgnoreCase("questions"))
			servlet.redirect("/home?type=questions");
		else 
			servlet.redirect("/home");

	}

}
