package com.example.tabulate;
import java.util.LinkedHashMap;

import parsing.ParseJson;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import database.AsyncResponse;
import database.Database;

public class ProfileActivity extends FragmentActivity implements AsyncResponse
{
	  private LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
	  private TextView bottles;
	  private TextView pints ;
	  private TextView total;
	  private String customer;
	  
	  public void onCreate(Bundle savedInstanceState) 
	    {
		 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_profile_);
	        
	        //init buttons
	        Button  btnPaid = (Button)findViewById(R.id.paidBtn);
	        //init views
	        bottles = (TextView)findViewById(R.id.tvNumberBottles);
	    	pints = (TextView)findViewById(R.id.tvNumberPints);
	    	total = (TextView)findViewById(R.id.tvTotal);
	        
	    	
	        //init listeners
	        btnPaid.setOnClickListener(new PaidListener());
	        
	        TextView name = (TextView)findViewById(R.id.profileNameBtn);////
	        customer=getIntent().getExtras().getString("name");
	        name.setText(customer);
	        addToMap("");
	        //get bottles from db
	        new Database (map,"sales/getBottlesPurchasedByCustomer",this);
	    }
	  
	  public void addToMap(String name)
	  {
	  	    map.put("name", name);
	        map.put("customer_id", getIntent().getExtras().getString("customer_id"));
	        map.put("event_id", getIntent().getExtras().getString("event_id"));
	  }	  
	    
	    class PaidListener implements OnClickListener
	    {

	    	  public void onClick(View v)
	    	    {
	    		  addToMap("");
	    		  map.put("paid", "1");
	    		  new Database(map,"sales/updatePaidStatus");
	    		  //update that customer paid.
	    		  System.out.println("id "+getIntent().getExtras().getString("customer_id"));
	    		  Toast.makeText(getApplicationContext(), customer+" successfully paid",
	    				   Toast.LENGTH_LONG).show();
	    	    }
	    }
	    
	    public void processFinish(String output)
		{
	    	ParseJson parse;
	    	if(output.contains("getBottles"))
	    	{
	    		System.out.println("bottles: "+output);
	    		
	    		parse = new ParseJson(output,bottles,new String[]{"SUM(s.quantity)"});
	    		parse.changeTextView();
	    		addToMap("");
	    		new Database (map,"sales/getPintsPurchasedByCustomer",this);

	    	}
	    	else if(output.contains("getPints"))
			{
	    		System.out.println("pints "+output);
				parse = new ParseJson(output,pints,new String[]{"SUM(s.quantity)"});
	    		parse.changeTextView();
	    		addToMap("");
	    		new Database (map,"sales/getTotalUnpaidForCustomer",this);
			}
	    	
	    	else if(output.contains("getTotal"))
	    	{
	    		System.out.println("total "+output);
	    		parse = new ParseJson(output,total,new String[]{"SUM(s.cost_total)"});
	    		parse.changeTextView();
	    	}
	    	
	    	
	    	System.out.println("output: "+output);
		}
		

		
}
