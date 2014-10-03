package tvweb2.strategy;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import tvweb2.BaseServlet;
import tvweb2.jpa.User;
import tvweb2.jpa.User.State;
import tvweb2.jpa.enums.Form;
import tvweb2.jpa.enums.Method;
import tvweb2.jpa.enums.Task;
import tvweb2.jpa.evaluation.Familiarity;
import tvweb2.jpa.evaluation.PreStudy;
import tvweb2.jpa.evaluation.Questionnaire;
import tvweb2.jpa.evaluation.Tasks;
import tvweb2.jpa.service.UserService;

public class Export implements BaseStgy {
	public static ArrayList<Class<? extends User>> auths;
	public BaseServlet servlet;
	public UserService us;

	private Workbook wb;
	private Sheet sheetBlind;
	private Sheet sheetNormal;
	private CellStyle style_header;
	private CellStyle style_numeric;
	private CellStyle style_gray;
	private Font f_bold;
	private DataFormat dataFormat;

	public Export(BaseServlet s) {
		super();
		servlet = s;
		us = servlet.getUserservice();

		prepSheets();
	}

	@Override
	public boolean authenticate() {
		return true;
	}

	@Override
	public void execute() throws IOException {

		List<User> users = new ArrayList<User>(servlet.getUserservice()
				.findAllUsers());

		Collections.sort(users);
		int total_width = 0;
		for (int i = 0, count_blind = 2, count_normal = 2, offset = 0; i < users
				.size(); i++, offset = 0) {

			User u = users.get(i);

			if (u != null && !u.getName().startsWith("test")) {

				System.out.println("Writing: " + u.getName());
				Row r;
				if (u.isBlind()) {
					count_blind++;
					r = sheetBlind.createRow(count_blind);
				} else {
					count_normal++;
					r = sheetNormal.createRow(count_normal);
				}

				offset = writeName(r, u, offset) + 1;
				offset = writeOrder(r, u, offset) + 1;
				offset = writeTimes(r, u, offset) + 1;
				offset = writeAnswers(r, u, offset) + 1;
				offset = writeFamiliarity(r, u, offset) + 1;
				offset = writeQuestionnaire(r, u, offset) + 1;
				offset = writePreStudy(r, u, offset) + 1;
				total_width = offset;
			}

		}

		fixAesthetic(total_width);

		servlet.getResponse().setContentType("text/plain");
		servlet.getResponse().setHeader("Content-Disposition",
				"attachment; filename=data-" + getDate() + ".xls");
		wb.write(servlet.getResponse().getOutputStream());
	}

	private int writeName(Row r, User u, int offset) {

		r.createCell(0).setCellValue(u.getName());
		return offset + 1;

	}

	private int writeOrder(Row r, User u, int offset) {
		String str = "";
		List<Method> mo = u.getMethodOrder().getOrder();
		List<Form> fo = u.getPageOrder().getOrder();

		for (Method m : mo)
			str += m.toString().toLowerCase() + " - ";
		str = str.substring(0, str.length() - 3);
		str += " & ";
		for (Form f : fo)
			str += f.toString().toLowerCase() + " - ";
		str = str.substring(0, str.length() - 3);

		r.createCell(offset).setCellValue(str);

		return offset + 1;
	}

	private int writeTimes(Row r, User u, int offset) {
		int width = Method.length * Form.length;
		Map<Task, Float> times = u.getElapsedTimes();

		// Calculate index by Method.ordinal * Method.length + Form.ordinal
		float[] totals = new float[width];
		int[] counts = new int[width];

		for (int i = 0; i < width * 2; i++) { // two tasks for each column
			Task t = us.getTask(u, i, State.TASKS);
			Form f = us.getPage(u, i, State.TASKS).getType();
			Method m = us.getMethod(u, i, State.TASKS);

			Float value = times.get(t);
			if (value != null) {
				totals[m.ordinal() * Form.length + f.ordinal()] += value;
				counts[m.ordinal() * Form.length + f.ordinal()]++;
			}
		}

		for (int j = 0; j < width; j++) {
			if (counts[j] != 0) {
				Cell cell = r.createCell(offset + j);
				cell.setCellValue(totals[j] / counts[j]);
				cell.setCellStyle(style_numeric);
			}

		}

		return offset + width;
	}

	private int writeAnswers(Row r, User u, int offset) {
		int width = Method.length * Form.length;

		Tasks ta = u.getTaskAnswers();
		String answers[] = new String[width];
		for (int i = 0; i < width; i++)
			answers[i] = "";

		for (int i = 0; i < width * 2; i++) {
			Task t = us.getTask(u, i, State.TASKS);
			Form f = us.getPage(u, i, State.TASKS).getType();
			Method m = us.getMethod(u, i, State.TASKS);

			String answer = ta.getAnswer(i);
			String correct = "";

			if (m == Method.PERSONALIZED)
				correct = t.getAnswerPerson();
			else
				correct = t.getAnswerNormal();
			if (answer != null)
				answers[m.ordinal() * Form.length + f.ordinal()] += answer
						+ ":";
			answers[m.ordinal() * Form.length + f.ordinal()] += correct + " | ";
		}

		for (int j = 0; j < width; j++) {
			r.createCell(offset + j).setCellValue(answers[j]);
		}

		return offset + width;
	}

	private int writeQuestionnaire(Row r, User u, int offset) {
		int width = Method.length * Form.length;
		int within_offset = 17;

		Questionnaire qs[] = new Questionnaire[width];

		for (int i = 0; i < width * 2; i += 2) {

			Form f = us.getPage(u, i, State.TASKS).getType();
			Method m = us.getMethod(u, i, State.TASKS);

			qs[m.ordinal() * Form.length + f.ordinal()] = u
					.getQuestionnaire(i / 2 + 1);

		}

		for (int i = 0; i < within_offset; i++) {
			for (int q = 0; q < width; q++)
				if (qs[q] != null) {
					if (within_offset == i + 1) {
						if (qs[q].getFeedback() != null)
							r.createCell(offset + i + q * within_offset)
									.setCellValue(qs[q].getFeedback());
					} else if (qs[q].getAnswer(i) != null)
						r.createCell(offset + i + q * within_offset)
								.setCellValue(qs[q].getAnswer(i));
				}

		}

		return offset + within_offset * width - 1;
	}

	private int writeFamiliarity(Row r, User u, int offset) {
		int width = Method.length * Form.length;
		Familiarity f = u.getFamiliarity();
		if (f != null)
			for (int i = 0; i < width; i++) {

				Float answer = f.getAnswer(i);
				if (answer != null)
					r.createCell(offset + i).setCellValue(f.getAnswer(i));
			}
		return offset + width;
	}

	private int writePreStudy(Row r, User u, int offset){
		int width = 12;
		PreStudy ps = u.getPrestudy();
		if(ps != null) {
			
			r.createCell(offset + 1).setCellValue(ps.getAge());
			r.createCell(offset + 2).setCellValue(ps.getGender());
			r.createCell(offset + 3).setCellValue(ps.getEdu());
			r.createCell(offset + 4).setCellValue(ps.getExp());
			r.createCell(offset + 5).setCellValue(ps.getOwner());
			r.createCell(offset + 6).setCellValue(ps.getFreq());
			r.createCell(offset + 7).setCellValue(ps.getAim());
			r.createCell(offset + 8).setCellValue(ps.getTools());
			r.createCell(offset + 9).setCellValue(ps.getDisabilities());
			r.createCell(offset + 10).setCellValue(ps.getBlindSince());
			r.createCell(offset + 11).setCellValue(ps.getProbs());
			r.createCell(offset + 12).setCellValue(ps.getSols());
		}
		
		return offset;
		
	}
	
	private void prepSheets() {

		wb = new HSSFWorkbook();
		sheetBlind = wb.createSheet("Blind");
		sheetNormal = wb.createSheet("Normal");

		HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette();
		palette.setColorAtIndex(HSSFColor.GREY_25_PERCENT.index, (byte) 230,
				(byte) 230, (byte) 230);

		style_gray = wb.createCellStyle();
		style_gray.setFillForegroundColor(IndexedColors.GREY_25_PERCENT
				.getIndex());
		style_gray.setFillPattern(CellStyle.SOLID_FOREGROUND);

		style_header = wb.createCellStyle();
		f_bold = wb.createFont();
		f_bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style_header.setFont(f_bold);
		style_header.setAlignment(CellStyle.ALIGN_CENTER);

		style_numeric = wb.createCellStyle();
		dataFormat = wb.createDataFormat();
		style_numeric.setDataFormat(dataFormat.getFormat("0.00"));

		Row row1_blind, row1_normal, row2_blind, row2_normal, row0_blind, row0_normal;

		row0_blind = sheetBlind.createRow(0);
		row0_normal = sheetNormal.createRow(0);
		row1_blind = sheetBlind.createRow(1);
		row1_normal = sheetNormal.createRow(1);
		row2_blind = sheetBlind.createRow(2);
		row2_normal = sheetNormal.createRow(2);

		createGrayCell(row0_blind, 1);
		createGrayCell(row0_normal, 1);

		// ORDER HEADER
		createBoldCell(row2_blind, 2).setCellValue("Order");
		createBoldCell(row2_normal, 2).setCellValue("Order");

		createGrayCell(row0_blind, 3);
		createGrayCell(row0_normal, 3);

		// TIMES HEADER: METHOD ROW
		createBoldCell(row1_blind, 4).setCellValue("Regular");
		sheetBlind.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));

		createBoldCell(row1_blind, 6).setCellValue("Treeview");
		sheetBlind.addMergedRegion(new CellRangeAddress(1, 1, 6, 7));

		createBoldCell(row1_blind, 8).setCellValue("Personalized");
		sheetBlind.addMergedRegion(new CellRangeAddress(1, 1, 8, 9));

		createBoldCell(row1_normal, 4).setCellValue("Regular");
		sheetNormal.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));

		createBoldCell(row1_normal, 6).setCellValue("Treeview");
		sheetNormal.addMergedRegion(new CellRangeAddress(1, 1, 6, 7));

		createBoldCell(row1_normal, 8).setCellValue("Personalized");
		sheetNormal.addMergedRegion(new CellRangeAddress(1, 1, 8, 9));

		// TIMES HEADER: FORM ROW
		createBoldCell(row2_blind, 4).setCellValue("Simple");
		createBoldCell(row2_blind, 5).setCellValue("Complex");
		createBoldCell(row2_blind, 6).setCellValue("Simple");
		createBoldCell(row2_blind, 7).setCellValue("Complex");
		createBoldCell(row2_blind, 8).setCellValue("Simple");
		createBoldCell(row2_blind, 9).setCellValue("Complex");

		createBoldCell(row2_normal, 4).setCellValue("Simple");
		createBoldCell(row2_normal, 5).setCellValue("Complex");
		createBoldCell(row2_normal, 6).setCellValue("Simple");
		createBoldCell(row2_normal, 7).setCellValue("Complex");
		createBoldCell(row2_normal, 8).setCellValue("Simple");
		createBoldCell(row2_normal, 9).setCellValue("Complex");

		// TIMES HEADER: FIRST ROW
		createCell(row0_blind, 4)
				.setCellValue(
						"ELAPSED TIMES"
								+ "\n"
								+ "---------------------------------------------"
								+ "\n"
								+ "The averages of two tasks for each method - pagetype pair. Page Types: Simple,Complex. Method: Regular, Treeview, Personalized"
								+ "\n\n");
		sheetBlind.addMergedRegion(new CellRangeAddress(0, 0, 4, 9));
		createCell(row0_normal, 4)
				.setCellValue(
						"ELAPSED TIMES"
								+ "\n"
								+ "---------------------------------------------"
								+ "\n"
								+ "The averages of two tasks for each method - pagetype pair. Page Types: Simple,Complex. Method: Regular, Treeview, Personalized"
								+ "\n\n");
		sheetNormal.addMergedRegion(new CellRangeAddress(0, 0, 4, 9));

		createGrayCell(row0_blind, 10);
		createGrayCell(row0_normal, 10);

		// ACCURACY HEADER: METHOD ROW
		createBoldCell(row1_blind, 11).setCellValue("Regular");
		sheetBlind.addMergedRegion(new CellRangeAddress(1, 1, 11, 12));

		createBoldCell(row1_blind, 13).setCellValue("Treeview");
		sheetBlind.addMergedRegion(new CellRangeAddress(1, 1, 13, 14));

		createBoldCell(row1_blind, 15).setCellValue("Personalized");
		sheetBlind.addMergedRegion(new CellRangeAddress(1, 1, 15, 16));

		createBoldCell(row1_normal, 11).setCellValue("Regular");
		sheetNormal.addMergedRegion(new CellRangeAddress(1, 1, 11, 12));

		createBoldCell(row1_normal, 13).setCellValue("Treeview");
		sheetNormal.addMergedRegion(new CellRangeAddress(1, 1, 13, 14));

		createBoldCell(row1_normal, 15).setCellValue("Personalized");
		sheetNormal.addMergedRegion(new CellRangeAddress(1, 1, 15, 16));

		// ACCURACY HEADER: FORM ROW
		createBoldCell(row2_blind, 11).setCellValue("Simple");
		createBoldCell(row2_blind, 12).setCellValue("Complex");
		createBoldCell(row2_blind, 13).setCellValue("Simple");
		createBoldCell(row2_blind, 14).setCellValue("Complex");
		createBoldCell(row2_blind, 15).setCellValue("Simple");
		createBoldCell(row2_blind, 16).setCellValue("Complex");

		createBoldCell(row2_normal, 11).setCellValue("Simple");
		createBoldCell(row2_normal, 12).setCellValue("Complex");
		createBoldCell(row2_normal, 13).setCellValue("Simple");
		createBoldCell(row2_normal, 14).setCellValue("Complex");
		createBoldCell(row2_normal, 15).setCellValue("Simple");
		createBoldCell(row2_normal, 16).setCellValue("Complex");

		// ACCURACY HEADER: FIRST ROW
		createCell(row0_blind, 11)
				.setCellValue(
						"TASK ANSWER ERROR RATE"
								+ "\n"
								+ "--------------------------------------------------------"
								+ "\n"
								+ "Accuracy of for each method - pagetype pair. Page Types: Simple,Complex. Method: Regular, Treeview, Personalized"
								+ "\n\n");
		sheetBlind.addMergedRegion(new CellRangeAddress(0, 0, 11, 16));
		createCell(row0_normal, 11)
				.setCellValue(
						"TASK ANSWER ERROR RATE"
								+ "\n"
								+ "--------------------------------------------------------"
								+ "\n"
								+ "Accuracy of for each method - pagetype pair. Page Types: Simple,Complex. Method: Regular, Treeview, Personalized"
								+ "\n\n");
		sheetNormal.addMergedRegion(new CellRangeAddress(0, 0, 11, 16));

		createGrayCell(row0_blind, 17);
		createGrayCell(row0_normal, 17);

		// FAMILIARITY HEADER
		createCell(row0_blind, 18).setCellValue(
				"FAMILIARITY" + "\n"
						+ "----------------------------------------------"
						+ "\n" + "Familiarity with the given webpage topics. "
						+ "\n" + "1st: Nigerian S.G.        " + "\n"
						+ "2nd: Vehicles" + "\n" + "3rd: Water Co." + "\n"
						+ "4th: Fish" + "\n" + "5th: Fabric" + "\n"
						+ "6th: Camera" + "\n" + "7th: News" + "\n"
						+ "8th: Government" + "\n" + "9th: Food" + "\n");
		sheetBlind.addMergedRegion(new CellRangeAddress(0, 0, 18, 23));

		createBoldCell(row2_blind, 18).setCellValue("1st");
		createBoldCell(row2_blind, 19).setCellValue("2nd");
		createBoldCell(row2_blind, 20).setCellValue("3rd");
		createBoldCell(row2_blind, 21).setCellValue("4th");
		createBoldCell(row2_blind, 22).setCellValue("5th");
		createBoldCell(row2_blind, 23).setCellValue("6th");

		createGrayCell(row0_blind, 24);

		createCell(row0_normal, 18).setCellValue(
				"FAMILIARITY" + "\n"
						+ "----------------------------------------------"
						+ "\n" + "Familiarity with the given webpage topics. "
						+ "\n" + "1st: Nigerian S.G.        " + "\n"
						+ "2nd: Vehicles" + "\n" + "3rd: Water Co." + "\n"
						+ "4th: Fish" + "\n" + "5th: Fabric" + "\n"
						+ "6th: Camera" + "\n" + "7th: News" + "\n"
						+ "8th: Government" + "\n" + "9th: Food" + "\n");
		sheetNormal.addMergedRegion(new CellRangeAddress(0, 1, 18, 23));

		createBoldCell(row2_normal, 18).setCellValue("1st");
		createBoldCell(row2_normal, 19).setCellValue("2nd");
		createBoldCell(row2_normal, 20).setCellValue("3rd");
		createBoldCell(row2_normal, 21).setCellValue("4th");
		createBoldCell(row2_normal, 22).setCellValue("5th");
		createBoldCell(row2_normal, 23).setCellValue("6th");

		createGrayCell(row0_normal, 24);

		// QUESTIONNAIRE HEADING
		createCell(row0_blind, 25)
				.setCellValue(
						"QUESTIONNAIRE RESULTS"
								+ "\n"
								+ "---------------------------------------------------"
								+ "\n"
								+ "Columns are grouped into methods and answers (ranging 1 to 7) displayed for each of the 16 questions in questionnaire.");
		sheetBlind.addMergedRegion(new CellRangeAddress(0, 0, 25, 126));
		createCell(row0_normal, 25)
				.setCellValue(
						"QUESTIONNAIRE RESULTS"
								+ "\n"
								+ "---------------------------------------------------"
								+ "\n"
								+ "Columns are grouped into methods and answers (ranging 1 to 7) displayed for each of the 16 questions in questionnaire.");
		sheetNormal.addMergedRegion(new CellRangeAddress(0, 0, 25, 126));

		// QUESTIONNAIRE HEADER: METHOD ROW
		createBoldCell(row1_blind, 25).setCellValue("Regular");
		createBoldCell(row1_blind, 59).setCellValue("Treeview");
		createBoldCell(row1_blind, 93).setCellValue("Personalized");

		createBoldCell(row2_blind, 25).setCellValue("Simple");
		createBoldCell(row2_blind, 42).setCellValue("Complex");
		createBoldCell(row2_blind, 59).setCellValue("Simple");
		createBoldCell(row2_blind, 76).setCellValue("Complex");
		createBoldCell(row2_blind, 93).setCellValue("Simple");
		createBoldCell(row2_blind, 110).setCellValue("Complex");

		sheetBlind.addMergedRegion(new CellRangeAddress(1, 1, 25, 58));
		sheetBlind.addMergedRegion(new CellRangeAddress(1, 1, 59, 92));
		sheetBlind.addMergedRegion(new CellRangeAddress(1, 1, 93, 126));
		sheetBlind.addMergedRegion(new CellRangeAddress(2, 2, 25, 41));
		sheetBlind.addMergedRegion(new CellRangeAddress(2, 2, 42, 58));
		sheetBlind.addMergedRegion(new CellRangeAddress(2, 2, 59, 75));
		sheetBlind.addMergedRegion(new CellRangeAddress(2, 2, 76, 92));
		sheetBlind.addMergedRegion(new CellRangeAddress(2, 2, 93, 109));
		sheetBlind.addMergedRegion(new CellRangeAddress(2, 2, 110, 126));

		createBoldCell(row1_normal, 25).setCellValue("Regular");
		createBoldCell(row1_normal, 59).setCellValue("Treeview");
		createBoldCell(row1_normal, 93).setCellValue("Personalized");

		createBoldCell(row2_normal, 25).setCellValue("Simple");
		createBoldCell(row2_normal, 42).setCellValue("Complex");
		createBoldCell(row2_normal, 59).setCellValue("Simple");
		createBoldCell(row2_normal, 76).setCellValue("Complex");
		createBoldCell(row2_normal, 93).setCellValue("Simple");
		createBoldCell(row2_normal, 110).setCellValue("Complex");

		sheetNormal.addMergedRegion(new CellRangeAddress(1, 1, 25, 58));
		sheetNormal.addMergedRegion(new CellRangeAddress(1, 1, 59, 92));
		sheetNormal.addMergedRegion(new CellRangeAddress(1, 1, 93, 126));
		sheetNormal.addMergedRegion(new CellRangeAddress(2, 2, 25, 41));
		sheetNormal.addMergedRegion(new CellRangeAddress(2, 2, 42, 58));
		sheetNormal.addMergedRegion(new CellRangeAddress(2, 2, 59, 75));
		sheetNormal.addMergedRegion(new CellRangeAddress(2, 2, 76, 92));
		sheetNormal.addMergedRegion(new CellRangeAddress(2, 2, 93, 109));
		sheetNormal.addMergedRegion(new CellRangeAddress(2, 2, 110, 126));
		
		createGrayCell(row0_blind, 127);
		createGrayCell(row0_normal, 127);
		
		// PRESTUDY HEADING
		createCell(row0_blind, 128)
				.setCellValue(
						"PreStudy");
		sheetBlind.addMergedRegion(new CellRangeAddress(0, 0, 128, 139));
		createCell(row0_normal, 128)
				.setCellValue(
						"PreStudy");
		sheetNormal.addMergedRegion(new CellRangeAddress(0, 0, 128, 139));

		createBoldCell(row1_blind, 128).setCellValue("Age");
		createBoldCell(row1_blind, 129).setCellValue("Gender");
		createBoldCell(row1_blind, 130).setCellValue("Education");
		createBoldCell(row1_blind, 131).setCellValue("Experience");
		createBoldCell(row1_blind, 132).setCellValue("Owner");
		createBoldCell(row1_blind, 133).setCellValue("Frequency");
		createBoldCell(row1_blind, 134).setCellValue("Aim");
		createBoldCell(row1_blind, 135).setCellValue("Tools");
		createBoldCell(row1_blind, 136).setCellValue("Other Disabilities");
		createBoldCell(row1_blind, 137).setCellValue("Blind Since");
		createBoldCell(row1_blind, 138).setCellValue("Probs");
		createBoldCell(row1_blind, 139).setCellValue("Sols");

		createBoldCell(row1_normal, 128).setCellValue("Age");
		createBoldCell(row1_normal, 129).setCellValue("Gender");
		createBoldCell(row1_normal, 130).setCellValue("Education");
		createBoldCell(row1_normal, 131).setCellValue("Experience");
		createBoldCell(row1_normal, 132).setCellValue("Owner");
		createBoldCell(row1_normal, 133).setCellValue("Frequency");
		createBoldCell(row1_normal, 134).setCellValue("Aim");
		createBoldCell(row1_normal, 135).setCellValue("Tools");
		createBoldCell(row1_normal, 136).setCellValue("Other Disabilities");
		createBoldCell(row1_normal, 137).setCellValue("Blind Since");
		createBoldCell(row1_normal, 138).setCellValue("Probs");
		createBoldCell(row1_normal, 139).setCellValue("Sols");
		

	}

	private void fixAesthetic(int offset) {

		// Adjust widths
		for (int o = 0; o < offset; o++) {
			if (o == 58 || o == 92 || o == 126 || o == 41 || o == 75
					|| o == 109)
				continue;
			sheetNormal.autoSizeColumn(o);
			sheetBlind.autoSizeColumn(o);
		}

		// Freeze first column
		sheetNormal.createFreezePane(1, 0, 1, 0);
		sheetBlind.createFreezePane(1, 0, 1, 0);

		sheetBlind.addMergedRegion(new CellRangeAddress(0, offset, 1, 1));
		sheetBlind.addMergedRegion(new CellRangeAddress(0, offset, 3, 3));
		sheetBlind.addMergedRegion(new CellRangeAddress(0, offset, 10, 10));
		sheetBlind.addMergedRegion(new CellRangeAddress(0, offset, 17, 17));
		sheetBlind.addMergedRegion(new CellRangeAddress(0, offset, 24, 24));
		sheetNormal.addMergedRegion(new CellRangeAddress(0, offset, 1, 1));
		sheetNormal.addMergedRegion(new CellRangeAddress(0, offset, 3, 3));
		sheetNormal.addMergedRegion(new CellRangeAddress(0, offset, 10, 10));
		sheetNormal.addMergedRegion(new CellRangeAddress(0, offset, 17, 17));
		sheetNormal.addMergedRegion(new CellRangeAddress(0, offset, 24, 24));

	}

	private Cell createBoldCell(Row row, int col) {
		Cell cell = row.createCell(col);
		cell.setCellStyle(style_header);
		return cell;
	}

	private Cell createCell(Row row, int col) {
		Cell cell = row.createCell(col);
		return cell;
	}

	private Cell createGrayCell(Row row, int col) {
		Cell cell = row.createCell(col);
		cell.setCellStyle(style_gray);
		return cell;
	}

	private String getDate() {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_kk-mm-ss");
		Date date = new Date();
		return df.format(date);
	}
}
