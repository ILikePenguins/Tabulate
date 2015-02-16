package com.example.tabulate;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import table.Row;
import table.SalesTable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TableLayout;
import android.widget.TextView;
import database.AsyncResponse;
import database.Database;

public class AddBeerActivity extends FragmentActivity implements AsyncResponse
{
	LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
	private SalesTable table;
	 public void onCreate(Bundle savedInstanceState) 
	    {
		 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.add_beer_sale);
	        
	        TextView name = (TextView)findViewById(R.id.tvCustomer);////
	        name.setText(getIntent().getExtras().getString("name"));
	        
	        addToMap("","","","","");
	        //get bottles from db
	        new Database (map,"beer/retrieveBottlesAndPints",this).execute();
	       // new Database (map,"customers/get_beers.php",this).execute();
	    }
	
	  public void addToMap(String name, String keg, String costp,String costb,String qb)
	  {
	  	    map.put("name", name);
	        map.put("quantity_keg", keg);
	        map.put("cost_pint", costp);
	        map.put("cost_bottle", costb);
	        map.put("quantity_bottle", qb);
	        map.put("event_id", getIntent().getExtras().getString("event_id"));
	  }	  
	
	public void processFinish(String output) 
	{
		String[] colNames={"Name","dec","Quantity","inc","Cost_Each","Type","Id"};
		String[] rowNames={"name","quantity","cost_each","type","id"};
		table= new SalesTable(output,(TableLayout) findViewById(R.id.tableSales),this,colNames,rowNames);
		table.buildTable();
		
	}
	
	public void rowsChanged()
	{
	    for (Entry<Integer, Row> entry : table.getRows().entrySet()) 
	    {
		    Integer key = entry.getKey();
		    Row value = (Row)entry.getValue();
		    if(value.isChanged())
		    	System.out.println("key: "+key+value.toString());
		}
	}
	
//	public static class DecListener implements OnClickListener
//	{
//		public void onClick(View v) 
//		{
//			System.out.println(v.getId());
//			System.out.println("click");
//			
//		}
//	}
	
	

}