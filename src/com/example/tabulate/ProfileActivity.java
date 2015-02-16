package com.example.tabulate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Queue;

import parsing.Parse;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import database.AsyncResponse;
import database.Database;

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
	        
	        addToMap("","","","","");
	        //get bottles from db
	        new Database (map,"sales/getBottlesPurchasedByCustomer",this).execute();
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
	    	
	    	System.out.println("output: "+output);
	    	
	    		
	    		
		}
	    
	    public void decrementQuantity()
	    {
	    	//get the current quantity, and decrement by 1
	    	
	    	
	    }
	    

	
		
		public void addToHashMap(String s)
		{
			
			
		}
		

		
}
