package lk.ysu.workingschedule.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lk.ysu.workingschedule.dao.Dao_inputData;
import lk.ysu.workingschedule.dao.Dao_mainOrder;
import lk.ysu.workingschedule.dao.Dao_page;
import lk.ysu.workingschedule.dao.Dao_taskOrder;
import lk.ysu.workingschedule.dao.Dao_unit;
import lk.ysu.workingschedule.db.DB_Link;

public class Servlet_messageList extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public Servlet_messageList() {
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
		//PrintWriter out = response.getWriter();
		//���ֹ����������ù��ܻ��ֱ�־"part"Ĭ��ֵ
		int part = 1;	
		String str = request.getParameter("part");
		if(str == null){
			Object obj = request.getSession().getAttribute("part_messageList");
			if(obj != null){
				part = (Integer) obj;
			}
		}else{
			part = Integer.parseInt(str);
		}
		
		switch(part){
		case 1:	
			//���ذ������Ų�ѯ�ĵ�����¼
			searchMainOrder(request,response);
			request.getRequestDispatcher("../mainOrder.jsp").forward(request,response);
			break;
		case 2:
			//�������ж�����¼
			try {
				mainOrders(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("../mainOrderList.jsp").forward(request,response);
			break;
		case 3:
			//ȷ�ϰ������Ų�ѯ�ĵ�����¼
			try {
				checkMainOrder(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("/servlet/Servlet_messageList?part=1").forward(request,response);
			break;
		case 4:
			//ȷ�϶����б��еļ�¼
			try {
				checkMainOrder(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("/servlet/Servlet_messageList?part=2").forward(request,response);
			break;
		case 5:
			//�鿴�������Ų�ѯ�ĵ�����¼��Ӧ�Ŀ���Ϣ
			mainOrderNumber(request,response);
			request.getRequestDispatcher("/servlet/Servlet_messageList?part=1").forward(request,response);
			break;
		case 6:
			//�鿴�����б�ĵ�����¼��Ӧ�Ŀ���Ϣ
			mainOrderNumber(request,response);
			request.getRequestDispatcher("/servlet/Servlet_messageList?part=2").forward(request,response);
			break;
		case 7:
			//��ʾ��ѯ��������Ӧ���������б�
			try {
				taskOrders(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("../taskOrderList.jsp").forward(request,response);
			break;
		case 8:
			//���ذ��������Ų�ѯ�ĵ�����¼
			searchTaskOrder(request,response);
			request.getRequestDispatcher("../taskOrder.jsp").forward(request,response);
			break;
		case 9:
			//����������������¼
			try {
				taskOrders(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("../taskOrderList_2.jsp").forward(request,response);
			break;
		case 10:
			//ȷ�ϰ��������Ų�ѯ�ĵ�����¼
			try {
				checkTaskOrder(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("/servlet/Servlet_messageList?part=8").forward(request,response);
			break;
		case 11:
			//ȷ���������б��еļ�¼
			try {
				checkTaskOrder(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("/servlet/Servlet_messageList?part=9").forward(request,response);
			break;	
		case 12:
			//�鿴���������Ų�ѯ�ĵ�����¼��Ӧ�Ŀ���Ϣ
			taskOrderNumber(request,response);
			request.getRequestDispatcher("/servlet/Servlet_messageList?part=8").forward(request,response);
			break;
		case 13:
			//�鿴�������б�ĵ�����¼��Ӧ�Ŀ���Ϣ
			taskOrderNumber(request,response);
			request.getRequestDispatcher("/servlet/Servlet_messageList?part=9").forward(request,response);
			break;
		case 14:
			//��ʾ��ѯ����������Ӧ�Ŀ��б�
			try {
				units(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("../unitList.jsp").forward(request,response);
			break;
		case 15:
			//���ذ�����Ų�ѯ�ĵ�����¼
			searchUnit(request,response);
			request.getRequestDispatcher("../unit.jsp").forward(request,response);
			break;
		case 16:
			//�������п���¼
			try {
				units(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("../unitList_2.jsp").forward(request,response);
			break;
		case 17:
			//ȷ�ϰ�������Ų�ѯ�ĵ�����¼
			try {
				checkUnit(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("/servlet/Servlet_messageList?part=15").forward(request,response);
			break;
		case 18:
			//ȷ�ϰ�������Ų�ѯ�ĵ�����¼
			try {
				checkUnit(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("/servlet/Servlet_messageList?part=16").forward(request,response);
			break;			
		default:
			break;
		}
	}
	
	public int page(HttpServletRequest request, HttpServletResponse response,String pageSession){
		//��ȡ��ǰҳ���ҳ��
		String str = request.getParameter("page");
		int page;
		if(str != null){	
			page = Integer.parseInt(str);								
			//��ҳ������洢��session�У�������ҳ����תʱ���»�ȡԭҳ��
			request.getSession().setAttribute(pageSession, page);
		}else{
			Object obj = request.getSession().getAttribute(pageSession);
			if(obj != null){
				//���û��ֱ�ӵ�ҳ�������ѡȡ֮ǰ�洢��session�е�ҳ��
				page = (Integer) request.getSession().getAttribute(pageSession);
			}else{
				page = 1;
			}				
		}	
		//System.out.println(page);
		return page;
	}
	public void taskOrders(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		int page = 1;
		String number_order_main = request.getParameter("number_order_main");
		
		String sql = "SELECT * FROM order_main WHERE number_order_main='"+number_order_main+"'";
		String sql2 = "";
		String sql3 = "";
		Dao_inputData di = new Dao_inputData();
		try {
			//System.out.println(sql);
			if(di.isExist(sql)){
				//��������ֵ��Ϊ�գ���Ϊ�������б�ҳ���ڵ��õ��������б�
				sql2 = "SELECT COUNT(*) AS num FROM order_task WHERE number_order_main='"+number_order_main+"'";
				sql3 = "SELECT * FROM order_task WHERE number_order_main='"+number_order_main+"' ORDER BY number_order_task LIMIT ?,?";
				page = page(request,response,"page_taskOrderList");		       
				request.setAttribute("src", "/servlet/Servlet_messageList?part=7&number_order_main="+number_order_main);
			}else{
				//��������ֵΪ�գ���Ϊ����������ҳ���ڵ��õ��������б�				
				sql2 = "SELECT COUNT(*) AS num FROM order_task";
				sql3 = "SELECT * FROM order_task ORDER BY number_order_task LIMIT ?,?";
				page = page(request,response,"page_taskOrderList_2");
				request.setAttribute("src", "/servlet/Servlet_messageList?part=9");
			}
			//System.out.println(sql2);
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		//��ȡ������Ŀ��Ҫ����ҳ��
		int count_pages = 0;	
		Dao_page dp = new Dao_page();
		//
		dp.open();
		count_pages = dp.get_pages(sql2);
		dp.close_get_pages();
		//
		dp.close();
		
        request.setAttribute("count_pages", count_pages);  
        //System.out.println(sql2);
        //System.out.println();
        request.setAttribute("page", page);
        //System.out.println(page);
		Dao_taskOrder dt = new Dao_taskOrder();
		request.setAttribute("messages", dt.messages(page, sql3));
	}
	public void mainOrderNumber(HttpServletRequest request, HttpServletResponse response){
		String number_order_main = request.getParameter("number_order_main");
		request.getSession().setAttribute("number_order_main", number_order_main);
	}
	public void searchMainOrder(HttpServletRequest request, HttpServletResponse response){
		String number_order_main = request.getParameter("number_order_main");
		String sql = "SELECT * FROM order_main WHERE number_order_main='"+number_order_main+"'";
		Dao_inputData di = new Dao_inputData();
		try {
			if(di.isExist(sql)){
				Dao_mainOrder dm = new Dao_mainOrder();
				request.setAttribute("message", dm.message(number_order_main));
			}
			else{
				request.getSession().setAttribute("number_order_main", null);
				//System.out.println("");
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void mainOrders(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		//��ȡ��ǰҳ���ҳ��
		String pageSession = "page_mainOrderList";
		String str = request.getParameter("page");
		int page;
		if(str != null){	
			page = Integer.parseInt(str);								
			//��ҳ������洢��session�У�������ҳ����תʱ���»�ȡԭҳ��
			request.getSession().setAttribute(pageSession, page);
		}else{
			Object obj = request.getSession().getAttribute(pageSession);
			if(obj != null){
				//���û��ֱ�ӵ�ҳ�������ѡȡ֮ǰ�洢��session�е�ҳ��
				page = (Integer) request.getSession().getAttribute(pageSession);
			}else{
				page = 1;
			}				
		}		
		//��ȡ������Ŀ��Ҫ����ҳ��
		int count_pages = 0;	
		String sql = "SELECT COUNT(*) AS num FROM order_main";	
		Dao_page dp = new Dao_page();
		//
		dp.open();
		count_pages = dp.get_pages(sql);
		dp.close_get_pages();
		//
		dp.close();
		
        request.setAttribute("count_pages", count_pages);  
        //System.out.println();
        request.setAttribute("page", page);
        request.setAttribute("src", "/servlet/Servlet_messageList?part=2");
        
		sql = "SELECT * FROM order_main";
		Dao_mainOrder dm = new Dao_mainOrder();
		DB_Link dbl = new DB_Link();
		//
		dbl.connectDB();
		dbl.executeQuery(sql);
		ResultSet rs = dbl.getRs();
		while(rs.next()){
			sql = "SELECT * FROM order_main a LEFT JOIN client b on a.number_client = b.number_client ORDER BY number_order_main LIMIT ?,?";
			request.setAttribute("messages", dm.messages(page, sql));
		}
		dbl.finishQuery();
		//
		dbl.disconnectDB();
	}
	public void checkMainOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		String number_order_main = request.getParameter("number_order_main");
		String checked = request.getParameter("checked");
		boolean isChecked = false;
		
		if("checked".equals(checked)){
			isChecked = true;
		}
		String sql = "UPDATE order_main SET checked="+isChecked+" WHERE number_order_main='"+number_order_main+"'";		
		String sql2 = "UPDATE order_task SET checked="+isChecked+" WHERE number_order_main='"+number_order_main+"'";
		String sql3 = "UPDATE unit a LEFT JOIN order_task b ON a.number_order_task=b.number_order_task SET a.checked="+isChecked+" WHERE number_order_main='"+number_order_main+"'";
		DB_Link dbl = new DB_Link();
		//
		dbl.connectDB();
		dbl.executeUpdate(sql);
		dbl.executeUpdate(sql2);
		dbl.executeUpdate(sql3);
		dbl.finishQuery();
		//
		dbl.disconnectDB();
		//System.out.println(checked+number_order_main);
	}
	public void searchTaskOrder(HttpServletRequest request, HttpServletResponse response){
		String number_order_task = request.getParameter("number_order_task");
		String sql = "SELECT * FROM order_task WHERE number_order_task='"+number_order_task+"'";
		Dao_inputData di = new Dao_inputData();
		try {
			if(di.isExist(sql)){
				Dao_taskOrder dm = new Dao_taskOrder();
				request.setAttribute("message", dm.message(number_order_task));
			}
			else{
				request.getSession().setAttribute("number_order_task", null);
				//System.out.println("");
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void checkTaskOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		String number_order_task = request.getParameter("number_order_task");
		String checked = request.getParameter("checked");
		boolean isChecked = false;
		
		if("checked".equals(checked)){
			isChecked = true;
		}
		String sql = "UPDATE order_task SET checked="+isChecked+" WHERE number_order_task='"+number_order_task+"'";
		String sql2 = "UPDATE unit SET checked="+isChecked+" WHERE number_order_task='"+number_order_task+"'";
		DB_Link dbl = new DB_Link();
		//
		dbl.connectDB();
		dbl.executeUpdate(sql);
		dbl.executeUpdate(sql2);
		dbl.finishQuery();
		//
		dbl.disconnectDB();
		//System.out.println(checked+number_order_main);
	}
	public void taskOrderNumber(HttpServletRequest request, HttpServletResponse response){
		String number_order_task = request.getParameter("number_order_task");
		request.getSession().setAttribute("number_order_task", number_order_task);
	}
	public void units(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		int page = 1;
		String number_order_task = request.getParameter("number_order_task");
		
		String sql = "SELECT * FROM order_task WHERE number_order_task='"+number_order_task+"'";
		String sql2 = "";
		String sql3 = "";
		Dao_inputData di = new Dao_inputData();
		try {
			//System.out.println(sql);
			if(di.isExist(sql)){
				//����������ֵ��Ϊ�գ���Ϊ����������ҳ���ڵ��õĿ��б�	
				sql2 = "SELECT COUNT(*) AS num FROM unit WHERE number_order_task='"+number_order_task+"'";
				sql3 = "SELECT * FROM unit a LEFT JOIN craft b on a.number_craft = b.number_craft WHERE number_order_task='"+number_order_task+"' ORDER BY number_order_task LIMIT ?,?";
				page = page(request,response,"page_unitList");	
				request.setAttribute("src", "/servlet/Servlet_messageList?part=14&number_order_task="+number_order_task);
			}else{
				//����������ֵΪ�գ���Ϊ�������б�ҳ���ڵ��õĿ��б�	
				sql2 = "SELECT COUNT(*) AS num FROM unit";
				sql3 = "SELECT * FROM unit a LEFT JOIN craft b on a.number_craft = b.number_craft ORDER BY number_unit LIMIT ?,?";
				page = page(request,response,"page_unitList_2");
				request.setAttribute("src", "/servlet/Servlet_messageList?part=16");
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		//��ȡ������Ŀ��Ҫ����ҳ��
		int count_pages = 0;	
		Dao_page dp = new Dao_page();
		//
		dp.open();
		count_pages = dp.get_pages(sql2);
		dp.close_get_pages();
		//
		dp.close();
		
        request.setAttribute("count_pages", count_pages);  
        //System.out.println();
        request.setAttribute("page", page);
        
		Dao_unit du = new Dao_unit();
		
		request.setAttribute("messages", du.messages(page, sql3));
	}
	public void searchUnit(HttpServletRequest request, HttpServletResponse response){
		String number_unit = request.getParameter("number_unit");
		String sql = "SELECT * FROM unit WHERE number_unit='"+number_unit+"'";
		Dao_inputData di = new Dao_inputData();
		try {
			if(di.isExist(sql)){
				Dao_unit du = new Dao_unit();
				request.setAttribute("message", du.message(number_unit));
			}
			else{
				request.getSession().setAttribute("number_unit", null);
				//System.out.println("");
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void checkUnit(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		String number_unit = request.getParameter("number_unit");
		String length_unit = request.getParameter("length_unit");
		int length_unit_2 = 0;
		String checked = request.getParameter("checked");
		boolean isChecked = false;
		
		if("checked".equals(checked)){
			isChecked = true;
		}
		String sql = "UPDATE unit SET checked="+isChecked+" WHERE number_unit='"+number_unit+"'";		
		if(isNumeric(length_unit)&&isChecked){
			length_unit_2 = Integer.parseInt(length_unit);
			sql = "UPDATE unit SET checked="+isChecked+",length_unit = "+length_unit_2+" WHERE number_unit='"+number_unit+"'";
		}
		//System.out.println(sql);
		DB_Link dbl = new DB_Link();
		//
		dbl.connectDB();
		dbl.executeUpdate(sql);
		dbl.finishQuery();
		//
		dbl.disconnectDB();
		//System.out.println(checked+number_order_main);
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
