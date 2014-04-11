package tvweb2.jpa.evaluation;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Questionnaire")
public class Questionnaire {

	@Id
	@GeneratedValue
	private long id;

	private Float answer01;
	private Float answer02;
	private Float answer03;
	private Float answer04;
	private Float answer05;
	private Float answer06;
	private Float answer07;
	private Float answer08;
	private Float answer09;
	private Float answer10;
	private Float answer11;
	private Float answer12;
	private Float answer13;
	private Float answer14;
	private Float answer15;
	private Float answer16;

	@Column(length=600)
	private String feedback;


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
		case 10:
			return this.getAnswer10();
		case 11:
			return this.getAnswer11();
		case 12:
			return this.getAnswer12();
		case 13:
			return this.getAnswer13();
		case 14:
			return this.getAnswer14();
		case 15:
			return this.getAnswer15();
		case 16:
			return this.getAnswer16();
			
		}
//		try {
//			Field f;
//			f = Questionnaire.class.getDeclaredField("answer" + (index + 1));
//			if (f != null) 
//				return (Float) f.get(this);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return null;

	}

	public void setFeedback(String val){
		this.feedback = val;
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
		case 10:
			this.setAnswer10(val);
		case 11:
			this.setAnswer11(val);
		case 12:
			this.setAnswer12(val);
		case 13:
			this.setAnswer13(val);
		case 14:
			this.setAnswer14(val);
		case 15:
			this.setAnswer15(val);
		case 16:
			this.setAnswer16(val);
		}
//		try {
//			Field f;
//			f = Questionnaire.class.getDeclaredField("answer" + (index + 1));
//			if (f != null) 
//				f.set(this, val);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Float getAnswer1() {
		return answer01;
	}


	public void setAnswer1(Float answer1) {
		this.answer01 = answer1;
	}


	public Float getAnswer2() {
		return answer02;
	}


	public void setAnswer2(Float answer2) {
		this.answer02 = answer2;
	}


	public Float getAnswer3() {
		return answer03;
	}


	public void setAnswer3(Float answer3) {
		this.answer03 = answer3;
	}


	public Float getAnswer4() {
		return answer04;
	}


	public void setAnswer4(Float answer4) {
		this.answer04 = answer4;
	}


	public Float getAnswer5() {
		return answer05;
	}


	public void setAnswer5(Float answer5) {
		this.answer05 = answer5;
	}


	public Float getAnswer6() {
		return answer06;
	}


	public void setAnswer6(Float answer6) {
		this.answer06 = answer6;
	}


	public Float getAnswer7() {
		return answer07;
	}


	public void setAnswer7(Float answer7) {
		this.answer07 = answer7;
	}


	public Float getAnswer8() {
		return answer08;
	}


	public void setAnswer8(Float answer8) {
		this.answer08 = answer8;
	}


	public Float getAnswer9() {
		return answer09;
	}


	public void setAnswer9(Float answer9) {
		this.answer09 = answer9;
	}


	public Float getAnswer10() {
		return answer10;
	}


	public void setAnswer10(Float answer10) {
		this.answer10 = answer10;
	}


	public Float getAnswer11() {
		return answer11;
	}


	public void setAnswer11(Float answer11) {
		this.answer11 = answer11;
	}


	public Float getAnswer12() {
		return answer12;
	}


	public void setAnswer12(Float answer12) {
		this.answer12 = answer12;
	}


	public Float getAnswer13() {
		return answer13;
	}


	public void setAnswer13(Float answer13) {
		this.answer13 = answer13;
	}


	public Float getAnswer14() {
		return answer14;
	}


	public void setAnswer14(Float answer14) {
		this.answer14 = answer14;
	}


	public Float getAnswer15() {
		return answer15;
	}


	public void setAnswer15(Float answer15) {
		this.answer15 = answer15;
	}


	public Float getAnswer16() {
		return answer16;
	}


	public void setAnswer16(Float answer16) {
		this.answer16 = answer16;
	}

	public String getFeedback(){
		return feedback;
	}



	public String getAttributeStr(String a) {
		try {
			// string field
			Field f = Questionnaire.class.getDeclaredField(a);
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
