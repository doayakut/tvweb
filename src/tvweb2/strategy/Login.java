package tvweb2.strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import tvweb2.BaseServlet;
import tvweb2.BaseServlet.MessageType;
import tvweb2.jpa.User;
import tvweb2.jpa.service.UserService;

public class Login implements BaseStgy {
	public static ArrayList<Class<? extends User>> auths;
	public BaseServlet servlet;
	
	private User user = null;

	public Login(BaseServlet s) {
		super();
		servlet = s;
	}

	@Override
	public boolean authenticate() {
		return true;
	}

	@Override
	public void execute() throws IOException {
		
		String name = servlet.getRequest().getParameter("name").toLowerCase();
		String type = servlet.getRequest().getParameter("type").toUpperCase();
		String redirect = servlet.getRequest().getParameter("redirect");
		
		user = getUserFromDB(name, type);
		
		if(user == null) { // Create a new user
			user = servlet.getUserservice().createUser(servlet.getExperiment().getOrder());
			servlet.getExpservice().updateOrders(servlet.getExperiment());

			user.setCurr(0);
			user.setType(User.Type.valueOf(type));
			
			if(user != null) {
				UserService userservice = servlet.getUserservice();
				userservice.setUserUsername(user, name);
			}
			else{
				servlet.redirect("/home", "Can not create user", MessageType.ERROR);
				return;
			}
		}
		else{
		}
		
		
		
		HttpSession session = servlet.getRequest().getSession();
		
		if (session.getAttribute("user") != null)
			session.removeAttribute("user");

		session.setAttribute("user", user);
		if(redirect != null && !redirect.isEmpty())
			servlet.redirect("/home?action=questions");
			
		servlet.redirect("/home?action=exp");

	}
	
	private User getUserFromDB(String name, String type) {
		
		
		String jpql = "select u from User u where u.name = :name and u.type = :type";
		Query q= servlet.getEntityManager().createQuery(jpql);
		
		q.setParameter("name", name);
		q.setParameter("type", User.Type.valueOf(type));
		
		@SuppressWarnings("unchecked")
		List<User> users =  (List<User>) q.getResultList();
		
		if(!users.isEmpty())
			return users.get(0);
		else 
			return null;
	}


}
