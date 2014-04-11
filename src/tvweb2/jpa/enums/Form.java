package tvweb2.jpa.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Form {
	SIMPLE, COMPLEX;
	public static final List<Form> list = new ArrayList<Form>(Arrays.asList(Form.values()));
	public static final int length = Form.values().length;

}
