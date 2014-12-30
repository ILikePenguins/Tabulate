package com.example.tabulate;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class NamesActivity extends Activity {
private EditText etName ;
private ArrayAdapter<String>   adapter;
private ArrayList<String> list = new ArrayList<String>();
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_list);

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
    	            new CreateNewCustomer(input).execute();
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
	        Intent profileIntent = new Intent(NamesActivity.this,ProfileActivity2.class);
	        //loads map if it is not loaded already
	        profileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	      //pass values to the map activity
	        profileIntent.putExtra("name", parent.getItemAtPosition(position).toString());
	        //start profile activity
	      	startActivity(profileIntent);
	    }

	}
    
    class CreateNewCustomer  extends AsyncTask<String, String, String> 
    {
    	String name;
    	 JSONParser jsonParser = new JSONParser();//**********************//
    	 String createCustomerUrl="http://192.168.1.9:8080/android_connect/create_customer.php";
    	public CreateNewCustomer(String n)
    	{
    		name=n;
    	}
		protected String doInBackground(String... arg0) 
		{
			 // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
			  // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(createCustomerUrl,
                    "POST", params);
            Log.d("Create Response", json.toString());
            System.out.println("sasdasd");
            // check for success tag
            try {
                int success = json.getInt("success");
 
                if (success == 1) {
                    // successfully created product
                   // Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                    //startActivity(i);
                		System.out.println("success!");
                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                	System.out.println("fail");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
			return null;
		}
    
    
    }
    

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.names, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
