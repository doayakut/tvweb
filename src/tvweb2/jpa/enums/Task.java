package tvweb2.jpa.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tvweb2.jpa.User;


public enum Task {
	Train1(Page.train1, 0, User.State.TRAINING,
			"What it considered a classic example of Sparkling Wine?",
			"In the U.S., how much alcohol does a wine need to contain, in order to be categorized as Dessert Wine.",
			"What color grapes are used when making Red Wine?",
			"Wine is a alcohol containing beverage.",
			"Dessert Wine is a type of wine. Wine is a alcohol containing beverage.",
			"",
			"",
			""),
	Train2(Page.train2, 0, User.State.TRAINING,
			"What is the third aesthetic and rhythmic quality of poems?",
			"When was the first volume of the Novel don quixote published?",
			"Thalia, who is one of symbol of the ancient Greek Muses in Drama Literature, was portrayed as crowned with which plant?",
			"Drama and Poem are types of literature",
			"A Novel is a type of literature in the form of a sequential story",
			"",
			"",
			""),
	Task1(Page.test1,0, User.State.TASKS,
			"What major does the governor of the State of Imo hold a Master's in?",
			"What month was the governor of Lagos born? ",
			"What year was the governor of Lagos sworn into office?",
			"Imo is a State in Nigeria",
			"Lagos is a State in Nigeria",
			"Biology",
			"September",
			"2011"), 
	Task2(Page.test1,1, User.State.TASKS,
			"What year did the governor of the State of Abia graduate with his Bachelor’s degree? ",
			"What city was the governor of Kogi born?",
			"What age did the governor of the State of Kogi  become a first class flying instructor?",
			"Abia is a State in Nigeria",
			"Kogi is a State in Nigeria",
			"1965",
			"Manchester",
			"35"),  
	Task3(Page.test2,0, User.State.TASKS,
			"What type of Canoe is the oldest recovered boat in the world?",
			"How many axles do most Train bogies have? ",
			"In the earlier days, what kind of locomotives were most trains powered by? ",
			"A boat is a small vehicle that is used for traveling on water.",
			"A Train is a series of connected railroad cars pulled or pushed by one or more locomotives. ",
			"Amazon",
			"7",
			"Palm Oil"),
	Task4(Page.test2,1, User.State.TASKS,
			"What year did Sir George Cayley set forth the concept of the modern airplane ?",
			"What percentage of the world's motorcyles are in developing countries of Asia?",
			"How many motorcycles are there per 1000 people?",
			"An Airplane is a machine that has wings and an engine and that flies through the air.",
			"A Motorcycle is a vehicle with two wheels that is powered by a motor and that can carry one or two people.",
			"1829",
			"58",
			"73"),
	Task5(Page.test3,0, User.State.TASKS,
			"What family owns the Fiji Water Company?",
			"Dasani is owned by what company?",
			"What year was Dasani first introduced to the Brazilian Market?",
			"Fiji is a brand of bottled spring water",
			"Dasani is a brand of bottled spring water",
			"Carter",
			"Pepsi",
			"1972"),
	Task6(Page.test3,1, User.State.TASKS,
			"How many bottling facilities does Nestle have in Canada?",
			"What year was the Deer Park Company established?",
			"What was Deer Park's current slogan?",
			"Nestle is a brand of bottled spring water",
			"Deer Park is a brand of bottled spring water",
			"17",
			"1467",
			"Drink More"),
	Task7(Page.test4,0, User.State.TASKS,
			"What is the second of the five genus groups of Anchovies? ",
			"What region of Zimbabwe is the Spiny Dogfish meat mostly eaten?",
			"How many Spiny Dogfish does the average Zimbabwean family eat in a week?",
			"Anchovies are a type of fish",
			"Dogfish are a type of edible fish",
			"Lemonjello",
			"Riverland",
			"12"),
	Task8(Page.test4,1, User.State.TASKS,
			"What is the first skin color of the Rockfish listed on the website?",
			"Who caught the Blueline Tilefish in July?",
			"How many pounds did the Blueline Tilefish caught in July weigh? ",
			"Rockfish are a type of edible fish",
			"Tilefish are a type if edible fish",
			"Purple",
			"Anthony Smith",
			"25"),
	Task9(Page.test5,0, User.State.TASKS,
			"According to the website, how many months ago was the Olympus PEN E-P5 released?",
			"What does the Canon Rebel SL1 camera weigh in lbs? ",
			"What is the height of the Canon Rebel SL1?",
			"The Olympus PEN E-P5 is a camera. A camera is a device used for taking pictures. ",
			"The Canon Rebel SL1 is a camera. A camera is a device used for taking pictures. ",
			"9",
			"3",
			"3.7"),
	Task10(Page.test5,1, User.State.TASKS,
			"What is the first of the 2 best features of the Kodak Easyshare Z5010. ",
			"What movie was the Fujifilm X-M1 camera used to film recently?",
			"How much did the movie filmed using the Fujifilm X-M1 camera make on opening day?",
			"The Kodak Easyshare Z5010 is a camera. A camera is a device used for taking pictures. ",
			"The Fujifilm M-X1 is a camera. A camera is a device used for taking pictures. ",
			"Shutter Speed",
			"Halo",
			"2million"),
	Task11(Page.test6,0, User.State.TASKS,
			"How many crimps per inch does the fine wool, merino, have?",
			"What thickness (in inches) is the least flexible type of fleece?",
			"What is the thinnest (in inches) thickness of Fleece?",
			"Wool is a type of fabric. ",
			"Fleece is a type of fabric",
			"70",
			"250",
			"20"),
	Task12(Page.test6,1, User.State.TASKS,
			"What country was silk first developed?",
			"What is the  natural color of Dry Denim?",
			"Where is Dry Denim typically found?",
			"Silk is a type of fabric. ",
			"Denim is a type of fabric",
			"Japan",
			"Brown",
			"West Africa");
	
	public static final int length = Task.values().length;
	public static final List<Task> list = new ArrayList<Task>(Arrays.asList(Task.values()));

	private final Page page;
	private final int no;
	private final User.State state;
	private final String questionNormal;
	private final String questionPerson;
	private final String questionTraing;
	private final String defNormal;
	private final String defPerson;

	private final String answerNormal;
	private final String answerPerson;
	private final String answerTraing;
	private Task(Page parent, int no, User.State state, String q_n, String q_p, String q_t, String d_n, String d_p, String a_n, String a_p, String a_t) {
		this.page = parent;
		this.no = no;
		this.state = state;
		this.questionNormal = q_n;
		this.questionPerson = q_p;
		this.questionTraing = q_t;
		this.defNormal = d_n;
		this.defPerson = d_p;
		this.answerNormal = a_n;
		this.answerPerson = a_p;
		this.answerTraing = a_t;
	}
	public Page getParent() {
		return page;
	}

	public String getQuestionNormal() {
		return questionNormal;
	}
	public String getQuestionPerson() {
		return questionPerson;
	}
	public String getQuestionTraing() {
		return questionTraing;
	}
	public String getDefNormal() {
		return defNormal;
	}
	public String getDefPerson() {
		return defPerson;
	}

	public String getAnswerNormal() {
		return answerNormal;
	}
	public String getAnswerPerson() {
		return answerPerson;
	}
	public String getAnswerTraing() {
		return answerTraing;
	}
	public int getNo(){
		return no;
	}
	public User.State getState(){
		return state;
	}
	public static Task get(Page t, int i, User.State state){
		Task[] all = Task.values();
		for(Task task: all){
			if(task.getParent() == t && task.getNo() == i && ((state == User.State.TRAINING && task.getState() == state) || (state != User.State.TRAINING && task.getState() != User.State.TRAINING)))
				return task;
		}
		return null;
	}
	public static Task getTask(int index){
		
		Task[] allTasks = Task.values(); // .values() method return in the order they are declared.
		if(index < allTasks.length)
			return allTasks[index];
		else
			return null;
	}
}
