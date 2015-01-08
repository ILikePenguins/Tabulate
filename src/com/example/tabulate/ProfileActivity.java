package com.example.tabulate;
import java.util.LinkedHashMap;

import database.Database;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends FragmentActivity
{
	  LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
       
	  public void onCreate(Bundle savedInstanceState) 
	    {
		 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_profile_2);
	        Button  btnAddBottle = (Button)findViewById(R.id.addBottleBtn);
	        Button  btnAddPint = (Button)findViewById(R.id.addPintBtn);
	        Button  btnPaid = (Button)findViewById(R.id.paidBtn);
	        
	        btnAddBottle.setOnClickListener(new AddBottleListener());
	        btnAddPint.setOnClickListener(new addPintListener());
	        btnPaid.setOnClickListener(new PaidListener());
	        
	        TextView name = (TextView)findViewById(R.id.profileNameBtn);////
	        name.setText(getIntent().getExtras().getString("name"));
	        map.put("name", getIntent().getExtras().getString("name"));
	    }
	  
	  
	    class AddBottleListener implements OnClickListener
	    {

	    	  public void onClick(View v)
	    	    {
	    	        //update database
  	            new Database(map,"customers/increment_bottle.php").execute();
	    		  //update form
	    	    }
	    }
	    
	    class addPintListener implements OnClickListener
	    {

	    	  public void onClick(View v)
	    	    {
	    		  new Database(map,"customers/increment_pint.php").execute();
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
	    	 TextView bottles = (TextView)findViewById(R.id.tvNumberBottles);
	    	 TextView pints = (TextView)findViewById(R.id.tvNumberPints);
	    	 TextView total = (TextView)findViewById(R.id.tvTotal);
	    	 //get # pints and bottles from database
	    	 
	    	
	    }
}
