package lk.ysu.workingschedule.model;

public class Data_taskOrder {
	public String getNumber_order_task() {
		return number_order_task;
	}
	public void setNumber_order_task(String number_order_task) {
		this.number_order_task = number_order_task;
	}
	public String getName_product() {
		return name_product;
	}
	public void setName_product(String name_product) {
		this.name_product = name_product;
	}
	public String getType_product() {
		return type_product;
	}
	public void setType_product(String type_product) {
		this.type_product = type_product;
	}
	public int getLength_product() {
		return length_product;
	}
	public void setLength_product(int length_product) {
		this.length_product = length_product;
	}
	public String getNumber_color() {
		return number_color;
	}
	public void setNumber_color(String number_color) {
		this.number_color = number_color;
	}
	public int getCount_units() {
		return count_units;
	}
	public void setCount_units(int count_units) {
		this.count_units = count_units;
	}
	public String getRatio_complete() {
		return ratio_complete;
	}
	public void setRatio_complete(String ratio_complete) {
		this.ratio_complete = ratio_complete;
	}
	String number_order_task;
	String name_product;
	String type_product;
	int length_product;
	String number_color;
	int count_units;
	String ratio_complete;
	boolean checked;
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
