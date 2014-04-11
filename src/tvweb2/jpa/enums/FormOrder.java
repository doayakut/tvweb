package tvweb2.jpa.enums;

import java.util.ArrayList;
import java.util.List;

public enum FormOrder {
	O1(0,1),
	O2(1,0);
	
	private List<Form> order;

	private FormOrder(int type0, int type1){
		order = new ArrayList<Form>();
		order.add(Form.list.get(type0));
		order.add(Form.list.get(type1));
	}

	public Form get(int index){
		return order.get(index);
	}
	public List<Form> getOrder(){
		return order;
	}
	public static final int size = FormOrder.values().length;

}
