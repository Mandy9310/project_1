package lk.ysu.workingschedule.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import lk.ysu.workingschedule.db.DB_Link;

public class Dao_inputData {
    DB_Link dbl = new DB_Link();
	public void data_update(String sql) throws SQLException{
		//连接
		dbl.connectDB();
		dbl.executeUpdate(sql);
		dbl.finishUpdate();
		//断开
		dbl.disconnectDB();
		
	}
	public void delete_unit(String number_unit) throws SQLException{
		String sql_1 = "UPDATE unit SET available=FALSE WHERE number_unit='"+number_unit+"'";
		String sql_2 = "DELETE FROM relation_unit_machine WHERE number_unit='"+number_unit+"'";
		String sql_3 = "DELETE FROM relation_unit_process WHERE number_unit='"+number_unit+"'";
		data_update(sql_1);
		data_update(sql_2);
		data_update(sql_3);
	}
	//判断查询内容是否存在
	public boolean isExist(String sql) throws SQLException{
		boolean result = false;
       	DB_Link dbl = new DB_Link();
    	//
    	dbl.connectDB();
    	dbl.executeQuery(sql);
    	ResultSet rs = dbl.getRs();
		if(rs.next()){
			result = true;
		}
		dbl.finishQuery();
		//
		dbl.disconnectDB();
		return result;
	}
}
