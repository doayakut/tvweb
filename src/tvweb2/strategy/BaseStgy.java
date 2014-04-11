package tvweb2.strategy;

import java.io.IOException;
import java.util.ArrayList;

import tvweb2.BaseServlet;
import tvweb2.jpa.User;

public interface BaseStgy {
	
	public ArrayList<Class<User>> auths = null;
	public BaseServlet root = null; 

	public boolean authenticate();
	public void execute() throws IOException;
	
}
