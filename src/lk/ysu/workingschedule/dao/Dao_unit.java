package lk.ysu.workingschedule.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lk.ysu.workingschedule.db.DB_Link;
import lk.ysu.workingschedule.model.Data_unit;

public class Dao_unit {
    public List<Data_unit> messages(int page,String sql) throws SQLException {
    	List<Data_unit> unit = new ArrayList<Data_unit>();
    	Dao_page dp = new Dao_page(); 
    	//
    	dp.open();
    	dp.con_page(page, sql);    	
        ResultSet rs = dp.getDbl().getRs(); 
        //System.out.println(page+"-----"+sql);
        while (rs.next()) {  
        	Data_unit du = new Data_unit();        	
        	du.setNumber_unit(rs.getString("number_unit"));
        	du.setLength_unit(rs.getInt("length_unit"));
        	du.setNumber_craft(rs.getString("number_craft"));
        	du.setName_craft(rs.getString("name_craft"));
        	du.setChecked(rs.getBoolean("checked"));
        	unit.add(du);				
        }
        dp.close_con_page();
        dp.close();
		return unit;  
    }	
    public Data_unit message(String number_unit) throws SQLException {
    	String sql = "SELECT * FROM unit a LEFT JOIN craft b on a.number_craft = b.number_craft WHERE number_unit = '"+number_unit+"'";
    	Data_unit unit = new Data_unit();
        //
        DB_Link dbl = new DB_Link();
        //
        dbl.connectDB();
        dbl.executeQuery(sql);
        ResultSet rs = dbl.getRs();	
        while (rs.next()) {  
        	unit.setNumber_unit(rs.getString("number_unit"));
        	unit.setLength_unit(rs.getInt("length_unit"));
        	unit.setNumber_craft(rs.getString("number_craft"));
        	unit.setName_craft(rs.getString("name_craft"));
        	unit.setNumber_order_task(rs.getString("number_order_task"));
        	String name_process_crt = name_process_crt(rs.getString("number_unit"));
        	//System.out.println(name_process_crt);
        	unit.setName_process_crt(name_process_crt);
        	unit.setChecked(rs.getBoolean("checked"));			
        }
        dbl.finishQuery();
        //
        dbl.disconnectDB();
		return unit;  
    }	
  //依据卡号和起始工序位置，在"relation_unit_process"表中添加卡和工序（其对应工艺包括的工序）对应数据
  	public void assign_unit_process(String number_unit,int location) throws SQLException{
      	DB_Link dbl = new DB_Link();
      	//
      	dbl.connectDB();    	
  		String sql1 = "DELETE FROM relation_unit_process WHERE number_unit='"+number_unit+"'";
  		dbl.executeUpdate(sql1);
  		dbl.finishUpdate();
  		String sql2 = "SELECT * FROM unit a LEFT JOIN craft b ON a.number_craft=b.number_craft WHERE available=TRUE AND checked=TRUE AND number_unit='"+number_unit+"'";
  		String number_craft = "";
  		int count_process = 0;
  		//System.out.println();
      	dbl.executeQuery(sql2);
      	ResultSet rs = dbl.getRs();
  		if(rs.next()){
  			number_craft = rs.getString("number_craft");
  			count_process = rs.getInt("count_process");
  		}
  		dbl.finishQuery();
  		//
  		dbl.disconnectDB();
  		if(count_process > 0){
  			for(int i=location;i<=count_process;i++){
  				String sql3 = "SELECT number_process FROM relation_craft_process WHERE number_craft='"+number_craft+"' AND location='"+i+"'";
  		    	DB_Link dbl2 = new DB_Link();
  		    	//
  		    	dbl2.connectDB();
  		    	dbl2.executeQuery(sql3);
  		    	rs = dbl2.getRs();
  				String number_process = "";
  				if(rs.next()){
  					number_process = rs.getString("number_process");
  				}
  				dbl2.finishQuery();
  				//
  				
  				String sql4 = "INSERT INTO relation_unit_process(number_unit,location,number_process,tag_state)VALUES('"+number_unit+"',"+i+",'"+number_process+"','-1')";
  				//System.out.println(sql4);
  				dbl2.executeUpdate(sql4);
  				dbl2.finishUpdate();
  				//
  				dbl2.disconnectDB();
  			}
  		}
  	}
  	//将所有可用、经过核实的卡（不包括拆卡、转卡得到的新卡）和对应工序（默认起始位置"1"）的数据添加到数据库
  	public void assign_unit_process_2() throws SQLException{
  		String sql = "SELECT number_unit FROM unit WHERE available=TRUE AND checked=TRUE AND tag_conversion='0'";
      	DB_Link dbl = new DB_Link();
      	//
      	dbl.connectDB();
      	dbl.executeQuery(sql);
      	ResultSet rs = dbl.getRs();
  		while(rs.next()){
  			String number_unit = rs.getString("number_unit");
  			assign_unit_process(number_unit,1);
  		}
  		dbl.finishQuery();
  		//
  		dbl.disconnectDB();
  	}
  	//获取卡当前处理工序名称
  	public String name_process_crt(String number_unit) throws SQLException{
  		String name_process_crt = "";
  		String sql = "SELECT * FROM relation_unit_process a LEFT JOIN proces b ON a.number_process=b.number_process WHERE number_unit='"+number_unit+"' AND tag_state!=1 ORDER BY location LIMIT 1";
  		DB_Link dbl = new DB_Link();
  		//
  		dbl.connectDB();
  		dbl.executeQuery(sql);
  		ResultSet rs = dbl.getRs();  		
  		if(rs.next()){
  			name_process_crt = rs.getString("name_process");
  		}
  		dbl.finishQuery();
  		//
  		dbl.disconnectDB();
  		//System.out.println(sql);
  		return name_process_crt;
  	}
    public int new_number_unit(String number_order_task) throws SQLException{
    	String site_str = "";
    	int site_int = 0;
    	String sql = "SELECT number_unit FROM unit WHERE number_order_task='"+number_order_task+"'";
    	DB_Link dbl = new DB_Link();
    	//
    	dbl.connectDB();
    	dbl.executeQuery(sql);
    	ResultSet rs = dbl.getRs();
    	while(rs.next()){
    		String number_unit2 = rs.getString("number_unit");
    		site_str = number_unit2.substring(number_unit2.lastIndexOf("-")+1);
			//System.out.println(site_str);
    		if(isNumeric(site_str)){
    			if(site_int < Integer.parseInt(site_str)){
    				site_int = Integer.parseInt(site_str);
    			}
    		}
    	}
    	dbl.finishQuery();
    	dbl.disconnectDB();
    	return site_int;
    }
    
    //判断字符串是否为数字
    public static boolean isNumeric(String str){  
    	  for (int i = str.length();--i>=0;){    
    	   if (!Character.isDigit(str.charAt(i))){  
    	    return false;  
    	   }  
    	  }  
    	  return true;  
    }  
    
    public int location_crt(String number_unit) throws SQLException{
		int location = 0;
    	String sql3 = "SELECT * FROM relation_unit_process a LEFT JOIN proces b ON a.number_process=b.number_process WHERE tag_state='完成' AND number_unit='"+number_unit+"' ORDER BY location DESC LIMIT 0,1";
    	DB_Link dbl = new DB_Link();
    	//
    	dbl.connectDB();
    	dbl.executeQuery(sql3);
    	ResultSet rs = dbl.getRs();
    	if(rs.next()){
    		location = rs.getInt("location");
    	}
    	dbl.finishQuery();
    	dbl.disconnectDB();
    	return location;
    }

}
