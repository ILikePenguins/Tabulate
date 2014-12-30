package com.example.tabulate;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends FragmentActivity
{
	  public void onCreate(Bundle savedInstanceState) 
	    {
		 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_profile);
	        Button  btnDrinkUp = (Button)findViewById(R.id.DrinkUpBtn);
	        Button  btnTab = (Button)findViewById(R.id.TabBtn);
	        
	        btnDrinkUp.setOnClickListener(new DrinkUpListener());
	        btnTab.setOnClickListener(new TabListener());
	        TextView name = (TextView)findViewById(R.id.nameTv);////
	        name.setText(getIntent().getExtras().getString("name"));
	    }
	  
	  
	    class DrinkUpListener implements OnClickListener
	    {

	    	  public void onClick(View v)
	    	    {
	    	        
	    	    }
	    }
	    
	    class TabListener implements OnClickListener
	    {

	    	  public void onClick(View v)
	    	    {
	    	       
	    	    }
	    }
}
