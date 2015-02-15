package com.example.tabulate;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import parsing.Parse;

import database.AsyncResponse;
import database.Database;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class NamesActivity extends Activity implements AsyncResponse
{
private EditText etName ;
private ArrayAdapter<String>   adapter;
private ArrayList<String> list = new ArrayList<String>();
private LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
private String eventID;
private Parse parse;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_list);

        parse=new Parse();
        
        Button  btnAdd = (Button)findViewById(R.id.addTaskBtn);
        Button  btnRemove = (Button)findViewById(R.id.removeBtn);
        Button  btnInven = (Button)findViewById(R.id.inventoryBtn);
       
        btnAdd.setOnClickListener(new AddPersonListener());
        btnRemove.setOnClickListener(new RemovePersonListener());
        btnInven.setOnClickListener(new InventoryListener());
        
        etName = (EditText)findViewById(R.id.etAdd);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        // set the lv variable to your list in the xml
        ListView  lv=(ListView)findViewById(R.id.name_list);  
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListenerListViewItem());
        
        
         //new Database ("customers/get_customers.php",this).execute();
        map.put("name", getIntent().getExtras().getString("name"));
        System.out.println(getIntent().getExtras().getString("name"));
        //name and date will be sent together, parse then send
        map.put("date", getIntent().getExtras().getString("date"));
        new Database (map,"events/getEventID",this).execute();     
       
    }
    public ArrayAdapter<String> getAdapter() {
		return adapter;
	}
	class AddPersonListener implements OnClickListener
    {

    	  public void onClick(View v)
    	    {
    	        String input = etName.getText().toString();
    	        if(input.length() > 0)
    	        {
    	            // add string to the adapter
    	        	System.out.println("*****"+input);
    	            adapter.add(input);
    	            etName.setText("");
    	            
    	            //add to database
    	            LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
    	            map.put("name", input);
    	            map.put("event_id",eventID);
    	            new Database(map,"customers/create").execute();
    	            //new CreateNewCustomer(input).execute();
    	        }
    	    }
    }
    
    class RemovePersonListener implements OnClickListener
    {

    	  public void onClick(View v)
    	    {
    	        String input = etName.getText().toString();
    	        if(input.length() > 0)
    	        {
    	            // add string to the adapter
    	        	System.out.println("*****"+input);
    	            adapter.remove(input);
    	            etName.setText("");
    	        }
    	    }
    }
    
    class InventoryListener implements OnClickListener
    {

    	  public void onClick(View v)
    	    {
    		  Intent inventoryIntent = new Intent(NamesActivity.this,InventoryActivity.class);
    		  inventoryIntent.putExtra("event_id",eventID);
  	      	  startActivity(inventoryIntent);
    	    }
    }
    
    
    public class OnItemClickListenerListViewItem implements OnItemClickListener 
    {
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	    {
	    	//get name of person clicked on
	        System.out.println("name "+parent.getItemAtPosition(position).toString());
	        //start map view
	        Intent profileIntent = new Intent(NamesActivity.this,ProfileActivity.class);
	        //loads map if it is not loaded already
	        profileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	      //pass values to the map activity
	        profileIntent.putExtra("name", parent.getItemAtPosition(position).toString());
	        profileIntent.putExtra("event_id",eventID);
	        //start profile activity
	      	startActivity(profileIntent);
	    }

	}
    

    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.names, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	public void processFinish(String output)
	{
		System.out.println("output: "+ output);
		if(output.contains("getEventID"))
		{
			loadCustomers(output);
		}
		else
			addNamesToAdapter(output);
	}
	
	public void loadCustomers(String output)
	{
		getEventID(output);
		map.put("name","");
		map.put("event_id",eventID);
	    new Database (map,"customers/retrieveCustomersByEvent",this).execute();
	}
	
	public void getEventID(String response)
	{
		parse.setString(response);
		eventID=parse.id();
		
	}
	
	public void addNamesToAdapter(String response)
	{
		parse.setString(response);
		String [] tokens =parse.Names();
		for(String s: tokens)
		{
			// add each person to the adapter list
			adapter.add(s);
		}
	}

}
