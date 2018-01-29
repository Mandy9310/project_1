package lk.ysu.workingschedule.dao;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import lk.ysu.workingschedule.db.DB_Link;
import lk.ysu.workingschedule.model.Data_machine;

public class Dao_machine {
	public int count_machine(String number_type_machine) throws SQLException{
		int num_int = 1;
		String num_str = "";
		String num_str2 = "";
		String sql1 = "SELECT number_machine FROM machine WHERE number_type_machine = '"+number_type_machine+"'";
		DB_Link dbl = new DB_Link();
		//
		dbl.connectDB();
		dbl.executeQuery(sql1);
		ResultSet rs1 = dbl.getRs();
		while(rs1.next()){
			num_str = rs1.getString("number_machine");
			if(num_str != null && !"".equals(num_str)){
				for(int i=0;i<num_str.length();i++){
					if(num_str.charAt(i)>=48 && num_str.charAt(i)<=57){
						num_str2+=num_str.charAt(i);
					}
				}
			}
			if(num_str2 != "" && num_str2 != null){
				if(Integer.parseInt(num_str2) > num_int){
					num_int = Integer.parseInt(num_str2);
				}
			}
			num_str2 = "";
		}
		dbl.finishQuery();
		//
		dbl.disconnectDB();
		return num_int;		
	}
	
	public String name_machine(String number_type_machine) throws SQLException{
		String name = "";
		String str = "";
		String sql1 = "SELECT number_machine FROM machine WHERE number_type_machine = '"+number_type_machine+"'";	
		DB_Link dbl = new DB_Link();
		//
		dbl.connectDB();
		dbl.executeQuery(sql1);
		ResultSet rs1 = dbl.getRs();
		while(rs1.next()){
			str = rs1.getString("number_machine");
			if(str != null && !"".equals(str)){
				for(int i=0;i<str.length();i++){
					if(!(str.charAt(i)>=48 && str.charAt(i)<=57)){
						name+=str.charAt(i);
					}
				}
			}
			if(name.length() > 0){
				break;
			}		
		}
		dbl.finishQuery();
		//
		dbl.disconnectDB();
		return name;		
	}
	
   public List<Data_machine> messages(int page,String sql) throws SQLException {
    	List<Data_machine> machine = new ArrayList<Data_machine>();
    	Dao_page dp = new Dao_page(); 
    	//
    	dp.open();
    	dp.con_page(page, sql);
        ResultSet rs = dp.getDbl().getRs();
        while (rs.next()) {  
        	Data_machine dm = new Data_machine();
        	dm.setNumber_machine(rs.getString("number_machine"));
        	dm.setName_machine(rs.getString("name_machine"));
        	dm.setState_machine(rs.getString("state_machine"));
        	dm.setCount_inlet(rs.getInt("count_inlet"));
        	dm.setBound_lower(rs.getInt("bound_lower"));
        	dm.setBound_upper(rs.getInt("bound_upper"));
        	dm.setName_department(rs.getString("name_department"));
        	machine.add(dm);	
        }
        dp.close_con_page();
        //
        dp.close();
		return machine;  
    }
   public Data_machine message(String sql) throws SQLException {
		Data_machine machine = new Data_machine();
		DB_Link dbl = new DB_Link();
		//
		dbl.connectDB();
		dbl.executeQuery(sql); 	
		ResultSet rs = dbl.getRs();
		   while (rs.next()) {  
			   machine.setNumber_machine(rs.getString("number_machine"));
			   machine.setName_machine(rs.getString("name_machine"));
			   machine.setState_machine(rs.getString("state_machine"));
			   machine.setCount_inlet(rs.getInt("count_inlet"));
			   machine.setBound_lower(rs.getInt("bound_lower"));
			   machine.setBound_upper(rs.getInt("bound_upper"));
			   machine.setName_department(rs.getString("name_department"));
		
		   }
		dbl.finishQuery();
		//
		dbl.disconnectDB();
		return machine;  
   }
   JFreeChart chart;
	public JFreeChart getChart() {
	    return chart;
	}
	public void setChart(JFreeChart chart) {
	    this.chart = chart;
	}
	/**
	 * ������״ͼ
	 * @param chartTitle ͼ�����
	 * @param xName      x�����
	 * @param yName      y�����
	 * @param dataset    ���ݼ�
	 * @return
	 */
	public JFreeChart createChart(String chartTitle, String xName,String yName, CategoryDataset dataset) {
	    /**
	     * createBarChart�Ĳ����ֱ�Ϊ��
	     * ���⣬��������⣬��������⣬���ݼ���ͼ�귽��ˮƽ����ֱ��
	     * ���Ƿ���ʾͼ�����Ƿ���ʾtooltips���Ƿ�urls
	     */
	    chart = ChartFactory.createBarChart(chartTitle, xName, yName,dataset, PlotOrientation.VERTICAL,true, true, false);
	    /**
	     * VALUE_TEXT_ANTIALIAS_OFF��ʾ�����ֵĿ���ݹر�,ʹ�õĹرտ���ݺ����御��ѡ��12��14�ŵ�������,���������������ÿ�
	     */
	    chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	    // ����ɫ
	    chart.setBackgroundPaint(Color.white);
	    // ���ñ�������
	    chart.getTitle().setFont(new Font("����", Font.ROMAN_BASELINE, 16));
	    // ͼ������ɫ
	    //chart.getLegend().setBackgroundPaint(new Color(110, 182, 229));
	    // ͼ������
	    chart.getLegend().setItemFont(new Font("����", Font.PLAIN, 13));
	 
	    CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
	    // ���������߿ɼ�
	    //categoryPlot.setDomainGridlinesVisible(true);
	    // ����ɫ��
	    //categoryPlot.setDomainGridlinePaint(Color.black);
	    // ���ú����߿ɼ�
	    categoryPlot.setRangeGridlinesVisible(true);
	    // ����ɫ��
	    categoryPlot.setRangeGridlinePaint(Color.black);
	    // ��������͸����
	    categoryPlot.setForegroundAlpha(1.0f);
	    //������ͼ����ɫ��ע�⣬ϵͳȡɫ��ʱ��Ҫʹ��16λ��ģʽ���鿴��ɫ���룬�����Ƚ�׼ȷ��
	    categoryPlot.setBackgroundPaint(new Color(110, 182, 229));
	 
	    /*
	     * categoryPlot.setRangeCrosshairVisible(true);
	     * categoryPlot.setRangeCrosshairPaint(Color.blue);
	     */
	 
	    // ������--��Χ��
	    NumberAxis numberAxis = (NumberAxis) categoryPlot.getRangeAxis();
	    // ������y����������
	    numberAxis.setTickLabelFont(new Font("����", Font.PLAIN, 13));
	    // ������y���������
	    numberAxis.setLabelFont(new Font("����", Font.PLAIN, 13));
	    // ������ߵ�һ�� Item ��ͼƬ���˵ľ���
	     numberAxis.setUpperMargin(0.5);
	    // ������͵�һ�� Item ��ͼƬ�׶˵ľ���
	     numberAxis.setLowerMargin(0.0);
	    // ���ÿ̶ȵ�λ ΪInteger
	    numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	 
	    // ������--����ᡢ��
	    CategoryAxis categoryAxis = categoryPlot.getDomainAxis();
	    // ������x����������
	    categoryAxis.setTickLabelFont(new Font("����", Font.PLAIN, 14));
	    // ������x���������
	    categoryAxis.setLabelFont(new Font("����", Font.PLAIN, 15));
	    // ������λ�ã���б��
	    categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createDownRotationLabelPositions(0.5D));
	    //�����ϵ� Lable�Ƿ�������ʾ
	    categoryAxis.setMaximumCategoryLabelWidthRatio(1.0f);
	    
	    //���þ���ͼƬ��˾���
	    categoryAxis.setLowerMargin(0.0D);
	    // ���þ���ͼƬ�Ҷ˾���
	    categoryAxis.setUpperMargin(0.08D);
	 
	    // ��Ⱦ - �м�Ĳ���
	    BarRenderer barRenderer = (BarRenderer) categoryPlot.getRenderer();
	    // �������ӿ��
	    barRenderer.setMaximumBarWidth(0.02);
	    // �������Ӹ߶�
	    barRenderer.setMinimumBarLength(0.1);
	    // �������ӱ߿���ɫ
	    barRenderer.setBaseOutlinePaint(Color.BLACK);
	    // �������ӱ߿�ɼ�
	    barRenderer.setDrawBarOutline(true);
	    // ����������ɫ
	    barRenderer.setSeriesPaint(0, new Color(0, 255, 0));
	    // ����ÿ����֮�����
	    barRenderer.setItemMargin(5.0D);
	    // ��ʾÿ��������ֵ�����޸ĸ���ֵ����������
	    barRenderer.setIncludeBaseInRange(true);
	    barRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
	    barRenderer.setBaseItemLabelsVisible(true);
	    return chart;
	}
	
	/**
	 * ��״ͼ���ݼ�
	 *
	 * @return
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	public CategoryDataset createDataset(String number_machine) throws SQLException, ParseException {
	    String str1 = "����ʱ��";
		Date date = new Date();
		//����ʱ���ʽ
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		String querySql = "SELECT * FROM relation_unit_machine WHERE number_machine='"+number_machine+"'";
		String number_unit = "";
		String time_start_plan = "";
		String time_end_plan = "";
		DB_Link dbl = new DB_Link();
		//
		dbl.connectDB();
		dbl.executeQuery(querySql);
		ResultSet rs = dbl.getRs();
		while(rs.next()){
			number_unit = rs.getString("number_unit");
			time_start_plan = rs.getString("time_start_plan");
			time_end_plan = rs.getString("time_end_plan");
			date = format.parse(time_end_plan);
			cal.setTime(date);
			double end = cal.getTimeInMillis();
			date = format.parse(time_start_plan);
			cal.setTime(date);
			double start = cal.getTimeInMillis();
			double hours = (end - start) / 1000 / 3600;
			java.text.DecimalFormat df = new java.text.DecimalFormat("#.0");  
			hours = Double.parseDouble(df.format(hours));
			//System.out.println(hours);
			dataset.addValue(hours, str1,time_start_plan+" <"+number_unit+">");
		}
		dbl.finishQuery();
		//
		dbl.disconnectDB();

	    //dataset.addValue(1.0D, str1, str4);
	   
	    return dataset;
	}
	public int count_plan(String number_machine) throws SQLException{
		int count_plan = 1;
		String querySql = "SELECT COUNT(*) AS num FROM relation_unit_machine WHERE number_machine='"+number_machine+"'";
		//System.out.println(querySql);
		DB_Link dbl = new DB_Link();
		dbl.connectDB();
		dbl.executeQuery(querySql);
		ResultSet rs = dbl.getRs();
		if(rs.next()){
			count_plan = rs.getInt("num");
		}
		dbl.finishQuery();
		//
		dbl.disconnectDB();
		return count_plan;
	}
}
