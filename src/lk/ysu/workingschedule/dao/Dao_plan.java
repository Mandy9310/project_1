package lk.ysu.workingschedule.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lk.ysu.workingschedule.db.DB_Link;
import lk.ysu.workingschedule.model.Data_plan;

public class Dao_plan {
	
	//向显示列表的页面传送卡计划数据
    public List<Data_plan> messages(int page,String sql) throws SQLException {
    	List<Data_plan> plan_unit = new ArrayList<Data_plan>();
    	Dao_page dp = new Dao_page(); 
    	//
    	dp.open();
    	dp.con_page(page, sql);
        ResultSet rs = dp.getDbl().getRs();
        while (rs.next()) {  
        	Data_plan dpu = new Data_plan();
        	dpu.setNumber_unit(rs.getString("number_unit"));
        	dpu.setName_machine(rs.getString("name_machine"));
        	dpu.setTime_start_plan(rs.getString("time_start_plan"));
        	dpu.setTime_end_plan(rs.getString("time_end_plan"));
        	dpu.setTime_start_actual(rs.getString("time_start_actual"));
        	dpu.setTime_end_actual(rs.getString("time_end_actual"));
        	int tag_state_int = -1;
        	String tag_state_str="";
        	tag_state_str = rs.getString("tag_state");
        	if(tag_state_str != null){
        		Integer.parseInt(tag_state_str);
        	}
        	switch(tag_state_int){
        	case -1:
        		tag_state_str="待完成";
        		break;
        	case 0:
        		tag_state_str="处理中";
        		break;
        	case 1:
        		tag_state_str="已完成";
        		break;
    		default:
    			tag_state_str="";
    			break;
        	}
        	dpu.setTag_state(tag_state_str);     	     	
        	//System.out.println("(Dao_a_mainOrder_1)number_order_main="+rs.getString("number_order_main"));
        	plan_unit.add(dpu);				
        }
        dp.close_con_page();
        //
        dp.close();
		return plan_unit;  
    }
    
    //生成卡排产计划
    public void generate_plan_unit() throws SQLException, ParseException{
    	DB_Link dbl = new DB_Link();
    	//
    	dbl.connectDB();
    	String sql_2 = "DELETE FROM relation_unit_machine";
    	dbl.executeUpdate(sql_2);
    	dbl.finishUpdate();
    	String sql_1 = "SELECT a.number_unit,location,number_process FROM relation_unit_process a LEFT JOIN unit b ON a.number_unit=b.number_unit LEFT JOIN order_task c ON b.number_order_task=c.number_order_task ORDER BY c.priority,a.location,a.number_unit";
    	dbl.executeQuery(sql_1);
    	ResultSet rs = dbl.getRs();
    	String number_unit = "";
    	int location = 0;
    	String number_process = "";
    	while(rs.next()){
    		
    		number_unit = rs.getString("number_unit");
    		
    		location = rs.getInt("location");
    		number_process = rs.getString("number_process");
    		add_unit_machine(number_unit,location,number_process);
    		
    	}
    	dbl.finishQuery();
    	//
    	dbl.disconnectDB();
    }
    
    //
    public int time_unit_process(String number_unit,String number_process) throws SQLException{
    	int time_min = 0;
    	String sql_1 = "SELECT length_unit FROM unit WHERE number_unit='"+number_unit+"'";
    	String sql_2 = "SELECT speed_running FROM proces WHERE number_process='"+number_process+"'";
    	int length_unit = 0;
    	int speed_running = 1;
    	DB_Link dbl = new DB_Link();
    	//
    	dbl.connectDB();
    	dbl.executeQuery(sql_1);
    	ResultSet rs = dbl.getRs();
    	if(rs.next()){
    		length_unit = rs.getInt("length_unit");
    	}
    	dbl.finishQuery();
    	//
    	dbl.disconnectDB();
    	DB_Link dbl2 = new DB_Link();
    	//
    	dbl2.connectDB();
    	dbl2.executeQuery(sql_2);
    	rs = dbl2.getRs();
    	if(rs.next()){
    		speed_running = rs.getInt("speed_running");
    	}
    	dbl2.finishQuery();
    	//
    	dbl2.disconnectDB();
    	//速度单位是米/小时，这里返回的是分钟
    	time_min = length_unit * 60 / speed_running;
    	return time_min;
    	
    }
    public String is_inlet_available(String number_machine) throws SQLException{
    	String time_end_plan_latest = "";
    	int count_inlet = 1;
    	String sql_1 = "SELECT count_inlet FROM machine WHERE number_machine='"+number_machine+"'";
    	DB_Link dbl = new DB_Link();
    	//
    	dbl.connectDB();
    	dbl.executeQuery(sql_1);
    	ResultSet rs = dbl.getRs();
    	if(rs.next()){
    		count_inlet = rs.getInt("count_inlet");
    	}
    	String sql_2 = "SELECT * FROM relation_unit_machine WHERE number_machine='"+number_machine+"' ORDER BY time_start_plan DESC LIMIT 0,"+count_inlet;
    	dbl.executeQuery(sql_2);
    	rs = dbl.getRs();
    	int inlet_busy = 0;
    	boolean exist = false;
    	String time_start_plan_latest = "";
    	while(rs.next()){
    		//System.out.println("machine="+number_machine+"inletbusy="+inlet_busy+"inletcount="+count_inlet+"time_start_plan="+rs.getString("time_start_plan"));
			time_end_plan_latest = rs.getString("time_end_plan");
    		if(inlet_busy == 0){			
    			time_start_plan_latest = rs.getString("time_start_plan");
    			inlet_busy++;
    		}else if(rs.getString("time_start_plan").equals(time_start_plan_latest)){
    			inlet_busy++;
    		}else{
    			exist = true;
    			break;    			
    		}		
    	}
    	dbl.finishQuery();
    	//
    	dbl.disconnectDB();
    	if(inlet_busy < count_inlet && exist == false){
    		time_end_plan_latest = "";
    	}
    	
    	//System.out.println("machine="+number_machine+"inlet_busy="+inlet_busy+"count_inlet="+count_inlet+"time_end_plan_latest="+time_end_plan_latest);
    	//System.out.println("---------------------------------");
    	return time_end_plan_latest;
    }
    //
    public void add_unit_machine(String number_unit,int location,String number_process) throws SQLException, ParseException{
    	
    	//获取最早空闲机器的机器号和其最早开始时间
    	String sql_1 = "SELECT number_machine FROM machine a LEFT JOIN proces b ON a.number_type_machine=b.number_type_machine WHERE number_process='"+number_process+"'";
    	String number_machine = "";
    	String time_end_plan_earliest = "";
    	DB_Link dbl2 = new DB_Link();
    	//
    	dbl2.connectDB();
    	dbl2.executeQuery(sql_1);
    	ResultSet rs = dbl2.getRs();
    	//
    	int n = 0;
    	while(rs.next()){
    		String number_machine_2 = rs.getString("number_machine");   
    		String time_end_plan_each = is_inlet_available(number_machine_2);
    	
    		if(n == 0){
    			time_end_plan_earliest = time_end_plan_each;
    			number_machine = number_machine_2;
    			n++;
    		}else if(time_end_plan_earliest.compareTo(time_end_plan_each)>0){
				time_end_plan_earliest = time_end_plan_each;
				number_machine = number_machine_2;
			}
    	}
    	dbl2.finishQuery();
    	//
    	dbl2.disconnectDB();
    	//System.out.println("unit="+number_unit+"location="+location+"number_machine="+number_machine+"time_end_plan_earliest="+time_end_plan_earliest);
    	//System.out.println(".");
    	/*
	    	//获取最早空闲机器的机器号和其最早开始时间
	    	String sql_1 = "SELECT number_machine FROM machine a LEFT JOIN proces b ON a.number_type_machine=b.number_type_machine WHERE number_process='"+number_process+"'";
	    	Dao_data_check ddc = new Dao_data_check();
	    	String number_machine = "";
	    	String sql_2 = "";
	    	String time_end_plan_earliest = "";
	    	String sql_3 = "";
	    	DB_Link dbl = new DB_Link();
	    	//
	    	dbl.connectDB();
	    	dbl.executeQuery(sql_1);
	    	ResultSet rs = dbl.getRs();
	    	while(rs.next()){
	    		String number_machine_2 = rs.getString("number_machine");
	    		sql_2 = "SELECT * FROM relation_unit_machine WHERE number_machine='"+number_machine_2+"'";
	    		//如果该机器号机器还没有安排计划
	    		if(!ddc.isExist(sql_2)){
	    			number_machine = number_machine_2;
	    		}
	    	}
	    	dbl.finishQuery();
	    	//
	    	dbl.disconnectDB();
	    	DB_Link dbl2 = new DB_Link();
	    	//
	    	dbl2.connectDB();
	    	dbl2.executeQuery(sql_1);
	    	rs = dbl2.getRs();
	    	//如果该机器号对应机器已经安排了计划
	    	if(number_machine == ""){
	        	while(rs.next()){
	        		String number_machine_2 = rs.getString("number_machine");
	    			sql_3 = "SELECT time_end_plan FROM relation_unit_machine WHERE number_machine='"+number_machine_2+"' ORDER BY time_end_plan DESC LIMIT 0,1";
	    			
	            	DB_Link dbl3 = new DB_Link();
	            	//
	            	dbl3.connectDB();
	            	dbl3.executeQuery(sql_3);
	            	ResultSet rs2 = dbl3.getRs();
	    			if(rs2.next()){
	    				
	        			if(time_end_plan_earliest == ""){
	        				time_end_plan_earliest = rs2.getString("time_end_plan");
	        				number_machine = number_machine_2;
	        			}else{
	        				if(time_end_plan_earliest.compareTo(rs2.getString("time_end_plan"))>0){
	        					time_end_plan_earliest = rs2.getString("time_end_plan");
	        					number_machine = number_machine_2;
	        				}
	        			}    	
	        			//System.out.println("unit--"+number_unit+"number_machine_2--"+number_machine_2+"number_machine--"+number_machine+"time_end_plan_earliest--"+time_end_plan_earliest+"rs2.getString--"+rs2.getString("time_end_plan"));
	    			}			
	    			dbl3.finishQuery();
	    			//
	    			dbl3.disconnectDB();
	        	}
	    	}
	    	dbl2.finishQuery();
	    	//
	    	dbl2.disconnectDB();
    	*/
    	//求得需要添加记录中的开始时间和结束时间
		String time_start_plan = "";
		int time_unit_process = time_unit_process(number_unit,number_process);
		String time_end_plan = "";
    	int time_staying = 0;
    	String sql_4 = "SELECT time_staying FROM proces WHERE number_process='"+number_process+"'";
    	DB_Link dbl4 = new DB_Link();
    	//
    	dbl4.connectDB();
    	dbl4.executeQuery(sql_4);
    	rs = dbl4.getRs();
    	if(rs.next()){
    		time_staying = rs.getInt("time_staying");
    	}
    	dbl4.finishQuery();
    	//
    	dbl4.disconnectDB();
    	//获取当前时间
    	String time_current = "";
		Date date = new Date();
		//设置时间格式
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		time_current = format.format(date);
		Calendar cal = Calendar.getInstance();
		//获取其前一工序结束时间
		String sql_5 = "SELECT time_end_plan FROM relation_unit_machine WHERE number_unit='"+number_unit+"' AND location="+(location-1);
		String time_end_prior = "";
    	DB_Link dbl5 = new DB_Link();
    	//
    	dbl5.connectDB();
    	dbl5.executeQuery(sql_5);
    	rs = dbl5.getRs();
		if(rs.next()){
			time_end_prior = rs.getString("time_end_plan");
		}
		dbl5.finishQuery();

		if(time_end_prior == null || location == 1){
			time_end_prior = "";
		}
		//获取最早开始时间
		String time_start_earliest = "";
		if(time_end_plan_earliest == "" && time_end_prior == ""){
			time_start_earliest = "";
		}else if(time_end_plan_earliest.compareTo(time_end_prior)>=0){
			time_start_earliest = time_end_plan_earliest;
		}else if(time_end_plan_earliest.compareTo(time_end_prior)<0){
			time_start_earliest = time_end_prior;
		}
		//System.out.println("bijiso "+time_end_plan_earliest.compareTo(time_end_prior)+"time_end_prior="+time_end_prior+"time_end_plan_earliest=="+time_end_plan_earliest+"time_start_earliest=="+time_start_earliest);

    	if(time_start_earliest == ""){
    		time_start_plan = time_current;	
    		date = format.parse(time_start_plan);
			cal.setTime(date);
			cal.add(Calendar.MINUTE, time_unit_process);
			date = cal.getTime();
			time_end_plan = format.format(date);
    	}else{
    		date = format.parse(time_start_earliest);
			cal.setTime(date);
			cal.add(Calendar.MINUTE, time_staying);
			date = cal.getTime();
			time_start_plan = format.format(date);
			//
			cal.add(Calendar.MINUTE,time_unit_process);
			date = cal.getTime();
			time_end_plan = format.format(date);
    	}
    	//System.out.println(number_unit+"time_start_plan--"+time_start_plan+"time_end_plan--"+time_end_plan);
    	String sql_6 = "INSERT INTO relation_unit_machine SET number_unit='"+number_unit+"',location="+location+",number_machine='"+number_machine+"',time_start_plan='"+time_start_plan+"',time_end_plan='"+time_end_plan+"'";
    	dbl5.executeUpdate(sql_6);
    	dbl5.finishUpdate();
    	//
		dbl5.disconnectDB();
    	/*
    	//获取处理本工序类型的机器中，最早空闲机器的运行结束时间、机器号,以及本工序所需的停留时间
    	String sql_1 = "SELECT a.number_machine,time_end_plan FROM relation_unit_machine a LEFT JOIN machine b ON a.number_machine=b.number_machine LEFT JOIN proces c ON b.number_type_machine=c.number_type_machine WHERE number_process='"+number_process+"' ORDER BY time_end_plan LIMIT 0,1";
    	String sql_2 = "SELECT number_machine FROM machine a LEFT JOIN proces b ON a.number_type_machine=b.number_type_machine WHERE number_process='"+number_process+"' ORDER BY number_machine LIMIT 0,1";
    	String sql_3 = "SELECT time_staying FROM proces WHERE number_process='"+number_process+"'";
    	String time_end_plan_last = "";
    	String number_machine = "";

    	int time_staying = 0;
    	rs = dgd.get(sql_1);
    	if(rs.next()){
    		time_end_plan_last = rs.getString("time_end_plan");
    		number_machine = rs.getString("number_machine");
    	}
    	
    	//System.out.println(time_end_plan_last+"---"+number_machine);
    	rs = dgd.get(sql_2);
    	if(rs.next()){
    		number_machine = rs.getString("number_machine");
    	}
    	rs = dgd.get(sql_3);
    	if(rs.next()){
    		time_staying = rs.getInt("time_staying");
    	}
    	dgd.close_db();
    	//获取当前时间
    	String time_current = "";
		Date date = new Date();
		//设置时间格式
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		time_current = format.format(date);
		//求得需要添加记录中的开始时间和结束时间
		String time_start_plan = "";
		int time_unit_process = time_unit_process(number_unit,number_process);
		String time_end_plan = "";
		Calendar cal = Calendar.getInstance();
    	if(time_end_plan_last == null  || time_end_plan_last == "" || number_machine == null){
    		//System.out.println("1");
    		time_start_plan = time_current;	
    		//System.out.println(number_unit+"空开始"+time_start_plan);
    		date = format.parse(time_start_plan);
			cal.setTime(date);
			cal.add(Calendar.MINUTE, time_unit_process);
			date = cal.getTime();
			time_end_plan = format.format(date);
			//System.out.println(number_unit+"空结束"+time_end_plan);
    	}else{
    		//System.out.println("2");
    		date = format.parse(time_end_plan_last);
			cal.setTime(date);
			cal.add(Calendar.MINUTE, time_staying);
			date = cal.getTime();
			time_start_plan = format.format(date);
			//System.out.println(number_unit+"有开始"+time_start_plan);
    		date = format.parse(time_end_plan_last);
			cal.setTime(date);
			cal.add(Calendar.MINUTE, time_staying+time_unit_process);
			date = cal.getTime();
			time_end_plan = format.format(date);
			//System.out.println(number_unit+"有结束"+time_end_plan);
    	}
    	String sql_4 = "INSERT INTO relation_unit_machine SET number_unit='"+number_unit+"',location="+location+",number_machine='"+number_machine+"',time_start_plan='"+time_start_plan+"',time_end_plan='"+time_end_plan+"'";
    	Dao_set_data dsd = new Dao_set_data();
    	dsd.data_update(sql_4);
    	*/
    }
}
