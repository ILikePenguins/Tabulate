package com.example.tabulate;
import java.util.LinkedHashMap;

import database.AsyncResponse;
import database.Database;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends FragmentActivity implements AsyncResponse
{
	  LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
	  TextView bottles;
	  TextView pints ;
	  TextView total;
	  public void onCreate(Bundle savedInstanceState) 
	    {
		 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_profile_2);
	        //init buttons
	        Button  btnAddBottle = (Button)findViewById(R.id.addBottleBtn);
	        Button  btnAddPint = (Button)findViewById(R.id.addPintBtn);
	        Button  btnPaid = (Button)findViewById(R.id.paidBtn);
	        
	        //init views
	         bottles = (TextView)findViewById(R.id.tvNumberBottles);
	    	 pints = (TextView)findViewById(R.id.tvNumberPints);
	    	 total = (TextView)findViewById(R.id.tvTotal);
	        
	        //init listeners
	        btnAddBottle.setOnClickListener(new AddBottleListener());
	        btnAddPint.setOnClickListener(new addPintListener());
	        btnPaid.setOnClickListener(new PaidListener());
	        
	        TextView name = (TextView)findViewById(R.id.profileNameBtn);////
	        name.setText(getIntent().getExtras().getString("name"));
	        //add name to param list for json
	        map.put("name", getIntent().getExtras().getString("name"));
	        

	        new Database (map,"customers/get_pints.php",this).execute();
	    }
	  
	  
	    class AddBottleListener implements OnClickListener
	    {

	    	  public void onClick(View v)
	    	    {
	    	        //update database
  	            new Database(map,"customers/increment_bottle.php").execute();
  	            bottles.setText(incrementByOne (bottles.getText()));
	    		  //update form
	    	    }
	    }
	    
	    class addPintListener implements OnClickListener
	    {

	    	  public void onClick(View v)
	    	    {
	    		  new Database(map,"customers/increment_pint.php").execute();
	    		  pints.setText(incrementByOne (pints.getText()));
	    	    }
	    }
	    
	    class PaidListener implements OnClickListener
	    {

	    	  public void onClick(View v)
	    	    {
	    		  new Database(map,"customers/paid.php").execute();
	    		 // TextView tv = (TextView) findViewById(R.id.name_list);
	    		 // tv.setText("This is strike-thru");
	    		  //tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
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
			parsePints(output);
		}
	    
		public void parsePints(String response)
		{
			//parse the string of names returned from the database
			response=response.replaceAll("\\bpints\\b", "");
			response=response.replaceAll("\\bbottles\\b", "");
			response= response.replaceAll("[{:}]", "");
			response=response.replaceAll("\"", "");
			System.out.println(response);
			//response=response.substring(1,response.length()-1);
			
			String tokens[]=response.split(",");
			pints.setText(tokens[0]);
			bottles.setText(tokens[1]);	
		}
		
		public String incrementByOne(CharSequence c)
		{
			int x=Integer.parseInt(c.toString())+1;
			
			return x+"";
			
		}
}
