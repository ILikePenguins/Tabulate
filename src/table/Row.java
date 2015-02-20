package table;

import android.widget.TextView;

public class Row
{
	private String product_id;
	private int quantity;
	private TextView tvQuantity;
	private boolean changed;
	private String name;
	private int product_quantity;
	private int original_p_quantity;
	private TextView tv_p_quantity;


	public Row()
	{
		
	}
	public Row(int quantity, String product_id)
	{
		this.product_id=product_id;
		this.quantity=quantity;
		this.original_p_quantity=quantity;
		
	}
	public TextView getTv_p_quantity() {
		return tv_p_quantity;
	}
	public void setTv_p_quantity(TextView tv_p_quantity) {
		this.tv_p_quantity = tv_p_quantity;
	}
	public int getOriginal_p_quantity() {
		return original_p_quantity;
	}
	public int getProduct_quantity() {
		return product_quantity;
	}
	public void setProduct_quantity(int product_quantity) {
		this.product_quantity = product_quantity;
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
	public TextView getTvQuantity() {
		return tvQuantity;
	}
	public void setTvQuantity(TextView tv) {
		this.tvQuantity = tv;
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
