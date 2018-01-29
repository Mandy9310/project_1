package lk.ysu.workingschedule.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import lk.ysu.workingschedule.db.DB_Link;

public class Dao_craft {
	public void divide_craft_process() throws SQLException{
		//删除各工艺划分成工序的原始数据
		DB_Link dbl = new DB_Link();
    	//
    	dbl.connectDB();		
		String sql1 = "DELETE FROM relation_craft_process";
		dbl.executeUpdate(sql1);
		dbl.finishUpdate();
		//获取所有的工艺数据
		String sql2 = "SELECT * FROM craft";

    	dbl.executeQuery(sql2);
    	ResultSet rs = dbl.getRs();
		String number_craft = "";
		String name_craft = "";		
		int count_process = 0;
		
		while(rs.next()){
			number_craft = rs.getString("number_craft");
			name_craft = rs.getString("name_craft");			
			count_process = rs.getInt("count_process");
			//从各工艺名中拆分出其包含的各工序名
			int head = 0;
			int tail = 0;
			for(int i=1;i<=count_process;i++){
				int num = 2;
				for(int j=tail;j<name_craft.length();j++){					
					if(name_craft.charAt(j)=='>'){
						if(num == 2){
							head = j+1;	
							num--;
						}else if(num == 1){
							tail = j-1;
							num--;
						}												
					}
					if(num == 0){
						break;
					}
					if(j == name_craft.length()-1){
						tail = name_craft.length();
					}
				}
				String name_process = name_craft.substring(head, tail);
				//根据各工序名从工序数据表中获取其对应的工序编号
				String sql3 = "SELECT number_process FROM proces WHERE name_process='"+name_process+"'";
				DB_Link dbl2 = new DB_Link();
	        	//
	        	dbl2.connectDB();
	        	dbl2.executeQuery(sql3);
	        	ResultSet rs2 = dbl2.getRs();
				String number_process = "";
				if(rs2.next()){
					number_process = rs2.getString("number_process");
				}
				//插入新的工艺划分成工序的数据
				String sql4 = "INSERT INTO relation_craft_process (number_craft,location,number_process)VALUES('"+number_craft+"','"+i+"','"+number_process+"')";
				dbl.executeUpdate(sql4);
				dbl.finishUpdate();
				dbl2.finishQuery();
				//
				dbl2.disconnectDB();
			}
		}
		dbl.finishQuery();
		//
		dbl.disconnectDB();
	}
}
