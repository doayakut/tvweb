package tvweb2.jpa.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Demographic {
	Age("Age"), 
	Gender("Gender"),
	Education("What is your current/highest degree level?"), 
	Experience("How much experience do you have with using a mobile phone for web browsing?"),
	Ownership("Do you currently own a smartphone?"),
	Frequency("How often do you use your phone to browse web?"),
	Aim("When you use your phone's web browser, what do you MOSTLY use it for?"),
	Tools("When you use your phone's browser to find specific information on a website, do you mostly"),
	Problems("Please list and briefly explain at least the top two problems (in one or two sentences) that you often experience when you use your mobile phone for web browsing."),
	Solutions("What would be your suggestions on addressing those problems you described in Question #9?");
	
	public static final int length = Demographic.values().length;
	public static final List<Demographic> list = new ArrayList<Demographic>(Arrays.asList(Demographic.values()));
	
	private String text;
	
	
	private Demographic(String text) {
		this.text = text;
	}
	
	public String getText(){
		return text;
	}
}
