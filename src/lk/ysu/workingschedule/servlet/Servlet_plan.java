package lk.ysu.workingschedule.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.servlet.ServletUtilities;

import lk.ysu.workingschedule.dao.Dao_inputData;
import lk.ysu.workingschedule.dao.Dao_machine;
import lk.ysu.workingschedule.dao.Dao_page;
import lk.ysu.workingschedule.dao.Dao_plan;
import lk.ysu.workingschedule.dao.Dao_unit;

public class Servlet_plan extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public Servlet_plan() {
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
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");	
		//PrintWriter out = response.getWriter(); 
		//���ֹ����������ù��ܻ��ֱ�־"part"Ĭ��ֵ
		int part = 0;	
		String str = request.getParameter("part");
		if(str == null){
			Object obj = request.getSession().getAttribute("part_plan");
			if(obj != null){
				part = (Integer) obj;
			}
		}else{
			part = Integer.parseInt(str);
		}
		
		switch(part){
		case 1:	
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			///*
			//����ʼ�������Ĵ���ӹ���ϸ�������򣬽����͹��򣨶�Ӧ�ÿ����ջ��ֵĹ��򣩵Ķ�Ӧ������ӵ�"unit_process"��
			Dao_unit du = new Dao_unit();
			try {
				du.assign_unit_process_2();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			//���ɼƻ�
			Dao_plan dp = new Dao_plan();
			try {
				dp.generate_plan_unit();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			//*/
			response.setHeader("refresh", "0;url='../workSchedule.jsp'");
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			break;
		case 2:
			try {
				taskOrderNumber(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("../taskOrderPlan.jsp").forward(request,response);
			break;
		case 3:
			try {
				taskOrderPlan(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("../planList.jsp").forward(request,response);
			break;
		case 4:
			try {
				unitNumber(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("../unitPlan.jsp").forward(request,response);
			break;
		case 5:
			try {
				unitPlan(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("../planList.jsp").forward(request,response);
			break;
		case 6:
			try {
				machineNumber(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			request.getRequestDispatcher("../machinePlan.jsp").forward(request,response);
			break;
		case 7:		
			try {
				machinePlan(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}			
			break;
		case 8:		
			try {
				machineState(request,response);
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}			
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
	public void taskOrderNumber(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		String number_order_task = request.getParameter("number_order_task");
		Dao_inputData di = new Dao_inputData();
		String sql = "SELECT * FROM order_task WHERE number_order_task='"+number_order_task+"'";
		if(di.isExist(sql)){
			request.getSession().setAttribute("number_order_task_TOP", number_order_task);
			request.getSession().setAttribute("page_taskOrderPlan", 1);
		}		
		//System.out.println(request.getSession().getAttribute("number_order_task_TOP"));
	}
	public void taskOrderPlan(HttpServletRequest request, HttpServletResponse response) throws SQLException{		 
		Object obj = request.getSession().getAttribute("number_order_task_TOP");
		if(obj != null){
			int page = page(request,response,"page_taskOrderPlan");		       			
			String number_order_task = (String)obj;
			//System.out.println(number_order_task);
			request.setAttribute("src", "/servlet/Servlet_plan?part=3");
			String sql = "SELECT COUNT(*) AS num FROM relation_unit_machine a LEFT JOIN unit b ON a.number_unit=b.number_unit WHERE number_order_task='"+number_order_task+"'";
			//��ȡ������Ŀ��Ҫ����ҳ��
			int count_pages = 0;	
			Dao_page dp = new Dao_page();
			//
			dp.open();
			count_pages = dp.get_pages(sql);
			dp.close_get_pages();
			//
			dp.close();
			request.setAttribute("page", page); 
			request.setAttribute("count_pages", count_pages); 
			sql = "SELECT * FROM relation_unit_machine a LEFT JOIN unit b ON a.number_unit=b.number_unit LEFT JOIN relation_unit_process c ON a.number_unit=c.number_unit AND a.location=c.location LEFT JOIN machine d ON a.number_machine=d.number_machine WHERE number_order_task='"+number_order_task+"' ORDER BY a.number_unit,a.location,a.time_start_plan LIMIT ?,?";
			Dao_plan plan = new Dao_plan();
			request.setAttribute("messages", plan.messages(page, sql));
		}
	}
	public void unitNumber(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		String number_unit = request.getParameter("number_unit");
		Dao_inputData di = new Dao_inputData();
		String sql = "SELECT * FROM unit WHERE number_unit='"+number_unit+"'";
		if(di.isExist(sql)){
			request.getSession().setAttribute("number_unit_UP", number_unit);
			request.getSession().setAttribute("page_unitPlan", 1);
		}		
		//System.out.println();
	}
	public void unitPlan(HttpServletRequest request, HttpServletResponse response) throws SQLException{		 
		Object obj = request.getSession().getAttribute("number_unit_UP");
		if(obj != null){
			int page = page(request,response,"page_unitPlan");		       			
			String number_unit = (String)obj;
			//System.out.println(number_order_task);
			request.setAttribute("src", "/servlet/Servlet_plan?part=5");
			String sql = "SELECT COUNT(*) AS num FROM relation_unit_machine a LEFT JOIN relation_unit_process b ON a.number_unit=b.number_unit AND a.location=b.location LEFT JOIN machine c ON a.number_machine=c.number_machine WHERE a.number_unit='"+number_unit+"'";
			//��ȡ������Ŀ��Ҫ����ҳ��
			int count_pages = 0;	
			Dao_page dp = new Dao_page();
			//
			dp.open();
			count_pages = dp.get_pages(sql);
			dp.close_get_pages();
			//
			dp.close();
			request.setAttribute("page", page); 
			request.setAttribute("count_pages", count_pages); 
			sql = "SELECT * FROM relation_unit_machine a LEFT JOIN relation_unit_process b ON a.number_unit=b.number_unit AND a.location=b.location LEFT JOIN machine c ON a.number_machine=c.number_machine WHERE a.number_unit='"+number_unit+"' ORDER BY a.number_unit,a.location,a.time_start_plan LIMIT ?,?";
			Dao_plan plan = new Dao_plan();
			request.setAttribute("messages", plan.messages(page, sql));
		}
	}
	public void machineNumber(HttpServletRequest request, HttpServletResponse response) throws SQLException{		
		String browseWay = request.getParameter("input_2");
		request.getSession().setAttribute("browseWay_MP", browseWay);
		String number_machine = request.getParameter("input_1");
		Dao_inputData di = new Dao_inputData();
		String sql = "SELECT * FROM machine WHERE number_machine='"+number_machine+"'";
		if(di.isExist(sql)){
			request.getSession().setAttribute("number_machine_MP", number_machine);
		}		
		//System.out.println();
	}
	public void machinePlan(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{	
		Object obj = request.getSession().getAttribute("number_machine_MP");
		if(obj != null){
			String number_machine = (String)obj;
			obj = request.getSession().getAttribute("browseWay_MP");
			if(obj != null){
				String browseWay = (String)obj;
				if("unitList".equals(browseWay)){
					int page = page(request,response,"page_machinePlan");		       			
					//System.out.println(number_order_task);
					request.setAttribute("src", "/servlet/Servlet_plan?part=7");
					String sql = "SELECT COUNT(*) AS num FROM relation_unit_machine WHERE number_machine='"+number_machine+"'";
					//��ȡ������Ŀ��Ҫ����ҳ��
					int count_pages = 0;	
					Dao_page dp = new Dao_page();
					//
					dp.open();
					count_pages = dp.get_pages(sql);
					dp.close_get_pages();
					//
					dp.close();
					request.setAttribute("page", page); 
					request.setAttribute("count_pages", count_pages); 
					sql = "SELECT * FROM relation_unit_machine a LEFT JOIN machine b ON a.number_machine=b.number_machine LEFT JOIN relation_unit_process c ON a.number_unit=c.number_unit AND a.location=c.location WHERE a.number_machine='"+number_machine+"' ORDER BY a.time_start_plan LIMIT ?,?";
					Dao_plan plan = new Dao_plan();
					request.setAttribute("messages", plan.messages(page, sql));
				}
			}
		}
		request.getRequestDispatcher("../planList.jsp").forward(request,response);
	}
	public void machineState(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{	
		Object obj = request.getSession().getAttribute("number_machine_MP");
		if(obj != null){
			String number_machine = (String)obj;
			obj = request.getSession().getAttribute("browseWay_MP");
			if(obj != null){
				String browseWay = (String)obj;
				if("stateGraph".equals(browseWay)){
					int count_machine = 1;
					Dao_machine dm = new Dao_machine();
					
					try {
						count_machine = dm.count_plan(number_machine);
						dm.createChart("","","", dm.createDataset(number_machine));
						//dm.createChart("��̨��--"+number_machine,"��ʼʱ��<�����>","����ʱ�䣨Сʱ��", dm.createDataset(number_machine));
					} catch (SQLException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
					if(count_machine == 0){
						count_machine = 5;
					}
					int width_pic = 1000;
					if(width_pic < 40*count_machine){
						width_pic = 40*count_machine;
					}
					//ServletUtilities������web�����Ĺ����࣬����һ���ַ����ļ���,
					//�ļ����Զ����ɣ����ɺõ�ͼƬ���Զ����ڷ���������ʱ�ļ��£�temp��
					String filename = ServletUtilities.saveChartAsPNG(dm.getChart(), width_pic, 300, null, request.getSession());
					 
					//�����ļ���ȥ��ʱĿ¼��Ѱ�Ҹ�ͼƬ��
					//�����/DisplayChart·��Ҫ�������ļ����û��Զ����һ��(web.xml!!!!)
					String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;					 
					request.setAttribute("imgurl", graphURL);
					request.setAttribute("number_machine", number_machine);
					//����ҳ�� 
					 request.getRequestDispatcher("../machineState.jsp").forward(request,response);	
				}
			}
		}
       
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
