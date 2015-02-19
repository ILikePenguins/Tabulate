package com.example.tabulate;
import java.util.LinkedHashMap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import database.AsyncResponse;
import database.Database;
import form.ParseJson;

public class ProfileActivity extends FragmentActivity implements AsyncResponse
{
	  LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
	  TextView bottles;
	  TextView pints ;
	  TextView total;
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
	        //new Database (map,"sales/getPintsPurchasedByCustomer",this).execute();
	        
	        //get pints from db
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
	    		  new Database(map,"customers/paid").execute();
	    		  //TextView tv = (TextView) findViewById(R.id.name_list);
	    		 // tv.setText("This is strike-thru");
	    		// tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	    	       //cross name off
	    		  //change status?
	    	    }
	    }
	    
	    public void displayTab()
	    {
	    	 //get # pints and bottles from database
	    }
	    
	    public void processFinish(String output)
		{
	    	ParseJson parse;
	    	if(output.contains("getBottles"))
	    	{
	    		System.out.println("output: "+output);
	    		
	    		parse = new ParseJson(output,bottles,new String[]{"SUM(s.quantity)"});
	    		parse.changeTextView();
	    		addToMap("");
	    		new Database (map,"sales/getPintsPurchasedByCustomer",this);
//	    		try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
	    	}
	    	else if(output.contains("getPints"))
			{
	    		//System.out.println("**********fdsf");
				//System.out.println("output: "+output);
				parse = new ParseJson(output,pints,new String[]{"SUM(s.quantity)"});
	    		parse.changeTextView();
	    		addToMap("");
	    		new Database (map,"sales/getTotalForCustomer",this);
			}
	    	
	    	else if(output.contains("getTotal"))
	    	{
	    		parse = new ParseJson(output,total,new String[]{"SUM(s.cost_total)"});
	    		parse.changeTextView();
	    	}
	    	
	    	
	    	System.out.println("output: "+output);
		}
	    
		public void addToHashMap(String s)
		{
			
			
		}
		

		
}
