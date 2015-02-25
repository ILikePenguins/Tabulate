package adapter;

public class Customers
{

	private String name;
	private String customer_id;
	
	
	
	public Customers(String name, String customer_id) {
		this.name = name;
		this.customer_id = customer_id;
	}
	
	public Customers()
	{
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	
}
