package com.example.tabulate;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

import table.Row;
import table.SalesTable;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import database.AsyncResponse;
import database.Database;

public class AddBeerActivity extends FragmentActivity implements AsyncResponse
{
	LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
	private SalesTable table;
	private String customer_id;
	private Queue<LinkedHashMap<String,String>> q= new LinkedList<LinkedHashMap<String,String>>();
	 public void onCreate(Bundle savedInstanceState) 
	    {
		 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.add_beer_sale);
	        TextView name = (TextView)findViewById(R.id.tvCustomer);////
	        name.setText(getIntent().getExtras().getString("name"));
	        customer_id=(getIntent().getExtras().getString("customer_id"));
	        System.out.println("cust "+customer_id);
	        Button  btnProfile = (Button)findViewById(R.id.btnProfile);
	        btnProfile.setOnClickListener(new ProfileListener());
	        addToMap("","");
	        //get bottles from db
	        new Database (map,"sales/retrieveBottlesAndPintsSales",this);
	       // new Database (map,"beer/retrieveBottlesAndPints",this).execute();
	       // new Database (map,"customers/get_beers.php",this).execute();
	    }
	
	  public void addToMap(String product_id, String quantity)
	  {
	  	    map.put("product_id", product_id);
	        map.put("quantity", quantity);
	        map.put("customer_id",getIntent().getExtras().getString("customer_id"));
	        map.put("event_id", getIntent().getExtras().getString("event_id"));
	       // System.out.println(product_id+","+quantity+",");
	  }	  
	
	public void processFinish(String output) 
	{
		//System.out.println("output "+output);
		if(output.contains("name"))
		{
			String[] colNames={"Name","dec","Quantity","inc","Cost_Each","Type"};
			String[] rowNames={"name","quantity","cost_each","type","id"};
			table= new SalesTable(output,(TableLayout) findViewById(R.id.tableSales),this,colNames,rowNames);
			table.buildTable();
		}
		else if(!q.isEmpty()&& output.contains("sales/newSale"))
		{
			System.out.println("boop");
			new Database (q.remove(),"sales/newSale",this);
			
		}
		else if(q.isEmpty() && output.contains("updated"))
			{
				System.out.println("starting profile");
				startProfileActivity();
			}
		else
			System.out.println("output "+output);
	}
	
	public void rowsChanged()
	{
		// make new thread for queries
		int count =0;
	    for (Entry<Integer, Row> entry : table.getRows().entrySet()) 
	    {
		    Row value = (Row)entry.getValue();
		    if(value.isChanged())
		    {
		    	map= new LinkedHashMap<String, String>();
		    	addToMap(value.getProductId(),value.getQuantity()+"");
		    	if(count==0)
		    	{
			    	new Database (map,"sales/newSale",this);
		    	}
		    	else
		    	
		    		q.add(map);
		    	
		    	count++;
		    }
		    
		}
	    
	    if(count==0)
	    	startProfileActivity();
	    
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
	
    class ProfileListener implements OnClickListener
    {

    	  public void onClick(View v)
    	    {
    		  rowsChanged();
    		
    		//  Thread updateThread = new Thread(new UpdateDB());
    		 // updateThread.start();
    		
    	    }
    }
    
    public void startProfileActivity()
    {
    	  Intent profileIntent = new Intent(AddBeerActivity.this,ProfileActivity.class);
	        //loads map if it is not loaded already
  		  profileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	      //pass values to the map activity
  		  profileIntent.putExtra("name", getIntent().getExtras().getString("name"));
  		  profileIntent.putExtra("event_id",getIntent().getExtras().getString("event_id"));
  		  profileIntent.putExtra("customer_id",getIntent().getExtras().getString("customer_id"));
	        //start profile activity
	      	startActivity(profileIntent);
    }

}
