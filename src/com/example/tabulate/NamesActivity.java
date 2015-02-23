package com.example.tabulate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parsing.Parse;
import parsing.ParseJson;
import adapter.NameAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import database.AsyncResponse;
import database.Database;


public class NamesActivity extends Activity implements AsyncResponse,OnClickListener
{
private EditText etName ;
private ArrayAdapter<String>   adapter;
private ArrayList<String> list = new ArrayList<String>();

private LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
private String eventID;
private Parse parse;
private ParseJson pj;
private NameAdapter fta;
private ListView listView;
private ArrayList<String> addedNames;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_list);

        parse=new Parse();
        //buttons
        Button  btnAdd = (Button)findViewById(R.id.addTaskBtn);
        Button  btnRemove = (Button)findViewById(R.id.removeBtn);
        Button  btnInven = (Button)findViewById(R.id.inventoryBtn);
        //button listeners
        btnAdd.setOnClickListener(new AddPersonListener());
        btnRemove.setOnClickListener(new RemovePersonListener());
        btnInven.setOnClickListener(new InventoryListener());
        
        
        etName = (EditText)findViewById(R.id.etAdd);
        //listview
        listView= (ListView) findViewById(R.id.name_list);
       
        listView.setOnItemClickListener(new OnItemClickListenerListViewItem());
        
        map.put("name", getIntent().getExtras().getString("name"));
        //name and date will be sent together, parse then send
        map.put("date", getIntent().getExtras().getString("date"));
        //get the event id
        new Database (map,"events/getEventID",this);
        addedNames=new ArrayList<String>();
       
    }
    public ArrayAdapter<String> getAdapter()
    {
		return adapter;
	}
	
    
   
    
	public void loadCustomers(String output)
	{
		getEventID(output);
		map.put("name","");
		map.put("event_id",eventID);
		//with the event id, customers can now be retrieved
	    new Database (map,"customers/retrieveCustomersByEvent",this);
	}
	public void loadCustomers()
	{
		map.put("name","");
		map.put("event_id",eventID);
		//with the event id, customers can now be retrieved
	    new Database (map,"customers/retrieveCustomersByEvent",this);
	}
    
	public void processFinish(String output)
	{
		System.out.println("output: "+ output);
		if(output.contains("getEventID"))
		{
			System.out.println("loading customers");
			loadCustomers(output);
		}
		else if (output.contains("retrieveCustomersByEvent"))
		{
			//addNamesToAdapter(output);
			pj= new ParseJson(output,adapter,new String[]{"id","name"});
			pj.AddToAdapter();
			fta = new NameAdapter(pj.getCustomers());
			listView.setAdapter(fta);
	       
		}
		else if(output.contains("successfully"))
		{
			
			addCustomerId(output);
		}
	}
	
	public void addCustomerId(String output)
	{
		Pattern p = Pattern.compile("-?\\d+");
		Matcher m = p.matcher(output);
		//get if form response
		while (m.find()) 
		{
			if(m.group().equals("1"))
				continue;
			else
			{
				System.out.println(m.group());
				pj.getCustomer_id().put(pj.getCount(), m.group());
			}
		  
		}
		addedNames.remove(0);
		
		
	}

	public void getEventID(String response)
	{
		parse.setString(response);
		eventID=parse.id();
		
	}
	
	public void addPerson(String input)
	{
		 // add string to the adapter and update view
		addedNames.add(input);
    	pj.getCustomers().put(input, false);
    	fta = new NameAdapter(pj.getCustomers());
        listView.setAdapter(fta);
        fta.notifyDataSetChanged();
    	
        etName.setText("");
        
        //add to database
        LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
        map.put("name", input);
        map.put("event_id",eventID);
        new Database(map,"customers/create",this);
	}
	
	class AddPersonListener implements OnClickListener
    {

    	  public void onClick(View v)
    	    {
    	        String input = etName.getText().toString();
    	        if(input.length() > 0)
    	        {
    	        	addPerson(input);
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
    		  // start the inventory activity
    		  Intent inventoryIntent = new Intent(NamesActivity.this,InventoryActivity.class);
    		  inventoryIntent.putExtra("event_id",eventID);
  	      	  startActivity(inventoryIntent);
    	    }
    }
    
    class Refreshlistener implements OnClickListener
    {

    	  public void onClick(View v)
    	    {
    		  loadCustomers();
    	    }
    }
    
    public class OnItemClickListenerListViewItem implements OnItemClickListener 
    {
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	    {
	    	TextView tv= (TextView) findViewById((int)parent.getItemIdAtPosition(position));
	    	System.out.println("name "+tv.getText());
	    	//tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	    	//get name of person clicked on

	        Intent addBeerIntent = new Intent(NamesActivity.this,AddBeerActivity.class);
	        addBeerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        //pass values to the addbeer activity
	        addBeerIntent.putExtra("name", tv.getText());
	        addBeerIntent.putExtra("event_id",eventID);
	        addBeerIntent.putExtra("customer_id",pj.getCustomer_id().get(position));
	        
	        //start profile activity
	      	startActivity(addBeerIntent);
	      	
	      	
	    }

	}

	public void onClick(View v)
	{
		 String input = etName.getText().toString();
	        if(input.length() > 0)
	        {
	        	addPerson(input);
	        }
	}

	

}
