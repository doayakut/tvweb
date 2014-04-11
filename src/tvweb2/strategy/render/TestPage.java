package tvweb2.strategy.render;

import java.io.IOException;
import java.util.ArrayList;

import tvweb2.BaseServlet;
import tvweb2.jpa.User;
import tvweb2.strategy.BaseStgy;

public class TestPage implements BaseStgy {
	public static ArrayList<Class<? extends User>> auths;
	public BaseServlet servlet;


	public TestPage(BaseServlet s) {
		super();
		servlet = s;
	}

	@Override
	public boolean authenticate() {
		return true;
	}

	@Override
	public void execute() throws IOException {
		
		Template t = new Template("vmfiles/Test.vm", servlet);
		servlet.print(t.render());
		
	}

}
