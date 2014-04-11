package tvweb2.jpa.enums;

import java.util.ArrayList;
import java.util.List;

public enum MethodOrder {
	O1(0,1,2),
	O2(1,2,0),
	O3(2,0,1),
	O4(0,2,1),
	O5(2,1,0),
	O6(1,0,2);
	
	private List<Method> order;

	private MethodOrder(int type0, int type1, int type2){
		order = new ArrayList<Method>();
		order.add(Method.list.get(type0));
		order.add(Method.list.get(type1));
		order.add(Method.list.get(type2));
	}

	public Method get(int index){
		return order.get(index);
	}
	public List<Method> getOrder(){
		return order;
	}
	public static final int size = MethodOrder.values().length;

}
