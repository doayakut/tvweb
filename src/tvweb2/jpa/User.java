package tvweb2.jpa;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import tvweb2.jpa.enums.FormOrder;
import tvweb2.jpa.enums.MethodOrder;
import tvweb2.jpa.enums.Task;
import tvweb2.jpa.evaluation.Experience;
import tvweb2.jpa.evaluation.Familiarity;
import tvweb2.jpa.evaluation.PreStudy;
import tvweb2.jpa.evaluation.Questionnaire;
import tvweb2.jpa.evaluation.Screenshots;
import tvweb2.jpa.evaluation.Tasks;

@Entity
@Table(name = "User")
public class User implements Comparable<User> {

	@Id
	@GeneratedValue
	private long id;

	private String name;

	private int curr;
	private int learning;
	
	public enum Type {
		BLIND, NORMAL;
	}
	@Enumerated(EnumType.STRING)
	private Type type;

	public enum State {
		INFO, TASKS, QUESTIONNAIRE, TRAINING;
	}

	@Enumerated(EnumType.STRING)
	private State state;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "task")
	@Column(name = "time")
	@MapKeyEnumerated(EnumType.STRING)
	@CollectionTable(name = "ElapsedTimes", joinColumns = @JoinColumn(name = "user_id"))
	private Map<Task, Float> elapsedTimes = new HashMap<Task, Float>();

	@Enumerated(EnumType.STRING)
	private MethodOrder methodOrder;

	@Enumerated(EnumType.STRING)
	private FormOrder pageOrder;

	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private PreStudy prestudy;

	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private Familiarity familiarity;

	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private Tasks taskAnswers;

	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private Questionnaire questionnaire1;
	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private Questionnaire questionnaire2;
	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private Questionnaire questionnaire3;
	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private Questionnaire questionnaire4;
	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private Questionnaire questionnaire5;
	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private Questionnaire questionnaire6;

	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private Experience experience;

	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private Screenshots screenshots;

	private Date createDate;
	
	public User() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return name;
	}

	public void setUsername(String username) {
		this.name = username;
	}

	public int getCurr() {
		return curr;
	}

	public void setCurr(int curr) {
		this.curr = curr;
	}

	public Map<Task, Float> getElapsedTimes() {
		return elapsedTimes;
	}

	public void setElapsedTimes(Map<Task, Float> elapsedTimes) {
		this.elapsedTimes = elapsedTimes;
	}

	public static Hashtable<String, Object> getHashtable(User uoi) {
		Hashtable<String, Object> ht;

		if (uoi == null) {
			return null;
		}

		ht = new Hashtable<String, Object>();

		ht.put("id", uoi.getId());
		ht.put("name", uoi.getUsername());
		ht.put("task", uoi.getCurr());
		ht.put("type", uoi.getType());
		ht.put("learning", uoi.getLearning());
		ht.put("times", uoi.getElapsedTimes());
		ht.put("order", uoi.getMethodOrder().getOrder());

		return ht;
	}

	public boolean isCompleted() {
		if (this.curr == 12)
			return true;
		else
			return false;
	}
	public boolean isLearning() {
		return false;
	}
	
	public boolean isBlind(){
		return this.type == User.Type.BLIND;
	}

	public void addElapsedTime(Task task, float val) {
		elapsedTimes.put(task, val);
	}

	public FormOrder getPageOrder() {
		return pageOrder;
	}

	public void setPageOrder(FormOrder pageOrder) {
		this.pageOrder = pageOrder;
	}

	public void setMethodOrder(MethodOrder methodOrder) {
		this.methodOrder = methodOrder;
	}

	public MethodOrder getMethodOrder() {
		return methodOrder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public int compareTo(User o) {
		return new Long(id).compareTo(o.getId());
	}

	public PreStudy getPrestudy() {
		return prestudy;
	}

	public void setPrestudy(PreStudy prestudy) {
		this.prestudy = prestudy;
	}

	public Familiarity getFamiliarity() {
		return familiarity;
	}

	public void setFamiliarity(Familiarity familiarity) {
		this.familiarity = familiarity;
	}

	public Tasks getTaskAnswers() {
		return taskAnswers;
	}

	public void setTaskAnswers(Tasks taskAnswers) {
		this.taskAnswers = taskAnswers;
	}


	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}

	public Screenshots getScreenshots() {
		return screenshots;
	}

	public void setScreenshots(Screenshots screenshots) {
		this.screenshots = screenshots;
	}

	public Questionnaire getQuestionnaire1() {
		return questionnaire1;
	}

	public void setQuestionnaire1(Questionnaire q) {
		this.questionnaire1 = q;
	}

	public Questionnaire getQuestionnaire2() {
		return questionnaire2;
	}

	public void setQuestionnaire2(Questionnaire q) {
		this.questionnaire2 = q;
	}

	public Questionnaire getQuestionnaire3() {
		return questionnaire3;
	}

	public void setQuestionnaire3(Questionnaire q) {
		this.questionnaire3 = q;
	}

	public Questionnaire getQuestionnaire4() {
		return questionnaire4;
	}

	public void setQuestionnaire4(Questionnaire q) {
		this.questionnaire4 = q;
	}

	public Questionnaire getQuestionnaire5() {
		return questionnaire5;
	}

	public void setQuestionnaire5(Questionnaire q) {
		this.questionnaire5 = q;
	}

	public Questionnaire getQuestionnaire6() {
		return questionnaire6;
	}
	
	public void setQuestionnaire6(Questionnaire q) {
		this.questionnaire6 = q;
	}

	public Questionnaire getQuestionnaire(int i){
		switch(i){
		case 1: return questionnaire1;
		case 2: return questionnaire2;
		case 3: return questionnaire3;
		case 4: return questionnaire4;
		case 5: return questionnaire5;
		case 6: return questionnaire6;
		}
		return null;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getLearning() {
		return learning;
	}

	public void setLearning(int learning) {
		this.learning = learning;
	}


	public String getAttributeStr(String a) {
		try {
			// string field
			Field f = User.class.getDeclaredField(a);
			Object val = f.get(this);
			if (val != null){

				
				return val.toString();
			}
			else {
				final JoinColumn joinAnnotation = f
						.getAnnotation(JoinColumn.class);
				if (null != joinAnnotation) {
					return (!joinAnnotation.nullable()) ? "true" : "false";
				}

				final Column columnAnnotation = f.getAnnotation(Column.class);
				if (null != columnAnnotation) {
					return (!columnAnnotation.nullable()) ? "true" : "false";
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "";
	}
	
	public String toString(){
		return id + "-" + name;
		
	}
}
