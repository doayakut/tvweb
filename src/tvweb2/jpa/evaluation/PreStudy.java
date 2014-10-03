package tvweb2.jpa.evaluation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import tvweb2.jpa.enums.Method;

@Entity
@Table(name = "PreStudy")
public class PreStudy {

	@Id
	@GeneratedValue
	private long id;

	private String age;
	private String gender;
	private String edu;
	private String exp;
	private String owner;
	private String freq;
	private String aim;
	private String tools;
	
	@Column(length=600)
	private String disabilities;
	
	@Column(length=600)
	private String blindsince;
	
	@Column(length=600)
	private String probs;

	@Column(length=600)
	private String sols;
	
	enum Age {
		A18("18-25"), A26("26-30"), A31("31-35"), A36("36-40"), A41("Above 40");
		public String label;
		Age(String label){
			this.label = label;
		}
		public static final List<Age> list = new ArrayList<Age>(Arrays.asList(Age.values()));
	}
	enum Gender {
		M("Male"), F("Female");
		public String label;
		Gender(String label){
			this.label = label;
		}
		public static final List<Gender> list = new ArrayList<Gender>(Arrays.asList(Gender.values()));

	}
	enum Edu {
		UNGRAD("Undergraduate"), MASTERS("Masters"), PHD("Phd"), POST("Post-doc"), OTHER("");
		public String label;
		Edu(String label){
			this.label = label;
		}
		public static final List<Edu> list = new ArrayList<Edu>(Arrays.asList(Edu.values()));

	}

	enum Exp {
		NONE("None"), 
		VERY_LITTLE("Very Little (i.e. a few times each quarter)"), 
		AVERAGE("Average (i.e. a few times each month)"), 
		SIGNIFICANT("Significant (i.e. a few times each week)"), 
		EXTENSIVE("Extensive (i.e. on a daily basis)");
		public String label;
		Exp(String label){
			this.label = label;
		}
		public static final List<Exp> list = new ArrayList<Exp>(Arrays.asList(Exp.values()));

	}
	enum Owner {
		OWNER("Owner"), NON_OWNER("Non-owner");
		public String label;
		Owner(String label){
			this.label = label;
		}
		public static final List<Owner> list = new ArrayList<Owner>(Arrays.asList(Owner.values()));
	}
	enum Freq{
		NEVER("Never"), RARELY("Rarely"), MONTH("Several times a month"), WEEK("Several times a week"), DAY("Several times a day");
		public String label;
		 Freq(String label){
			this.label = label;
		}
		 public static final List<Freq> list = new ArrayList<Freq>(Arrays.asList(Freq.values()));

	}

	enum Aim{
		SEARCH("Search on the web"),BROWSING("Browsing Web Pages"),BANKING("Online Banking"), ENTERTAINMENT("Entertainment"), SHOPPING("Shopping"), OTHER("");
		public String label;
		 Aim(String label){
			this.label = label;
		}
		 public static final List<Aim> list = new ArrayList<Aim>(Arrays.asList(Aim.values()));

	}

	enum Tools{
		SEARCH("Use the search function to find the information you need on a website"),MENU("Browse for the information using the websites menu"), BROWSE("Just browse the content of a website to find the information");
		public String label;
		 Tools(String label){
			this.label = label;
		}
		 public static final List<Tools> list = new ArrayList<Tools>(Arrays.asList(Tools.values()));

	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAge() {
		if(this.age == null)
			return "";
		else 
			return Age.list.get(Integer.parseInt(this.age)-1).label;
	}
	

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		if(this.gender == null)
			return "";
		else 
			return Gender.list.get(Integer.parseInt(this.gender)-1).label;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEdu() {
		if(this.edu == null)
			return "";
		else {
			try{
				return Edu.list.get(Integer.parseInt(this.edu)-1).label;
			}
			catch(Exception e){
				return this.edu;
			}
		}
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getExp() {
		if(this.exp == null)
			return "";
		else
			return Exp.list.get(Integer.parseInt(this.exp)-1).label;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getOwner() {
		if(this.owner == null)
			return "";
		else 
			return Owner.list.get(Integer.parseInt(this.owner)-1).label;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getFreq() {
		if(this.freq == null)
			return "";
		else 
			return Freq.list.get(Integer.parseInt(this.freq)-1).label;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getAim() {
		if(this.aim == null)
			return "";
		else {
			String[] list = this.aim.split(";");
			StringBuilder sb = new StringBuilder();
			int count = 1;
			for(String s: list){
				s = s.trim();
				try{
					sb.append(count + ": " + Aim.list.get(Integer.parseInt(s)-1).label);
				}
				catch(Exception e){
					sb.append(count + ": " + s);
				}
				count++;
				
			}
			return sb.toString();
		}
	}

	public void setAim(String aim) {
		this.aim = aim;
	}

	public String getTools() {
		if(this.tools == null)
			return "";
		else 
			return Tools.list.get(Integer.parseInt(this.tools)-1).label;
	}

	public void setTools(String tools) {
		this.tools = tools;
	}

	public String getProbs() {
		return probs;
	}

	public void setProbs(String probs) {
		this.probs = probs;
	}

	public String getSols() {
		return sols;
	}

	public void setSols(String sols) {
		this.sols = sols;
	}

	public String getDisabilities() {
		return disabilities;
	}

	public void setDisabilities(String disabilities) {
		this.disabilities = disabilities;
	}

	public String getBlindSince() {
		return blindsince;
	}

	public void setBlindSince(String blindsince) {
		this.blindsince = blindsince;
	}
	

	public String getAttributeStr(String a) {
		try {
			// string field
			Field f = PreStudy.class.getDeclaredField(a);
			Object val = f.get(this);
			if (val != null)
				return val.toString();
			else {
				if(f.getAnnotation(JoinColumn.class) == null){
					
				}
				else 
					return "";

			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "";
	}


	public String toString(){
		return Long.toString(id);
		
	}
}
