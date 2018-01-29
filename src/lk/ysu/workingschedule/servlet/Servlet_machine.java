package lk.ysu.workingschedule.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lk.ysu.workingschedule.dao.Dao_inputData;
import lk.ysu.workingschedule.dao.Dao_machine;
import lk.ysu.workingschedule.dao.Dao_page;
import lk.ysu.workingschedule.db.DB_Link;

public class Servlet_machine extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public Servlet_machine() {
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
		request.setCharacterEncoding("UTF-8");
		//PrintWriter out = response.getWriter();
		//���ֹ����������ù��ܻ��ֱ�־"part"Ĭ��ֵ
		int part = 1;	
		String str = request.getParameter("part");
		if(str == null){
			Object obj = request.getSession().getAttribute("part_machine");
			if(obj != null){
				part = (Integer) obj;
			}
		}else{
			part = Integer.parseInt(str);
		}
		
		switch(part){
		case 1:			
			addMachine(request,response);
	 		request.getRequestDispatcher("../addMachine.jsp").forward(request,response);
			break;
		case 2:
			searchMachine(request,response);
			request.getRequestDispatcher("../machineMsg.jsp").forward(request,response);
			break;
		case 3:
			changeMsg(request,response);
			request.getRequestDispatcher("../machineMsg.jsp").forward(request,response);
			break;
		case 4:
			dltMachine(request,response);
			request.getRequestDispatcher("../machineMsg.jsp").forward(request,response);
			break;
		case 5:		
			machineList(request,response);
			break;
		case 6:
			changeMsg(request,response);
			machineList(request,response);
			break;
		case 7:
			dltMachine(request,response);
			machineList(request,response);
			break;
		default:
			break;
		}
	}
	public void addMachine(HttpServletRequest request, HttpServletResponse response){
		String str2_1 = null;
		String str2_2 = null;
		String str2_3 = null;
		String str2_4 = null;
		String str2_5 = null;

		str2_1 = request.getParameter("input_1");
		str2_2 = request.getParameter("input_2");
		str2_3 = request.getParameter("input_3");
		str2_4 = request.getParameter("input_4");
		str2_5 = request.getParameter("input_5");
				
		String number_type_machine = "";
		//��������Ӣ����д������׻���"DDJ"
		String abbr_type_machine = "";
		//��ȡ��������������"��ë"
		String name_type_machine = "";
			
		//��ȡ��ӵĻ�̨������Ϣ		
		String number_machine = "";	
		String name_machine = "";			
		int count_inlet = 1;
		int bound_upper = 1;
		int bound_lower = 1;
		String name_department = "";

		//���������ͺ������������Ʋ�Ϊ��
		if(str2_1 != "" && str2_5 != ""){
			//��ȡ��̨��źͻ�̨����							
			try {
				number_type_machine = str2_1;
				Dao_machine dbm1 = new Dao_machine();
				//��ǰ��ӻ�̨�������������ͻ����еı��
				int num = dbm1.count_machine(number_type_machine)+1;
				//��ȡ��̨���
				abbr_type_machine = dbm1.name_machine(number_type_machine);
				if(num < 10){
					number_machine = abbr_type_machine + "0" + num;
				}else{
					number_machine = abbr_type_machine + num;
				}
				request.getSession().setAttribute("b_number_machine_1", number_machine);
				//��ȡ��̨����	
				String sql1 = "SELECT name_type_machine FROM type_machine WHERE number_type_machine = '"+number_type_machine+"'";
				DB_Link dbl = new DB_Link();
				//
				dbl.connectDB();
				dbl.executeQuery(sql1);
				ResultSet rs = dbl.getRs();
				if(rs.next()){
					name_type_machine = rs.getString("name_type_machine") + "��";
				}
				dbl.finishQuery();
				//
				dbl.disconnectDB();
				name_machine = num + "#" + name_type_machine;
				request.getSession().setAttribute("b_name_machine_1", name_machine);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		
			count_inlet = Integer.parseInt(str2_2);
			bound_upper = Integer.parseInt(str2_3);
			bound_lower = Integer.parseInt(str2_4);
			name_department = str2_5;
			
			String sql1_2 = "INSERT INTO machine(number_machine,name_machine,state_machine,count_inlet,inlet_busy,bound_upper,bound_lower,number_type_machine,name_department)VALUES('"+number_machine+"','"+name_machine+"','����','"+count_inlet+"','0','"+bound_upper+"','"+bound_lower+"','"+number_type_machine+"','"+name_department+"')";
			Dao_inputData di = new Dao_inputData();
			try {
				di.data_update(sql1_2);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}else{
			request.getSession().setAttribute("b_number_machine_1", "");
		}	
	}
	public void searchMachine(HttpServletRequest request, HttpServletResponse response){
		String number_machine = request.getParameter("number_machine");
		if(number_machine != null && number_machine != ""){
			String sql = "SELECT * FROM machine WHERE number_machine='"+number_machine+"'";
			Dao_machine dm = new Dao_machine();
			try {
				request.setAttribute("message", dm.message(sql));
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}
	public void dltMachine(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String number_machine = request.getParameter("number_machine");	
		String sql = "DELETE FROM machine WHERE number_machine='"+number_machine+"'";
		Dao_inputData di = new Dao_inputData();
		try {
			di.data_update(sql);
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	public void changeMsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String number_machine = request.getParameter("number_machine");
		String input_1 = "";
		String input_2 = "";
		int input_2_int = 0;
		String input_3 = "";
		int input_3_int = 0;
		String input_4 = "";
		int input_4_int = 0;
		String input_5 = "";
		if(number_machine != null){
			input_1 = request.getParameter("input_1_"+number_machine);
			input_2 = request.getParameter("input_2_"+number_machine);
			if(input_2 != null){
				input_2_int = Integer.parseInt(input_2);
			}			
			input_3 = request.getParameter("input_3_"+number_machine);
			if(input_3 != null){
				input_3_int = Integer.parseInt(input_3);
			}
			input_4 = request.getParameter("input_4_"+number_machine);
			if(input_4 != null){
				input_4_int = Integer.parseInt(input_4);
			}
			input_5 = request.getParameter("input_5_"+number_machine);			
		}
		String sql = "UPDATE machine SET state_machine='"+input_1+"',count_inlet="+input_2_int+",bound_upper="+input_3_int+",bound_lower="+input_4_int+",name_department='"+input_5+"' WHERE number_machine='"+number_machine+"'";
		Dao_inputData di = new Dao_inputData();
		try {
			di.data_update(sql);
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void machineList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//��ȡ��ǰҳ���ҳ��
		String pageSession = "page1_b";
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
		String sql = "SELECT COUNT(*) AS num FROM machine";			
		String sql_2 = "SELECT * FROM machine LIMIT ?,?";

		try {
			Dao_page dp = new Dao_page();
			//
			dp.open();
			count_pages = dp.get_pages(sql);
			dp.close_get_pages();
			//
			dp.close();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
        request.setAttribute("count_pages", count_pages);  
        //System.out.println();
        request.setAttribute("page", page);
        request.setAttribute("src", "/servlet/Servlet_machine?part=5");
        
        Dao_machine dm = new Dao_machine();
		try {
			request.setAttribute("messages", dm.messages(page, sql_2));
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		request.getRequestDispatcher("../machines.jsp").forward(request,response);
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
