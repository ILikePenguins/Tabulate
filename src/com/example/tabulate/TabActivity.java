package com.example.tabulate;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.tabulate.ProfileActivity.DrinkUpListener;
import com.example.tabulate.ProfileActivity.TabListener;

public class TabActivity extends Activity
{	
	  public void onCreate(Bundle savedInstanceState) 
	    {
		 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_profile);
	        Button  btnDrinkUp = (Button)findViewById(R.id.DrinkUpBtn);
	        Button  btnTab = (Button)findViewById(R.id.TabBtn);
	        
	       // btnDrinkUp.setOnClickListener(new DrinkUpListener());
	      //  btnTab.setOnClickListener(new TabListener());
	      //  TextView name = (TextView)findViewById(R.id.nameTv);////
	      //  name.setText(getIntent().getExtras().getString("name"));
	    }
}
