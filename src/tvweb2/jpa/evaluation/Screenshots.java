package tvweb2.jpa.evaluation;

import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Screenshots")
public class Screenshots {

	@Id
	@GeneratedValue
	private long id;

	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private String answer5;
	private String answer6;
	private String answer7;
	private String answer8;
	private String answer9;

//	@OneToOne(mappedBy = "screenshots")
//	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getAnswer4() {
		return answer4;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public String getAnswer5() {
		return answer5;
	}

	public void setAnswer5(String answer5) {
		this.answer5 = answer5;
	}

	public String getAnswer6() {
		return answer6;
	}

	public void setAnswer6(String answer6) {
		this.answer6 = answer6;
	}

	public String getAnswer7() {
		return answer7;
	}

	public void setAnswer7(String answer7) {
		this.answer7 = answer7;
	}

	public String getAnswer8() {
		return answer8;
	}

	public void setAnswer8(String answer8) {
		this.answer8 = answer8;
	}

	public String getAnswer9() {
		return answer9;
	}

	public void setAnswer9(String answer9) {
		this.answer9 = answer9;
	}

	public String getAnswer(int index) {
		int i = index + 1;
		switch(i){
		case 1:
			return this.getAnswer1();
		case 2:
			return this.getAnswer2();
		case 3:
			return this.getAnswer3();
		case 4:
			return this.getAnswer4();
		case 5:
			return this.getAnswer5();
		case 6:
			return this.getAnswer6();
		case 7:
			return this.getAnswer7();
		case 8:
			return this.getAnswer8();
		case 9:
			return this.getAnswer9();
		}
//		
//		try {
//			Field f;
//			f = Screenshots.class.getDeclaredField("answer" + (index + 1));
//			if (f != null) 
//				return (String) f.get(this);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return null;

	}

	public void setAnswer(int index, String val) {
		int i = index + 1;
		switch(i){
		case 1:
			this.setAnswer1(val);
		case 2:
			this.setAnswer2(val);
		case 3:
			this.setAnswer3(val);
		case 4:
			this.setAnswer4(val);
		case 5:
			this.setAnswer5(val);
		case 6:
			this.setAnswer6(val);
		case 7:
			this.setAnswer7(val);
		case 8:
			this.setAnswer8(val);
		case 9:
			this.setAnswer9(val);
			
		}
//		try {
//			Field f;
//			f = Screenshots.class.getDeclaredField("answer" + (index + 1));
//			if (f != null) 
//				f.set(this, val);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}


	public String getAttributeStr(String a) {
		try {
			// string field
			Field f = Screenshots.class.getDeclaredField(a);
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
