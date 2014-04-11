package tvweb2.jpa.evaluation;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Experience")
public class Experience {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(length=600)
	private String probs;
	
	@Column(length=600)
	private String sols;
//
//	@OneToOne(mappedBy = "experience")
//	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}


	public String getAttributeStr(String a) {
		try {
			// string field
			Field f = Experience.class.getDeclaredField(a);
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
