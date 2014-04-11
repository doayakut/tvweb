package tvweb2.jpa.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Question {
	Q1("Learning to use the system was easy for me."),
	Q2("I found it easy to find the information I wanted."),
	Q3("My interaction with the webpage elements was clear and understandable."),
	Q4("I found the system to be flexible to interact with."),
	Q5("I would be easy for me to become skillful at using the system."),
	Q6("It was easy to find my way around the sites."),
	Q7("I always felt I knew what it was possible to do next."),
	Q8("Using the system enabled me to accomplish tasks more quickly."),
	Q9("Using the system improved my performance in completing the tasks."),
	Q10("Using the system increased my productivity on the tasks."),
	Q11("Using the system enhanced my effectiveness on the tasks."),
	Q12("Using the system made it easier to perform the tasks."),
	Q13("I found the system useful in completing the tasks."),
	Q14("Please do not answer this question and skip to the next one."),
	Q15("The system understood my browsing and search needs."),
	Q16("The system arranges the Web content in a way that meets my web browsing and search needs."),
	Q17("The system took individuals' personal browsing and search needs into consideration while adapting Web pages."),
	Q18("The system adapted websites with my browsing and search preferences in mind."),
	Q19("The system is well-suited to repeated visitors."),
	Q20("I am willing to use the system for information browsing and search on mobile handheld devices."),
	Q21("I plan to use the system during my browsing and search of information on mobile handheld devices."),
	Q22("I will use the system in future."),
	Q23("The system presented the right amount of information on screens."),
	Q24("The system made it fun to explore the sites."),
	Q25("The organization of information on the system screens was appealing."),
	Q26("The interaction with the system was attractive."),
	Q27("Overall, I am satisfied with this system. ");
	
	
	public static final int length = Question.values().length;
	public static final List<Question> list = new ArrayList<Question>(Arrays.asList(Question.values()));
	
	private String text;
	
	private Question(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}
	

}
