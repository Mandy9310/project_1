package lk.ysu.workingschedule.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lk.ysu.workingschedule.dao.Dao_craft;
import lk.ysu.workingschedule.dao.Dao_date;
import lk.ysu.workingschedule.dao.Dao_taskOrder;
import lk.ysu.workingschedule.db.DB_Link;

public class Servlet_system extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public Servlet_system() {
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
		
		//���ֹ����������ù��ܻ��ֱ�־"part_b"Ĭ��ֵ
		int part = 1;	
		String str = request.getParameter("part");
		if(str == null){
			Object obj = request.getSession().getAttribute("part_system");
			if(obj != null){
				part = (Integer) obj;
			}
		}else{
			part = Integer.parseInt(str);
		}
		
		switch(part){
		case 1:	
			setSystemParam(request,response);
			try {
				//���ɳ�ʼ����ӵ�"unit"��
				generateUnit(request,response);
				//���¹��ն�Ӧ��������ݣ���ӵ�"relation_craft_process"��
				Dao_craft dc = new Dao_craft();
				dc.divide_craft_process();
				//�������н������ݸ�ʽ�淶��
				Dao_date dd = new Dao_date();
				dd.date_delivery();
				//����������������ȼ�
				Dao_taskOrder dt = new Dao_taskOrder();
				dt.set_order_task_priority2();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}

			response.setHeader("refresh", "0;url='../systemParam.jsp'");
			break;	
		default:
			break;
		}
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
	public void setSystemParam(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String input_1 = request.getParameter("length_default");
		String input_2 = request.getParameter("deviation_allowed");
		//����Ĭ�Ͽ��������½�
		int max1 = 3000;
		int min1 = 1500;
		//���ÿɽ���������½�
		int max2 = 300;
		int min2 = 50;
		PrintWriter out = response.getWriter();
		if(isNumeric(input_1)&&isNumeric(input_2)){
			int value_1 = Integer.parseInt(input_1);
			int value_2 = Integer.parseInt(input_2);
			if(value_1 >= min1 && value_1 <=max1 && value_2 >= min2 && value_2 <=max2){
				//�жϳ�ʼʱ�Ƿ����Ĭ�Ͽ�������ϵͳ����
				String sql1 = "SELECT * FROM param_system where order_param = '1'";
				//��û��ִ�в������
				String sql2 = "INSERT param_system(order_param,length_default,deviation_allowed)VALUE('1','"+input_1+"','"+input_2+"')";
				//���ԭ��������ִ�и��²���
				String sql3 = "UPDATE param_system SET length_default = '"+input_1+"',deviation_allowed = '"+input_2+"'WHERE order_param = '1'";
				//������¼sql���Ӱ������ݿ�������
				int result = 0;
		    	DB_Link dbl = new DB_Link();
		    	//
		    	dbl.connectDB();
		    	dbl.executeQuery(sql1);
		    	ResultSet rs = dbl.getRs();
				try {
					if(rs.next()){
						result = dbl.executeUpdate(sql3);
					}else{
						result = dbl.executeUpdate(sql2);
					}		
				} catch (SQLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				dbl.finishUpdate();
				dbl.finishQuery();
				//
				try {
					dbl.disconnectDB();
				} catch (SQLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				if(result > 0){
		    		out.println("<script ;language='javascript'>alert('���ò����ɹ���')</script>");
		    	}else{
		    		out.println("<script ;language='javascript'>alert('���ò���ʧ�ܣ�')</script>");
		    	}	
			}else{
				out.println("<script ;language='javascript'>alert('�������󣡿�Ĭ�ϲ�����Χ��"+min1+"~"+max1+"���������ֵ��Χ��"+min2+"~"+max2+"��')</script>");
			}
		}	
	}
	public void generateUnit(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException{
    	int length_default = 0;
    	int deviation_allowed = 0;
    	String sql="SELECT * FROM param_system";
    	DB_Link dbl = new DB_Link();
    	//
    	dbl.connectDB();
    	dbl.executeQuery(sql);
    	ResultSet rs = dbl.getRs();
    	while(rs.next()){
    		length_default = rs.getInt("length_default");
    		deviation_allowed = rs.getInt("deviation_allowed");
    	}
    	dbl.finishQuery();
    	//����֮ǰĬ�Ͽ������������ֳ����ġ���δ�����Ų��Ŀ�
    	sql = "DELETE FROM unit WHERE checked = FALSE";
    	dbl.executeUpdate(sql);
    	dbl.finishUpdate();
		//��Ĭ�Ͽ���������ֵ��������δ�Ų������ֳɿ�				
    	String number_unit;
    	int length_unit;
    	String number_order_task;
    	int length_product;
    	String number_craft;
    	sql = "SELECT * FROM order_task WHERE tag_division = TRUE";

    	dbl.executeQuery(sql);
    	rs = dbl.getRs(); 	
    	while(rs.next()){   
    		//�����Ļ��ֳ��Ŀ�����
        	int count = 0;
        	//��ƽ������
        	int len_avg= 0;
        	//��ƽ�����Ȼ��ֺ��ʣ�೤��
        	int len_rmd= 0;
        	//Ĭ��ƽ�����ȼ�ȥ�ɽ������
        	int len_min= 0;
        	//Ĭ��ƽ�����ȼ��Ͽɽ������
        	int len_max= 0;
        	
    		number_order_task = rs.getString("number_order_task");
    		length_product = rs.getInt("length_product");
    		number_craft = rs.getString("number_craft");
    		
    		len_min = length_default - deviation_allowed;
    		len_max = length_default + deviation_allowed;
    		len_rmd = length_product % len_min;
    		count = length_product / len_min;
    		if(count == 0){
    			count = 1;
    		}
    		//�жϽ��೤���ȷֵ������ϣ��Ƿ񳬹��������Ͻ�
    		if((len_rmd / count + len_min) < len_max){
    			len_avg = len_rmd / count + len_min;
    			len_rmd = 0;
    		}else{    			
    			len_avg = len_min;
    		}
    		for(int i=1; i<=count; i++){
    			length_unit = len_avg;
    			number_unit = number_order_task +"-"+ i;
    			sql = "insert unit(number_unit,length_unit,tag_conversion,number_order_task,number_craft,available,checked)value('"+number_unit+"','"+length_unit+"','0','"+number_order_task+"','"+number_craft+"',true,true)";
    			dbl.executeUpdate(sql);
    			dbl.finishUpdate();
    		}
    		//���������೤������Ϊһ��
    		if(len_rmd > 0){
    			length_unit = len_rmd;
    			count++;
    			number_unit = number_order_task +"-"+ count;
    			sql = "insert unit(number_unit,length_unit,tag_conversion,number_order_task,number_craft,available,checked)value('"+number_unit+"','"+length_unit+"','0','"+number_order_task+"','"+number_craft+"',true,true)";
    			dbl.executeUpdate(sql);
    			dbl.finishUpdate();
    		}	    		
    	}
    	dbl.finishQuery();
    	//
    	dbl.disconnectDB();
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
