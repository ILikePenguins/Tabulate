package table;

import android.widget.TextView;

public class Row
{
	private String product_id;
	private int quantity;
	private TextView tv;
	private boolean changed;
	private String name;

	public Row()
	{
		
	}
	public Row(int quantity, String product_id)
	{
		this.product_id=product_id;
		this.quantity=quantity;
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isChanged() {
		return changed;
	}
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	public TextView getTv() {
		return tv;
	}
	public void setTv(TextView tv) {
		this.tv = tv;
	}

	public String getProductId() {
		return product_id;
	}

	public void setProductId(String id) {
		this.product_id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String toString() 
	{
		return "Row [id=" + product_id + ", quantity=" + quantity + "]";
	}

	
}
