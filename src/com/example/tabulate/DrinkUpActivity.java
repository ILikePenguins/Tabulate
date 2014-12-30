package com.example.tabulate;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class DrinkUpActivity extends Activity
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
