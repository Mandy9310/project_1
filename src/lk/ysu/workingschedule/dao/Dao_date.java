package lk.ysu.workingschedule.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lk.ysu.workingschedule.db.DB_Link;

public class Dao_date {
	//�Զ����еĽ������ݣ�2016/4/1��ʽ�����и�ʽ�淶����2016/04/01��ʽ�����Ա㰴��������
		public void date_delivery() throws SQLException{
			String sql = "SELECT * FROM order_main";
			String sql_2 = "";
			String date_delivery = "";
			String number_order_main = "";
	    	DB_Link dbl = new DB_Link();
	    	//
	    	dbl.connectDB();
	    	dbl.executeQuery(sql);
	    	ResultSet rs = dbl.getRs();
			while(rs.next()){
				number_order_main = rs.getString("number_order_main");
				date_delivery = rs.getString("date_delivery");
				date_delivery = date_format(date_delivery);		
				sql_2 = "UPDATE order_main SET date_delivery='"+date_delivery+"' WHERE number_order_main='"+number_order_main+"'";
				dbl.executeUpdate(sql_2);
				dbl.finishUpdate();
			}
			dbl.finishQuery();
			//
			dbl.disconnectDB();
		}
		//�����ڸ�ʽ"2012/4/1"�淶Ϊ"2012/04/01"
		public String date_format(String str){
			String year;
			String month;
			String date;
			year = str.substring(0, 4);
			int site_month = getFromIndex(str,"/",1);
			int site_date = getFromIndex(str,"/",2);
			month = str.substring(site_month+1, site_date);
			if(month.length() == 1){
				month = "0" + month;
			}
			date = str.substring(site_date+1);
			if(date.length() == 1){
				date = "0" + date;
			}		
			return year+"/"+month+"/"+date;
		}
		
		//���ַ���modelStr���ַ���str�е�count�γ���ʱ���±�  
		public int getFromIndex(String str, String modelStr, Integer count) {  
		    	//�����ַ�������ƥ��  
		        Matcher slashMatcher = Pattern.compile(modelStr).matcher(str);  
		    int index = 0;  
		       //matcher.find();���Բ������ģʽƥ����������е���һ��������  
		       while(slashMatcher.find()) {  
		        index++;  
		        //��modelStr�ַ���count�γ��ֵ�λ��  
		        if(index == count){  
		           break;  
		        }  
		    }  
		       //matcher.start();������ǰƥ��ĳ�ʼ������  
		       return slashMatcher.start();  
		}  
		public int get_year(String str){
			int year = 0;
			String year_str = "";
			int site = 0;
			site = getFromIndex(str,"/",1);
			year_str = str.substring(0, site);
			year = Integer.parseInt(year_str);
			return year;
		}
		public int get_month(String str){
			int month = 0;
			String month_str = "";
			int site1 = 0;
			int site2 = 0;
			site1 = getFromIndex(str,"/",1);
			site2 = getFromIndex(str,"/",2);
			month_str = str.substring(site1+1, site2);
			month = Integer.parseInt(month_str);
			return month;
		}
		public int get_date(String str){
			int date = 0;
			String date_str = "";
			int site = 0;
			site = getFromIndex(str,"/",2);
			date_str = str.substring(site+1);
			date = Integer.parseInt(date_str);
			return date;
		}
}
