package tvweb2.jpa.evaluation;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getAim() {
		return aim;
	}

	public void setAim(String aim) {
		this.aim = aim;
	}

	public String getTools() {
		return tools;
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
