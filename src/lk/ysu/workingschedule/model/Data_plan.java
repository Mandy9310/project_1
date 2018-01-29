package lk.ysu.workingschedule.model;

public class Data_plan {
	public String getNumber_unit() {
		return number_unit;
	}
	public void setNumber_unit(String number_unit) {
		this.number_unit = number_unit;
	}
	public String getName_machine() {
		return name_machine;
	}
	public void setName_machine(String name_machine) {
		this.name_machine = name_machine;
	}
	public String getTime_start_plan() {
		return time_start_plan;
	}
	public void setTime_start_plan(String time_start_plan) {
		this.time_start_plan = time_start_plan;
	}
	public String getTime_end_plan() {
		return time_end_plan;
	}
	public void setTime_end_plan(String time_end_plan) {
		this.time_end_plan = time_end_plan;
	}
	public String getTime_start_actual() {
		return time_start_actual;
	}
	public void setTime_start_actual(String time_start_actual) {
		this.time_start_actual = time_start_actual;
	}
	public String getTime_end_actual() {
		return time_end_actual;
	}
	public void setTime_end_actual(String time_end_actual) {
		this.time_end_actual = time_end_actual;
	}
	
	public String getTag_state() {
		return tag_state;
	}
	public void setTag_state(String tag_state) {
		this.tag_state = tag_state;
	}
	String number_unit;
	String name_machine;
	String time_start_plan;
	String time_end_plan;
	String time_start_actual;
	String time_end_actual;
	String tag_state;
}
