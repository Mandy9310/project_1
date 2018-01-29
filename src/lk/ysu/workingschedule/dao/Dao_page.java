package lk.ysu.workingschedule.dao;

import java.sql.SQLException;

import lk.ysu.workingschedule.db.DB_Link;

public class Dao_page {
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	final int MAX_ITEMS_NUM = 15;
	DB_Link dbl = new DB_Link();
	public DB_Link getDbl() {
		return dbl;
	}
	public void open(){
		dbl.connectDB();
	}
	public void close() throws SQLException{
		dbl.disconnectDB();
	}
	//按列表显示数据
	public void con_page(int page,String sql) throws SQLException{
		dbl.createPs(sql);
    	dbl.getPs().setInt(1, (page - 1) * MAX_ITEMS_NUM);  
    	dbl.getPs().setInt(2, MAX_ITEMS_NUM);           
        dbl.setRs(dbl.getPs().executeQuery());
        //System.out.println(sql);
    } 
	//关闭
	public void close_con_page() throws SQLException{
		if(dbl.getRs() != null){
			dbl.getRs().close();
		}
		dbl.closePs();
	}
	public int get_pages(String sql) throws SQLException {   
        int countPage = 0;  
        int total = 0;        
        //查询记录条数，然后把查询结果另起别名num  
        //String sql = "SELECT COUNT(*) AS num FROM " + name_table;       
        try {
			dbl.executeQuery(sql);
			if (dbl.getRs().next()) {  
				//total为留言的总条数  
				total = dbl.getRs().getInt("num");   
		    }            
		   //总页数=总条数/每页显示最大留言数，能除尽时直接取结果，不能除尽时，结果加1，多加一页来显示  
			countPage = (total % MAX_ITEMS_NUM == 0 ? total / MAX_ITEMS_NUM : total / MAX_ITEMS_NUM + 1); 
		    if (countPage != 0){  
		    	//System.out.println(sql+"---"+countPage);
		        return countPage;  
		    }
		    return countPage + 1;   //没有第0页，所以加1  
        } catch (SQLException e) {  
        	//
            throw new RuntimeException(e.getMessage(), e);  
        }  
    }
	public void close_get_pages(){
		dbl.finishQuery();
	}
}
