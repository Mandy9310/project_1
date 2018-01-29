package lk.ysu.workingschedule.model;

public class Data_mainOrder {
	public String getNumber_order_main() {
		return number_order_main;
	}
	public void setNumber_order_main(String number_order_main) {
		this.number_order_main = number_order_main;
	}
	public String getDate_delivery() {
		return date_delivery;
	}
	public void setDate_delivery(String date_delivery) {
		this.date_delivery = date_delivery;
	}
	public String getNumber_client() {
		return number_client;
	}
	public void setNumber_client(String number_client) {
		this.number_client = number_client;
	}
	public String getName_client() {
		return name_client;
	}
	public void setName_client(String name_client) {
		this.name_client = name_client;
	}
	String number_order_main;
	String date_delivery;
	String number_client;
	String name_client;
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	boolean checked;
}
