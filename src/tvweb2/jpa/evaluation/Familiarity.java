package tvweb2.jpa.evaluation;

import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Familiarity")
public class Familiarity {

	@Id
	@GeneratedValue
	private long id;

	private Float answer1;
	private Float answer2;
	private Float answer3;
	private Float answer4;
	private Float answer5;
	private Float answer6;
	private Float answer7;
	private Float answer8;
	private Float answer9;

	public Float getAnswer1() {
		return answer1;
	}

	public void setAnswer1(Float answer1) {
		this.answer1 = answer1;
	}

	public Float getAnswer2() {
		return answer2;
	}

	public void setAnswer2(Float answer2) {
		this.answer2 = answer2;
	}

	public Float getAnswer3() {
		return answer3;
	}

	public void setAnswer3(Float answer3) {
		this.answer3 = answer3;
	}

	public Float getAnswer4() {
		return answer4;
	}

	public void setAnswer4(Float answer4) {
		this.answer4 = answer4;
	}

	public Float getAnswer5() {
		return answer5;
	}

	public void setAnswer5(Float answer5) {
		this.answer5 = answer5;
	}

	public Float getAnswer6() {
		return answer6;
	}

	public void setAnswer6(Float answer6) {
		this.answer6 = answer6;
	}

	public Float getAnswer7() {
		return answer7;
	}

	public void setAnswer7(Float answer7) {
		this.answer7 = answer7;
	}

	public Float getAnswer8() {
		return answer8;
	}

	public void setAnswer8(Float answer8) {
		this.answer8 = answer8;
	}

	public Float getAnswer9() {
		return answer9;
	}

	public void setAnswer9(Float answer9) {
		this.answer9 = answer9;
	}

	public Float getAnswer(int index) {
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
//		try {
//			Field f;
//			f = Familiarity.class.getDeclaredField("answer" + (index + 1));
//			if (f != null)
//				return (Float) f.get(this);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return null;

	}

	public void setAnswer(int index, float val) {
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
//			f = Familiarity.class.getDeclaredField("answer" + (index + 1));
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
			Field f = Familiarity.class.getDeclaredField(a);
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
