package lk.ysu.workingschedule.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lk.ysu.workingschedule.dao.Dao_inputData;
import lk.ysu.workingschedule.dao.Dao_unit;
import lk.ysu.workingschedule.db.DB_Link;

public class Servlet_unit extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public Servlet_unit() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		//response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		//���ֹ����������ù��ܻ��ֱ�־"part"Ĭ��ֵ
		int part = 1;	
		String str = request.getParameter("part");
		if(str == null){
			Object obj = request.getSession().getAttribute("part_unit");
			if(obj != null){
				part = (Integer) obj;
			}
		}else{
			part = Integer.parseInt(str);
		}
		
		switch(part){
		case 1:	
			//�𿨿��Ų�ѯ
			searchUnit(request,response,"number_unit_TU");
			request.getRequestDispatcher("../transUnit.jsp").forward(request,response);
			break;
		case 2:	
			//����ת�����¿���Ϣ
			transUnit(request,response);
			request.getRequestDispatcher("/servlet/Servlet_unit?part=1").forward(request, response);
			break;
		case 3:	
			//���ת�����¿���Ϣ
			try {
				addTransUnit(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("/servlet/Servlet_unit?part=1").forward(request, response);
			break;
		case 4:	
			//�𿨿��Ų�ѯ
			searchUnit(request,response,"number_unit_SU");
			request.getRequestDispatcher("../splitUnit.jsp").forward(request,response);
			break;
		case 5:
			//���ɲ𿨵��¿���Ϣ
			splitUnit(request,response);
			request.getRequestDispatcher("/servlet/Servlet_unit?part=4").forward(request,response);
			break;
		case 6:
			//���ת�����¿���Ϣ
			try {
				addSplitUnit(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("/servlet/Servlet_unit?part=4").forward(request, response);
		default:
			break;
		}
	}
	public void addSplitUnit(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		String number_unit_new_1 = request.getParameter("number_unit_new_1");
		String number_unit_new_2 = request.getParameter("number_unit_new_2");
		String unit_new_check_1 = request.getParameter("unit_new_check_1");
		String unit_new_check_2 = request.getParameter("unit_new_check_2");
		String length_unit_1 = request.getParameter("length_unit_new_1");
		String length_unit_2 = request.getParameter("length_unit_new_2");
		int length_unit_new_1 = 0;
		int length_unit_new_2 = 0;
		if(isNumeric(length_unit_1)&&isNumeric(length_unit_2)){
			length_unit_new_1 = Integer.parseInt(length_unit_1);
			length_unit_new_2 = Integer.parseInt(length_unit_2);
		}
		String location_1 = request.getParameter("location_start_1");
		String location_2 = request.getParameter("location_start_2");
		int location_start_1 = 1;
		int location_start_2 = 1;
		if(isNumeric(location_1)&&isNumeric(location_2)){
			location_start_1 = Integer.parseInt(location_1);
			location_start_2 = Integer.parseInt(location_2);
		}
		String number_unit_old = request.getParameter("number_unit_old");
		if("checked".equals(unit_new_check_1)){
			addSplitUnit_2(number_unit_new_1,number_unit_old,length_unit_new_1,location_start_1);
		}
		if("checked".equals(unit_new_check_2)){
			addSplitUnit_2(number_unit_new_2,number_unit_old,length_unit_new_2,location_start_2);
		}
		DB_Link dbl = new DB_Link();
		//
		dbl.connectDB();
		String sql = "DELETE FROM unit WHERE number_unit='"+number_unit_old+"'";
		dbl.executeUpdate(sql);
		sql = "DELETE FROM relation_unit_process WHERE number_unit='"+number_unit_old+"'";
		dbl.executeUpdate(sql);
		dbl.finishUpdate();
		//
		dbl.disconnectDB();
	}
	public void addSplitUnit_2(String number_unit_new,String number_unit_old,int length_unit,int location) throws SQLException{
		DB_Link dbl = new DB_Link();
		//
		dbl.connectDB();
		String sql = "SELECT * FROM unit WHERE number_unit='"+number_unit_old+"'";
		dbl.executeQuery(sql);
		ResultSet rs = dbl.getRs();
		if(rs.next()){
			sql = "INSERT INTO unit(number_unit,length_unit,tag_conversion,number_order_task,number_craft,available,checked)VALUES('"+number_unit_new+"',"+length_unit+",'2','"+rs.getString("number_order_task")+"','"+rs.getString("number_craft")+"',"+rs.getBoolean("available")+","+rs.getBoolean("checked")+")";
			dbl.executeUpdate(sql);
		}
		dbl.finishQuery();
		Dao_unit du = new Dao_unit();
		du.assign_unit_process(number_unit_new, 1);
		sql = "UPDATE relation_unit_process SET tag_state='1' WHERE number_unit='"+number_unit_new+"' AND location<"+location;
		dbl.executeUpdate(sql);
		//��ӵ�"unit_history"������
		Calendar c = Calendar.getInstance();//���Զ�ÿ��ʱ���򵥶��޸�			
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH); 
		int date = c.get(Calendar.DATE); 
		int hour = c.get(Calendar.HOUR_OF_DAY); 
		int minute = c.get(Calendar.MINUTE); 
		//int second = c.get(Calendar.SECOND); 
		String time_crt = year + "/" + month + "/" + date + "_" +hour + ":" +minute;
		sql = "SELECT * FROM unit_history where number_unit_history='"+number_unit_old+"' AND number_unit='"+number_unit_new+"'";		
		Dao_inputData di = new Dao_inputData();		
		if(!di.isExist(sql)){
			sql = "INSERT INTO unit_history(number_unit_history,number_unit,time_conversion,tag_conversion)VALUES('"+number_unit_old+"','"+number_unit_new+"','"+time_crt+"','2')";
			dbl.executeUpdate(sql);
		}else{
			sql = "UPDATE unit_history SET number_unit_history='"+number_unit_old+"',number_unit='"+number_unit_new+"',time_conversion='"+time_crt+"',tag_conversion='2'";
			dbl.executeUpdate(sql);					
		}
		dbl.finishUpdate();
		//
		dbl.disconnectDB();
	}
	public void splitUnit(HttpServletRequest request, HttpServletResponse response){
		String number_order_task = request.getParameter("number_order_task");
		Dao_inputData di = new Dao_inputData();
		String sql = "SELECT * FROM order_task WHERE number_order_task='"+number_order_task+"'";		
		try {
			if(di.isExist(sql)){
				Dao_unit du = new Dao_unit();
				int number_unit_max = 0;
				try {
					number_unit_max = du.new_number_unit(number_order_task);
				} catch (SQLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				//�¿�����
				String number_unit_new_1 = number_order_task + "-" +(number_unit_max + 1);
				String number_unit_new_2 = number_order_task + "-" +(number_unit_max + 2);
				//�¿�������ʼ
				String name_process_crt = request.getParameter("process_crt");
				String number_craft = request.getParameter("number_craft");			
				int location_start = location(number_craft,name_process_crt);
				request.setAttribute("number_craft", number_craft);
				request.setAttribute("location", location_start);
				request.setAttribute("number_unit_new_1", number_unit_new_1);
				request.setAttribute("number_unit_new_2", number_unit_new_2);			
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void addTransUnit(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		String number_unit_new = request.getParameter("number_unit_new");
		String number_unit_old = request.getParameter("number_unit_old");
		String number_order_task = request.getParameter("number_order_task");
		int location = 1;
		String str = request.getParameter("location_start");
		//System.out.println(str);
		if(str != null){
			//��ȡ�¿�ת�����������¹��յ���ʼ����λ��
			location = Integer.parseInt(str);
			String sql = "UPDATE unit SET number_unit='"+number_unit_new+"',number_order_task='"+number_order_task+"',tag_conversion='1' WHERE number_unit='"+number_unit_old+"'";
			
			DB_Link dbl = new DB_Link();
			//
			dbl.connectDB();
			dbl.executeUpdate(sql);
			sql = "UPDATE relation_unit_process SET number_unit='"+number_unit_new+"' WHERE number_unit='"+number_unit_old+"'";
			//System.out.println(sql);
			dbl.executeUpdate(sql);
			sql = "DELETE FROM relation_unit_process WHERE number_unit='"+number_unit_new+"' AND tag_state=-1";
			dbl.executeUpdate(sql);
			//��ӵ�"unit_history"������
			Calendar c = Calendar.getInstance();//���Զ�ÿ��ʱ���򵥶��޸�			
			int year = c.get(Calendar.YEAR); 
			int month = c.get(Calendar.MONTH); 
			int date = c.get(Calendar.DATE); 
			int hour = c.get(Calendar.HOUR_OF_DAY); 
			int minute = c.get(Calendar.MINUTE); 
			//int second = c.get(Calendar.SECOND); 
			String time_crt = year + "/" + month + "/" + date + "_" +hour + ":" +minute;
			sql = "SELECT * FROM unit_history where number_unit_history='"+number_unit_old+"' AND number_unit='"+number_unit_new+"'";		
			Dao_inputData di = new Dao_inputData();		
			if(!di.isExist(sql)){
				sql = "INSERT INTO unit_history(number_unit_history,number_unit,time_conversion,tag_conversion)VALUES('"+number_unit_old+"','"+number_unit_new+"','"+time_crt+"','1')";
				dbl.executeUpdate(sql);
			}else{
				sql = "UPDATE unit_history SET number_unit_history='"+number_unit_old+"',number_unit='"+number_unit_new+"',time_conversion='"+time_crt+"'";
				dbl.executeUpdate(sql);					
			}
			dbl.finishUpdate();
			//
			dbl.disconnectDB();
			//���"relation_unit_process"������
			Dao_unit du = new Dao_unit();
			du.assign_unit_process(number_unit_new, location);
		}
	}
	public void searchUnit(HttpServletRequest request, HttpServletResponse response, String sessionName){
		String number_unit = request.getParameter("number_unit");
		if(number_unit == null){
			Object obj = request.getSession().getAttribute(sessionName);
			if(obj != null){
				number_unit = (String)obj;
			}
		}
		String sql = "SELECT * FROM unit WHERE number_unit='"+number_unit+"'";
		//System.out.println(sql);
		Dao_inputData di = new Dao_inputData();
		try {
			if(di.isExist(sql)){
				Dao_unit du = new Dao_unit();
				request.getSession().setAttribute(sessionName, number_unit);
				request.setAttribute("message", du.message(number_unit));
			}
			else{
				request.setAttribute("message", null);
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void transUnit(HttpServletRequest request, HttpServletResponse response){
		String number_order_task = request.getParameter("number_order_task");
		Dao_inputData di = new Dao_inputData();
		String sql = "SELECT * FROM order_task WHERE number_order_task='"+number_order_task+"'";		
		try {
			if(di.isExist(sql)){
				Dao_unit du = new Dao_unit();
				int number_unit_max = 0;
				try {
					number_unit_max = du.new_number_unit(number_order_task);
				} catch (SQLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				//�¿�����
				String number_unit_new = number_order_task + "-" +(number_unit_max + 1);
				//�¿�������ʼ
				String name_process_crt = request.getParameter("process_crt");
				String number_craft = "";
				sql = "SELECT number_craft FROM order_task WHERE number_order_task='"+number_order_task+"'"; 
				DB_Link dbl = new DB_Link();
				//
				dbl.connectDB();
				dbl.executeQuery(sql);
				ResultSet rs = dbl.getRs();
				if(rs.next()){
					number_craft = rs.getString("number_craft");
				}
				dbl.finishQuery();
				//
				dbl.disconnectDB();
				int location_start = location(number_craft,name_process_crt);
				request.setAttribute("number_craft", number_craft);
				request.setAttribute("location", location_start);
				request.setAttribute("number_unit", number_unit_new);
				request.setAttribute("number_order_task", number_order_task);				
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public int location(String number_craft,String name_process) throws SQLException{
		int location = 1;
		String sql = "SELECT location FROM relation_craft_process a LEFT JOIN proces b ON a.number_process=b.number_process WHERE name_process='"+name_process+"' AND number_craft='"+number_craft+"' ORDER BY a.location LIMIT 1";
		DB_Link dbl = new DB_Link();
		//
		dbl.connectDB();
		dbl.executeQuery(sql);
		ResultSet rs = dbl.getRs();
		if(rs.next()){
			location = rs.getInt("location");
		}
		dbl.finishQuery();
		//
		dbl.disconnectDB();
		return location;
	}
	public boolean isNumeric(String str){
		for (int i = 0; i < str.length(); i++){
			//System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	 }
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
