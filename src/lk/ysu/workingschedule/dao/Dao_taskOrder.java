package lk.ysu.workingschedule.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lk.ysu.workingschedule.db.DB_Link;
import lk.ysu.workingschedule.model.Data_taskOrder;

public class Dao_taskOrder {
	 public Data_taskOrder message(String number_order_task) throws SQLException {
	        String sql = "SELECT * FROM order_task WHERE number_order_task='"+number_order_task+"' ORDER BY number_order_task";     	
	        Data_taskOrder taskOrder = new Data_taskOrder();
	        //
	        DB_Link dbl = new DB_Link();
	        //
	        dbl.connectDB();
	        dbl.executeQuery(sql);
	        ResultSet rs = dbl.getRs();
	        //System.out.println(sql);
	        while (rs.next()) {  
	        	taskOrder.setNumber_order_task(rs.getString("number_order_task"));
	        	taskOrder.setName_product(rs.getString("name_product"));
	        	taskOrder.setType_product(rs.getString("type_product"));
	        	taskOrder.setNumber_color(rs.getString("number_color"));
	        	taskOrder.setLength_product(rs.getInt("length_product"));
	        	taskOrder.setChecked(rs.getBoolean("checked"));
	        }
	        dbl.finishQuery();
	        //
	        dbl.disconnectDB();
			return taskOrder;  
	    }
	   public List<Data_taskOrder> messages(int page,String sql) throws SQLException {
	    	List<Data_taskOrder> taskOrder = new ArrayList<Data_taskOrder>();
	    	Dao_page dp = new Dao_page(); 
	    	//
	    	dp.open();
	    	dp.con_page(page, sql);
	    	
	        ResultSet rs = dp.getDbl().getRs();    
	        while (rs.next()) {  
	        	Data_taskOrder dt = new Data_taskOrder();
	        	dt.setNumber_order_task(rs.getString("number_order_task"));
	        	dt.setName_product(rs.getString("name_product"));
	        	dt.setType_product(rs.getString("type_product"));
	        	dt.setNumber_color(rs.getString("number_color"));
	        	dt.setLength_product(rs.getInt("length_product"));
	        	dt.setChecked(rs.getBoolean("checked"));
	        	taskOrder.add(dt);	
	        }
	        dp.close_con_page();
	        //
	        dp.close();
			return taskOrder;  
	    }
		public void set_order_task_priority(String number_order_task) throws SQLException{
			int priority = 0;
			int length_product = 0;
			String number_craft = "";
			String date_delivery = "";
			String sql_1 = "SELECT length_product,number_craft,date_delivery FROM order_task a LEFT JOIN order_main b ON a.number_order_main=b.number_order_main WHERE number_order_task='"+number_order_task+"'";
			//System.out.println(sql_1);
	    	DB_Link dbl = new DB_Link();
	    	//
	    	dbl.connectDB();
	    	dbl.executeQuery(sql_1);
	    	ResultSet rs1 = dbl.getRs();
			if(rs1.next()){
				length_product = rs1.getInt("length_product");
				number_craft = rs1.getString("number_craft");
				date_delivery = rs1.getString("date_delivery");
			}
			//获取该生产单的花费时间
			int time_total = 0;
			String sql_2 = "SELECT speed_running FROM relation_craft_process a LEFT JOIN proces b ON a.number_process=b.number_process WHERE number_craft='"+number_craft+"'";
	    	dbl.executeQuery(sql_2);
	    	rs1 = dbl.getRs();
			while(rs1.next()){
				int speed_running = rs1.getInt("speed_running");
				if(speed_running > 0){
					time_total += length_product / speed_running;
				}			
			}
			dbl.finishQuery();
			//System.out.println(time_total);
			time_total = time_total / 24;
			//获取交期和当前时间的差值
			Calendar c = Calendar.getInstance();//可以对每个时间域单独修改			
			int year = c.get(Calendar.YEAR); 
			int month = c.get(Calendar.MONTH); 
			int date = c.get(Calendar.DATE); 
			//int hour = c.get(Calendar.HOUR_OF_DAY); 
			//int minute = c.get(Calendar.MINUTE); 
			Dao_date dd = new Dao_date();
			int year2 = dd.get_year(date_delivery);
			int month2 = dd.get_month(date_delivery);
			int date2 = dd.get_date(date_delivery);
			//System.out.println(year+"-"+month+"-"+date);
			//System.out.println(year2+"-"+month2+"-"+date2);
			priority = (year2-year)*365 + (month2-month)*30 + (date2-date) - time_total;
			String sql_3 = "UPDATE order_task SET priority="+priority+" WHERE number_order_task='"+number_order_task+"'";
			dbl.executeUpdate(sql_3);
			dbl.finishUpdate();
			//
			dbl.disconnectDB();
		}
		public void set_order_task_priority2() throws SQLException{
			String sql_1 = "SELECT number_order_task FROM order_task";
	    	DB_Link dbl = new DB_Link();
	    	//
	    	dbl.connectDB();
	    	dbl.executeQuery(sql_1);
	    	ResultSet rs1 = dbl.getRs();
			String number_order_task = "";
			while(rs1.next()){
				number_order_task = rs1.getString("number_order_task");
				set_order_task_priority(number_order_task);
			}
			dbl.finishQuery();
			//
			dbl.disconnectDB();
		}
}
