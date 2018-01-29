package lk.ysu.workingschedule.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lk.ysu.workingschedule.db.DB_Link;
import lk.ysu.workingschedule.model.Data_mainOrder;

public class Dao_mainOrder {
	 public Data_mainOrder message(String number_order_main) throws SQLException {
	        String sql = "SELECT * FROM order_main a LEFT JOIN client b on a.number_client = b.number_client WHERE number_order_main='"+number_order_main+"' ORDER BY number_order_main";     	
	        Data_mainOrder mainOrder = new Data_mainOrder();
	        //
	        DB_Link dbl = new DB_Link();
	        //
	        dbl.connectDB();
	        dbl.executeQuery(sql);
	        ResultSet rs = dbl.getRs();
	        //System.out.println(sql);
	        while (rs.next()) {  
	        	mainOrder.setNumber_order_main(rs.getString("number_order_main"));
	        	mainOrder.setDate_delivery(rs.getString("date_delivery"));
	        	mainOrder.setName_client(rs.getString("name_client"));
	        	mainOrder.setChecked(rs.getBoolean("checked"));
	        }
	        dbl.finishQuery();
	        //
	        dbl.disconnectDB();
			return mainOrder;  
	    }
	   public List<Data_mainOrder> messages(int page,String sql) throws SQLException {
	    	List<Data_mainOrder> mainOrder = new ArrayList<Data_mainOrder>();
	    	Dao_page dp = new Dao_page(); 
	    	//
	    	dp.open();
	    	dp.con_page(page, sql);
	        ResultSet rs = dp.getDbl().getRs();    
	        while (rs.next()) {  
	        	Data_mainOrder dm = new Data_mainOrder();
	        	dm.setNumber_order_main(rs.getString("number_order_main"));
	        	dm.setDate_delivery(rs.getString("date_delivery"));
	        	dm.setName_client(rs.getString("name_client"));
	        	dm.setChecked(rs.getBoolean("checked"));
	        	mainOrder.add(dm);	
	        }
	        dp.close_con_page();
	        //
	        dp.close();
			return mainOrder;  
	    }
}
