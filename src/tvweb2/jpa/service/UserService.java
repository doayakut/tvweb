package tvweb2.jpa.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import tvweb2.jpa.User;
import tvweb2.jpa.User.State;
import tvweb2.jpa.enums.Form;
import tvweb2.jpa.enums.Method;
import tvweb2.jpa.enums.FormOrder;
import tvweb2.jpa.enums.MethodOrder;
import tvweb2.jpa.enums.Page;
import tvweb2.jpa.enums.Task;
import tvweb2.jpa.evaluation.Experience;
import tvweb2.jpa.evaluation.Familiarity;
import tvweb2.jpa.evaluation.PreStudy;
import tvweb2.jpa.evaluation.Questionnaire;
import tvweb2.jpa.evaluation.Screenshots;
import tvweb2.jpa.evaluation.Tasks;

public class UserService {
	protected EntityManager em;
	protected UserTransaction ut;

	public UserService(EntityManager em) {
		this.em = em;
	}

	/*
	 * create User
	 */

	public User createUser(int order) {
		User emp = new User();

		emp.setMethodOrder(getMethodOrder(order));
		emp.setPageOrder(getPageOrder(order));
		emp.setState(User.State.TRAINING);
		emp.setLearning(0);

		PreStudy p = new PreStudy();
		emp.setPrestudy(p);
		// p.setUser(emp);

		Familiarity f = new Familiarity();
		emp.setFamiliarity(f);
		// f.setUser(emp);

		Tasks ta = new Tasks();
		emp.setTaskAnswers(ta);
		// ta.setUser(emp);

		Questionnaire qa;
		qa = new Questionnaire();
		emp.setQuestionnaire1(qa);
		// qa.setUser(emp);
		qa = new Questionnaire();
		emp.setQuestionnaire2(qa);
		// qa.setUser(emp);
		qa = new Questionnaire();
		emp.setQuestionnaire3(qa);
		
		qa = new Questionnaire();
		emp.setQuestionnaire4(qa);
		// qa.setUser(emp);
		qa = new Questionnaire();
		emp.setQuestionnaire5(qa);
		// qa.setUser(emp);
		qa = new Questionnaire();
		emp.setQuestionnaire6(qa);
		// qa.setUser(emp);

		Experience exp = new Experience();
		emp.setExperience(exp);
		// exp.setUser(emp);

		Screenshots ss = new Screenshots();
		emp.setScreenshots(ss);
		// ss.setUser(emp);

		emp.setCreateDate(new Date());
		
		em.persist(emp);
		return emp;
	}

	public void removeUser(User p) {
		em.remove(p);
	}

	/*
	 * set User password and email combination
	 */
	public User setUserUsername(User p, String val) {
		if (p != null) {
			p.setUsername(val);
		}
		return p;
	}

	public User setUserTask(User p, int val) {
		if (p != null) {
			p.setCurr(val);
		}
		return p;
	}

	public User setUserElapsed(User p, Task task, float val) {

		if (p != null) {
			p.addElapsedTime(task, val);
		}
		return p;

	}

	public User setUserState(User user, State state) {

		if (user != null) {
			user.setState(state);
		}
		return user;

	}

	public User setUserLearning(User user,int l) {
		if (user != null) {
			user.setLearning(l);
		}
		return user;
		
	}

	/*
	 * find User
	 */

	public User findUser(long id) {
		return em.find(User.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		Query query = em.createQuery("SELECT e FROM User e");
		return (List<User>) query.getResultList();
	}

	private MethodOrder getMethodOrder(int currOrder) {
		return MethodOrder.values()[currOrder % 6];
	}

	private FormOrder getPageOrder(int currOrder) {
		return FormOrder.values()[(currOrder / 6) % 2];
	}

	public Page getPage(User u, int i, State s) {
		if(s != User.State.TRAINING)
			i = i/2; // [0-5] // Reducing the index since there is two tasks for each TASK page
		int pageIndex = i % 2; // [0-1] // Getting the page number

		Form f = u.getPageOrder().get(pageIndex);
		Page page;
		if(s != User.State.TRAINING)
			page = Page.get(f,s, i / 2);
		else
			page = Page.get(f,s, 0);
		return page;
	}

	public Task getTask(User u, int i, State s) {

		Page page = getPage(u, i, s);
		if(s != User.State.TRAINING)
			i = i % 2;
		else
			i = 0;
		
		Task task = Task.get(page, i, s);
	
		return task;
		
	}

	public Method getMethod(User u, int i, State s) {

		if(s != User.State.TRAINING){
			i = i / 2;	
		}
		int method = i / 2;
		Method method_ = u.getMethodOrder().get(method);

		return method_;
		
	}

	public Page getCurrPage(User u) {
		return getPage(u, u.getCurr(), u.getState());
	}

	public Task getCurrTask(User u) {
		return getTask(u, u.getCurr(), u.getState());
	}

	public Method getCurrMethod(User u) {
		return getMethod(u, u.getCurr(), u.getState());
	}


}
