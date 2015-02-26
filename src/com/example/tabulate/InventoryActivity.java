package com.example.tabulate;

import java.util.LinkedHashMap;


import table.Table;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import database.AsyncResponse;
import database.Database;
import form.FormDialog;

public class InventoryActivity extends Activity implements AsyncResponse
{
	private static LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
	private static  Table table;
	static String event_id;
	
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_list);

        //buttons
        Button  btnaddBottle = (Button)findViewById(R.id.addBottle);
        Button  btnAddKeg = (Button)findViewById(R.id.addPint);
        //button listeners
        btnaddBottle.setOnClickListener(new AddBottleListener());
        btnAddKeg.setOnClickListener(new AddPintListener());
        
        event_id= getIntent().getExtras().getString("event_id");
      
        addToMap("","","","","","");
        new Database (map,"beer/retrieveBottlesAndPints",this);
    }
public static void addToMap(String name, String keg, String costp,String costb,String qb,String product_id)
{
	  map.put("name", name);
      map.put("quantity_keg", keg);
      map.put("cost_pint", costp);
      map.put("cost_bottle", costb);
      map.put("quantity_bottle", qb);
      map.put("product_id", product_id);
      map.put("event_id", event_id);
}
    class AddBottleListener implements OnClickListener
    {

    	  public void onClick(View v)
    	    {
    		  FormDialog fd = new FormDialog(true, InventoryActivity.this,event_id);
    		  fd.show(getFragmentManager(), "Add Beer");
    	    }
    }
    
    class AddPintListener implements OnClickListener
    {

    	  public void onClick(View v)
    	    {
    		  FormDialog fd = new FormDialog(false,InventoryActivity.this,event_id);
    		  fd.show(getFragmentManager(), "Add Beer");
    	    }
    }

public void processFinish(String output) 
{
	System.out.println(output);
	if(output.contains("*beer/create"))
		{
		}
	else if(output.contains("retrieveBottlesAndPints"))
	{
		//get resposne from DB and add them to table 
		String[] colNames={"Name","Quantity","Cost_Each","Type"};
		String[] rowNames={"name","quantity","cost_each","type","id"};
		table = new Table(output,(TableLayout) findViewById(R.id.tableInventory),this,colNames,rowNames);
		table.buildTable();
		
	
	}
}

public void refresh()
{
	addToMap("","","","","","");
    new Database (map,"beer/retrieveBottlesAndPints",this);
}

public static class RowListener implements OnClickListener
{

	private Activity activity;
	String id;
	public RowListener( Activity activity)
	{
		this.activity=activity;
	}
	
	public void onClick(View v) 
	{
		//product id
		String name=table.getRows().get(v.getId()).getName();
		id=table.getRows().get(v.getId()).getProductId();
		//load dialog asking to delete row or not
		new AlertDialog.Builder(activity)
	    .setTitle("Delete entry")
	    .setMessage("Are you sure you want to delete "+name+"?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() 
	    {
	    	
	        public void onClick(DialogInterface dialog, int which) 
	        { 
	        	addToMap("","","","","",id);
	        	 new Database (map,"beer/deleteBeer");
	            // continue with delete
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() 
	    {
	        public void onClick(DialogInterface dialog, int which) 
	        { 
	            // do nothing
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
	
	

	
}
public static Table getTable() {
	return table;
}

		
}


