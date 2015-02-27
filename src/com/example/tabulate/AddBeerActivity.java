package com.example.tabulate;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import table.Row;
import table.SalesTable;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
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
	private Queue<LinkedHashMap<String,String>> q= new LinkedList<LinkedHashMap<String,String>>();
	private boolean salesClicked;
	private boolean backClicked;
	 public void onCreate(Bundle savedInstanceState) 
	    {
		 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.add_beer_sale);
	        
	        //set name of customer 
	        TextView name = (TextView)findViewById(R.id.tvCustomer);
	        name.setText(getIntent().getExtras().getString("name"));
	        
	        //buttons
	        Button  btnProfile = (Button)findViewById(R.id.btnProfile);
	        btnProfile.setOnClickListener(new ProfileListener());
	        addToMap("","","","");
	        //get bottles from db
	        new Database (map,"sales/retrieveBottlesAndPintsSales",this);
	    }
	
	  public void addToMap(String product_id, String quantity,String amount,String dec)
	  {
	  	    map.put("product_id", product_id);
	        map.put("quantity", quantity);
	        map.put("amount", amount);
	        map.put("dec",dec);
	        map.put("customer_id",getIntent().getExtras().getString("customer_id"));
	        map.put("event_id", getIntent().getExtras().getString("event_id"));
	       // System.out.println(product_id+","+quantity+",");
	  }	  
	
	public void processFinish(String output) 
	{
		System.out.println("output "+output);
		//date off by a month
		// sales are updating , need to current quanity per customer vs amount purchased, only sale if greater
		// for decrement, need to update the sale id
		if(output.contains("name"))
		{ 
			//build the table
			String[] colNames={"Name","Dec","#Bought","Inc","#Stock","Price","Type"};
			String[] rowNames={"name","quantity","PQ","cost_each","type","id"};
			table= new SalesTable(output,(TableLayout) findViewById(R.id.tableSales),this,colNames,rowNames);
			table.buildTable();
		}
		else if(!q.isEmpty()&& output.contains("sales/newSale"))
		{
			//update the sales
			System.out.println("boop");
			new Database (q.remove(),"sales/newSale",this);
			
		}
		else if(q.isEmpty() && output.contains("updated")
				|| output.contains("sales/newSale"))
			{
			//start the sales activity once all beers are updated
				if(salesClicked)
				{
					System.out.println("starting profile");
					salesClicked=false;
					startProfileActivity();
				}
				else if(backClicked)
				{
					System.out.println("names activity");
					backClicked=false;
					//startNamesActivity();
				}
			}
		else
			System.out.println("output "+output);
	}
	
	public void addChangedRowsToQueue()
	{
		//get the rows that were changed, add them to db
		//adds to queue first
		int count =0;
	    for (Entry<Integer, Row> entry : table.getRows().entrySet()) 
	    {
		    Row value = (Row)entry.getValue();
		    if(value.isChanged())
		    {
		    	map= new LinkedHashMap<String, String>();
		    	if(value.quantityDec())
		    	{
		    		addToMap(value.getProductId()
		    				,value.getQuantity()+"",value.getProduct_quantity()+"","1");
		    		System.out.println("dec");
		    	}
		    	else
		    		addToMap(value.getProductId()
		    				,(value.getQuantity()-value.getOriginal_s_quantity())+"",value.getProduct_quantity()+"","0");
		    	if(count==0)
		    	{
			    	new Database (map,"sales/newSale",this);
		    	}
		    	else
		    		q.add(map);
		    	value.setOriginal_s_quantity(value.getQuantity());
		    	value.setChanged(false);
		    	count++;
		    }
		}
	    if(count==0 && salesClicked)
	    	startProfileActivity();
	}
	
	
    class ProfileListener implements OnClickListener
    {

    	  public void onClick(View v)
    	    {
    		  salesClicked=true;
    		  addChangedRowsToQueue();
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
    
    
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.
        	addChangedRowsToQueue();
        	startNamesActivity();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
//    public void onBackPressed()
//{
//    	addChangedRowsToQueue();
//    	//startNamesActivity();
//    	
//    	backClicked=true;
//    //	return;
////    	   Intent setIntent = new Intent(Intent.ACTION_MAIN);
////    	   setIntent.addCategory(Intent.CATEGORY_HOME);
////    	   setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////    	   startActivity(setIntent);
//    	}
    
    public void startNamesActivity()
    {
    	
    	Intent namesIntent = new Intent(AddBeerActivity.this,NamesActivity.class);
    	namesIntent.putExtra("event_id",getIntent().getExtras().getString("event_id"));
    	startActivity(namesIntent);
    }

}
