package tvweb2.strategy.render;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import tvweb2.BaseServlet;
import tvweb2.jpa.User;
import tvweb2.strategy.BaseStgy;

public class HomePage implements BaseStgy {
	public static ArrayList<Class<? extends User>> auths;
	public BaseServlet servlet;

	public HomePage(BaseServlet s) {
		super();
		servlet = s;
	}

	@Override
	public boolean authenticate() {
		return true;
	}

	@Override
	public void execute() throws IOException {
		Template t = new Template("vmfiles/Home.vm", servlet);

		
		/* fetching person hashtables */
		Hashtable<String, Object> ht_u = User.getHashtable((User)servlet.getUser());

		t.put("user", ht_u);
		t.put("pagetype", servlet.getRequest().getParameter("type"));
		t.put("max", 12);
			
		
		servlet.print(t.render());
		
	}

}
