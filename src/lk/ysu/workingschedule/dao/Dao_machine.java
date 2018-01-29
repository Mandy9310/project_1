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
	 * 创建柱状图
	 * @param chartTitle 图表标题
	 * @param xName      x轴标题
	 * @param yName      y轴标题
	 * @param dataset    数据集
	 * @return
	 */
	public JFreeChart createChart(String chartTitle, String xName,String yName, CategoryDataset dataset) {
	    /**
	     * createBarChart的参数分别为：
	     * 标题，横坐标标题，纵坐标标题，数据集，图标方向（水平、垂直）
	     * ，是否显示图例，是否显示tooltips，是否urls
	     */
	    chart = ChartFactory.createBarChart(chartTitle, xName, yName,dataset, PlotOrientation.VERTICAL,true, true, false);
	    /**
	     * VALUE_TEXT_ANTIALIAS_OFF表示将文字的抗锯齿关闭,使用的关闭抗锯齿后，字体尽量选择12到14号的宋体字,这样文字最清晰好看
	     */
	    chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	    // 背景色
	    chart.setBackgroundPaint(Color.white);
	    // 设置标题字体
	    chart.getTitle().setFont(new Font("宋体", Font.ROMAN_BASELINE, 16));
	    // 图例背景色
	    //chart.getLegend().setBackgroundPaint(new Color(110, 182, 229));
	    // 图例字体
	    chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 13));
	 
	    CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
	    // 设置纵虚线可见
	    //categoryPlot.setDomainGridlinesVisible(true);
	    // 虚线色彩
	    //categoryPlot.setDomainGridlinePaint(Color.black);
	    // 设置横虚线可见
	    categoryPlot.setRangeGridlinesVisible(true);
	    // 虚线色彩
	    categoryPlot.setRangeGridlinePaint(Color.black);
	    // 设置柱的透明度
	    categoryPlot.setForegroundAlpha(1.0f);
	    //设置柱图背景色（注意，系统取色的时候要使用16位的模式来查看颜色编码，这样比较准确）
	    categoryPlot.setBackgroundPaint(new Color(110, 182, 229));
	 
	    /*
	     * categoryPlot.setRangeCrosshairVisible(true);
	     * categoryPlot.setRangeCrosshairPaint(Color.blue);
	     */
	 
	    // 纵坐标--范围轴
	    NumberAxis numberAxis = (NumberAxis) categoryPlot.getRangeAxis();
	    // 纵坐标y轴坐标字体
	    numberAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 13));
	    // 纵坐标y轴标题字体
	    numberAxis.setLabelFont(new Font("宋体", Font.PLAIN, 13));
	    // 设置最高的一个 Item 与图片顶端的距离
	     numberAxis.setUpperMargin(0.5);
	    // 设置最低的一个 Item 与图片底端的距离
	     numberAxis.setLowerMargin(0.0);
	    // 设置刻度单位 为Integer
	    numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	 
	    // 横坐标--类别轴、域
	    CategoryAxis categoryAxis = categoryPlot.getDomainAxis();
	    // 横坐标x轴坐标字体
	    categoryAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 14));
	    // 横坐标x轴标题字体
	    categoryAxis.setLabelFont(new Font("宋体", Font.PLAIN, 15));
	    // 类别轴的位置，倾斜度
	    categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createDownRotationLabelPositions(0.5D));
	    //横轴上的 Lable是否完整显示
	    categoryAxis.setMaximumCategoryLabelWidthRatio(1.0f);
	    
	    //设置距离图片左端距离
	    categoryAxis.setLowerMargin(0.0D);
	    // 设置距离图片右端距离
	    categoryAxis.setUpperMargin(0.08D);
	 
	    // 渲染 - 中间的部分
	    BarRenderer barRenderer = (BarRenderer) categoryPlot.getRenderer();
	    // 设置柱子宽度
	    barRenderer.setMaximumBarWidth(0.02);
	    // 设置柱子高度
	    barRenderer.setMinimumBarLength(0.1);
	    // 设置柱子边框颜色
	    barRenderer.setBaseOutlinePaint(Color.BLACK);
	    // 设置柱子边框可见
	    barRenderer.setDrawBarOutline(true);
	    // 设置柱的颜色
	    barRenderer.setSeriesPaint(0, new Color(0, 255, 0));
	    // 设置每个柱之间距离
	    barRenderer.setItemMargin(5.0D);
	    // 显示每个柱的数值，并修改该数值的字体属性
	    barRenderer.setIncludeBaseInRange(true);
	    barRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
	    barRenderer.setBaseItemLabelsVisible(true);
	    return chart;
	}
	
	/**
	 * 柱状图数据集
	 *
	 * @return
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	public CategoryDataset createDataset(String number_machine) throws SQLException, ParseException {
	    String str1 = "持续时间";
		Date date = new Date();
		//设置时间格式
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
