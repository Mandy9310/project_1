package lk.ysu.workingschedule.model;

public class Data_unit {
	public String getNumber_unit() {
		return number_unit;
	}
	public void setNumber_unit(String number_unit) {
		this.number_unit = number_unit;
	}
	public int getLength_unit() {
		return length_unit;
	}
	public void setLength_unit(int length_unit) {
		this.length_unit = length_unit;
	}
	public String getTag_conversion() {
		return tag_conversion;
	}
	public void setTag_conversion(String tag_conversion) {
		this.tag_conversion = tag_conversion;
	}
	public String getNumber_order_task() {
		return number_order_task;
	}
	public void setNumber_order_task(String number_order_task) {
		this.number_order_task = number_order_task;
	}
	public String getNumber_craft() {
		return number_craft;
	}
	public void setNumber_craft(String number_craft) {
		this.number_craft = number_craft;
	}
	public String getNumber_machine() {
		return number_machine;
	}
	public void setNumber_machine(String number_machine) {
		this.number_machine = number_machine;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}	
	public String getName_craft() {
		return name_craft;
	}
	public void setName_craft(String name_craft) {
		this.name_craft = name_craft;
	}
	public String getName_process_crt() {
		return name_process_crt;
	}
	public void setName_process_crt(String name_process_crt) {
		this.name_process_crt = name_process_crt;
	}
	String number_unit;
	int length_unit;
	String tag_conversion;
	String number_order_task;
	String number_craft;
	String name_craft;
	String name_process_crt;
	String number_machine;
	boolean checked;
	String available;
	
	
}
