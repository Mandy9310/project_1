package lk.ysu.workingschedule.db;

import java.sql.*;

public class DB_Link {
	private String driverStr = "com.mysql.jdbc.Driver";
	private String name_db = "hf_workingschedule";
	private String port_db = "3306";
	private String code_db = "utf8";
	private String connStr = "jdbc:mysql://localhost:"+port_db+"/"+name_db+"?characterEncoding="+code_db;
	private String dbusername = "root";
	private String dbuserpwd = "1234";
	private Connection conn = null;
	private PreparedStatement ps = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	public PreparedStatement getPs() {
		return ps;
	}
	public ResultSet getRs() {
		return rs;
	}
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	//�������ݿ�
	public void connectDB(){
		try{
			Class.forName(driverStr);
			conn = DriverManager.getConnection(connStr, dbusername, dbuserpwd);
			//System.out.println("(DB_Link.java)ͬ���ݿ����ӣ�");
		}
		catch(Exception ex){
			//System.out.println("(DB_Link.java)�޷�ͬ���ݿ����ӣ�");
		}
		
	}
	//�Ͽ����ݿ�
	public void disconnectDB() throws SQLException{
		if(conn != null){
			conn.close();
		}
	}
	//�������ݿ�
	public int executeUpdate(String s){
		int result = 0;
		try{
			stmt = conn.createStatement();
			result = stmt.executeUpdate(s);
			//System.out.println("(DB_Link.java)ִ�и��³ɹ������µ�sql���Ϊ��"+s);
		}
		catch(Exception ex){
			//System.out.println("(DB_Link.java)ִ�и��´���");
		}
		return result;
	}
	//��������
	public void finishUpdate(){
		try{
			if(stmt != null){
				stmt.close();
			}
		}
		catch(Exception ex){}
	}
	//��ѯ
	public void executeQuery(String s){
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(s);
		}
		catch(Exception ex){
			//System.out.println("(DB_Link.java)ִ�в�ѯ����");
		}
	}
	//������ѯ
	public void finishQuery(){
		try{
			if(rs != null){
				rs.close();
			}
			if(stmt != null){
				stmt.close();
			}
		}
		catch(Exception ex){}
	}
	//����
	public void createPs(String s){
		try {
			//System.out.println("(DB_Link.java)conn"+conn);
			ps = conn.prepareStatement(s);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	//�ر�
	public void closePs(){
		try{
			if(ps != null){
				ps.close();
			}
		}
		catch(Exception ex){}
	}
 
}

