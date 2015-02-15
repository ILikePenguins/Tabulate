package com.example.tabulate;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import parsing.Parse;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import database.AsyncResponse;
import database.Database;

public class EventActivity extends Activity implements AsyncResponse
{
	private EditText etName;
	private ArrayAdapter<String>   adapter;
	private ArrayList<String> list = new ArrayList<String>();
	private LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
	private int id;
	private DatePicker datePicker;
	private Parse parse;
	  protected void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.event_names);

	        Button  btnAdd = (Button)findViewById(R.id.addEvent);
	       
	        btnAdd.setOnClickListener(new AddEventListener());
	        etName = (EditText)findViewById(R.id.etEvent);
	        datePicker = (DatePicker) findViewById(R.id.date);
	        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
	        		{
	        		public View getView(int position, View convertView, ViewGroup parent) {
	                View view = super.getView(position, convertView, parent);
	                TextView text = (TextView) view.findViewById(android.R.id.text1);
	                text.setTextColor(Color.WHITE);
	                return view;
	            }
	        };
	        
	        parse=new Parse();
	        
	        // set the lv variable to your list in the xml
	        ListView  lv=(ListView)findViewById(R.id.event_list);  
	        lv.setAdapter(adapter);
	        lv.setOnItemClickListener(new OnItemClickListenerListViewItem());
	        
	        map.put("name", "");
	        map.put("date", "");
	        new Database (map,"events/retrieve",this).execute();

	       
	    }
	  
		class AddEventListener implements OnClickListener
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
	    	            String date=datePicker.getYear()+"-" + datePicker.getMonth()+"-"+datePicker.getDayOfMonth();
	    	            //mysql format YY-MM-DD or YYYY-MM-DD
	    	            //add to database
	    	            LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
	    	            map.put("name", input);
	    	            map.put("date", date);
	    	            new Database (map,"events/create").execute();
	    	        }
	    	    }
	    }
		
	    public class OnItemClickListenerListViewItem implements OnItemClickListener 
	    {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		    {
		    	//get name of person clicked on
		    	String[] tokens = parent.getItemAtPosition(position).toString().split(" ");
		    	
		        System.out.println("name "+tokens[0]+" date "+ tokens[1]);
		        //start map view
		        Intent namesIntent = new Intent(EventActivity.this,NamesActivity.class);
		        //loads map if it is not loaded already
		        namesIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		      //pass values to the map activity
		        namesIntent.putExtra("name",tokens[0]);
		        namesIntent.putExtra("date",tokens[1]);
		        
		        //start names activity
		      	startActivity(namesIntent);
		    }

		}

		public void processFinish(String output)
		{
			addEventsToAdapter(output);
		}

		public void addEventsToAdapter(String response)
		{
			System.out.println(response);
			parse.setString(response);
			String tokens[]=parse.NameDate();
			for(String s: tokens)
			{
				// add each person to the adapter list
				adapter.add(s);
			}
		}


}
