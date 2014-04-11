package tvweb2.strategy.render;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import tvweb2.BaseServlet;
import tvweb2.jpa.User;
import tvweb2.strategy.BaseStgy;

public class ViewPage implements BaseStgy {
	public static ArrayList<Class<? extends User>> auths;
	public BaseServlet servlet;


	public ViewPage(BaseServlet s) {
		super();
		servlet = s;
	}

	@Override
	public boolean authenticate() {
		return true;
	}

	@Override
	public void execute() throws IOException {
		
		
		Template t = new Template("vmfiles/Viewpage.vm", servlet);
		
		String type = servlet.getRequest().getParameter("type");
		
		if(type == null || type.isEmpty())
			type = "elapsed";

		
		
		List<User> users = servlet.getUserservice().findAllUsers();
		List<Hashtable<String, Object>> ht_us = new ArrayList<Hashtable<String, Object>>();
		for(User u :users){
			Hashtable<String, Object> ht_u = User.getHashtable(u);
			ht_us.add(ht_u);
		}
		t.put("users", ht_us);
		t.put("type", type);
		servlet.print(t.render());
		
	}
}
