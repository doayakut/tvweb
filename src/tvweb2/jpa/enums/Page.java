package tvweb2.jpa.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tvweb2.jpa.User;


public enum Page {
	train1(1,0, User.State.TRAINING, Form.SIMPLE), 
	train2(2,0, User.State.TRAINING,Form.COMPLEX),
	test1(1,0, User.State.TASKS, Form.SIMPLE), 
	test2(2,1, User.State.TASKS, Form.SIMPLE),
	test3(3,2, User.State.TASKS, Form.SIMPLE),
	test4(4,0, User.State.TASKS, Form.COMPLEX),
	test5(5,1, User.State.TASKS, Form.COMPLEX),
	test6(6,2, User.State.TASKS, Form.COMPLEX);
	// 0,1,4,5,10,7,(8,9,10)

	private final int folder; // which folder its in
	private final int index; // 
	private final User.State state;
	private final Form form;

	public static final int length = Page.values().length;
	public static final List<Page> list = new ArrayList<Page>(Arrays.asList(Page.values()));
	
	Page(int folder, int index, User.State t,Form type){
		this.folder = folder;
		this.index = index;
		this.state = t;
		this.form = type;
	}
	public int getFolder() {
		return folder;
	}
	public Form getType() {
		return form;
	}
	public int getIndex() {
		return index;
	}
	public User.State getState() {
		return state;
	}
	public static Page get(Form form, User.State state, int index){
		Page[] all = Page.values();
		
		for(Page page: all){
			if(page.getType() == form && page.getIndex() == index && ((state == User.State.TRAINING && page.getState() == state) || (state != User.State.TRAINING && page.getState() != User.State.TRAINING)))
				return page;
		}
		return null;
	}
	

}
