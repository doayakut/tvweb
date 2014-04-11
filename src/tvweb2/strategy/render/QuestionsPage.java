package tvweb2.strategy.render;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import tvweb2.BaseServlet;
import tvweb2.jpa.User;
import tvweb2.strategy.BaseStgy;

public class QuestionsPage implements BaseStgy {
	public static ArrayList<Class<? extends User>> auths;
	public BaseServlet servlet;

	public QuestionsPage(BaseServlet s) {
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
		Template t = new Template("vmfiles/Questions.vm", servlet);

		/* fetching person hashtables */
		Hashtable<String, Object> ht_u = User.getHashtable((User)servlet.getUser());

		t.put("user", ht_u);

		servlet.print(t.render());
	}

}
