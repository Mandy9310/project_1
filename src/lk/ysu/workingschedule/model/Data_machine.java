package lk.ysu.workingschedule.model;

public class Data_machine {
	public String getNumber_machine() {
		return number_machine;
	}
	public void setNumber_machine(String number_machine) {
		this.number_machine = number_machine;
	}
	public String getName_machine() {
		return name_machine;
	}
	public void setName_machine(String name_machine) {
		this.name_machine = name_machine;
	}
	public String getState_machine() {
		return state_machine;
	}
	public void setState_machine(String state_machine) {
		this.state_machine = state_machine;
	}
	public int getCount_inlet() {
		return count_inlet;
	}
	public void setCount_inlet(int count_inlet) {
		this.count_inlet = count_inlet;
	}
	public int getInlet_busy() {
		return inlet_busy;
	}
	public void setInlet_busy(int inlet_busy) {
		this.inlet_busy = inlet_busy;
	}
	public int getBound_upper() {
		return bound_upper;
	}
	public void setBound_upper(int bound_upper) {
		this.bound_upper = bound_upper;
	}
	public int getBound_lower() {
		return bound_lower;
	}
	public void setBound_lower(int bound_lower) {
		this.bound_lower = bound_lower;
	}
	public String getNumber_type_machine() {
		return number_type_machine;
	}
	public void setNumber_type_machine(String number_type_machine) {
		this.number_type_machine = number_type_machine;
	}
	public String getName_department() {
		return name_department;
	}
	public void setName_department(String name_department) {
		this.name_department = name_department;
	}
	String number_machine;
	String name_machine;
	String state_machine;
	int count_inlet;
	int inlet_busy;
	int bound_upper;
	int bound_lower;
	String number_type_machine;
	String name_department;
	
}
