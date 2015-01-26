package com.example.tabulate;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import parsing.Parse;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import database.AsyncResponse;
import database.Database;

public class InventoryActivity extends Activity implements AsyncResponse
{
	private ArrayAdapter<String>   adapter;
	private ArrayList<String> list = new ArrayList<String>();
	private boolean isBottle;
	private LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
	private Parse parse;
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_list);

      parse=new Parse();
      Button  btnaddBottle = (Button)findViewById(R.id.addBottle);
      btnaddBottle.setOnClickListener(new AddBottleListener());
      
      Button  btnAddKeg = (Button)findViewById(R.id.addPint);
      btnAddKeg.setOnClickListener(new AddPintListener());
        
        //etName = (EditText)findViewById(R.id.etAdd);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        // set the lv variable to your list in the xml
        ListView  lv=(ListView)findViewById(R.id.beer_list);  
        lv.setAdapter(adapter);
      
        addToMap("","","","","");
        new Database (map,"beer/retrieveBottlesAndPints",this).execute();
       // lv.setOnItemClickListener(new OnItemClickListenerListViewItem());
    }
public void addToMap(String name, String keg, String costp,String costb,String qb)
{
	  map.put("name", name);
      map.put("quantity_keg", keg);
      map.put("cost_pint", costp);
      map.put("cost_bottle", costb);
      map.put("quantity_bottle", qb);
      map.put("event_id", getIntent().getExtras().getString("event_id"));
}
    class AddBottleListener implements OnClickListener
    {

    	  public void onClick(View v)
    	    {
    		  isBottle=true;
    		  FormDialog fd = new FormDialog();
    		  fd.show(getFragmentManager(), "Add Beer");
    		  
    	    }
    }
    
    class AddPintListener implements OnClickListener
    {

    	  public void onClick(View v)
    	    {
    		  isBottle=false;
    		  FormDialog fd = new FormDialog();
    		  fd.show(getFragmentManager(), "Add Beer");
    		 
    	    }
    }
    
 class FormDialog extends DialogFragment
 {
	    public Dialog onCreateDialog(Bundle savedInstanceState)
	    {
	      
		return form();
	    }
 }
public AlertDialog form()
{
	//Preparing views
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    View layout = inflater.inflate(R.layout.add_beer, (ViewGroup) findViewById(R.id.name_list));
    //layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.
    final EditText nameBox = (EditText) layout.findViewById(R.id.etBeerName);
    final EditText beerCost= (EditText) layout.findViewById(R.id.etBeerCost);
    final EditText beerQuantity = (EditText) layout.findViewById(R.id.etBeerQuantity);
    nameBox.setText("");
    TextView quantityView = (TextView)layout.findViewById(R.id.tvBeerQuantity);
    if(isBottle)
    {
    	beerQuantity.setVisibility(View.VISIBLE);
    	quantityView.setVisibility(View.VISIBLE);
    }
    else
    {
    	
    	beerQuantity.setVisibility(View.GONE);
    	quantityView.setVisibility(View.GONE);
    }
    //Building dialog
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setView(layout);
    
    //save button
    builder.setPositiveButton("Save", new DialogInterface.OnClickListener() 
    {

    	public void onClick(DialogInterface dialog, int which)
        {//save info
        	String beer = "Name: "+nameBox.getText()+" Quantity: "+beerQuantity.getText()+" Cost_each: $"
        			+beerCost.getText();
        	adapter.add(beer);
            dialog.dismiss();
            
            //map.put("name", nameBox.getText().toString());
            if(isBottle)
            	addToMap(nameBox.getText().toString(),"",beerCost.getText().toString(),beerCost.getText().toString(),beerQuantity.getText().toString());
            else
            	addToMap(nameBox.getText().toString(),"1",beerCost.getText().toString(),beerCost.getText().toString(),"");
           // if(isBottle)
            //{
            	new Database(map,"beer/create").execute();
//            }
//            else
//            	new Database(map,"add_pint").execute();
        }
    });
    //cancel button
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
    {

    	public void onClick(DialogInterface dialog, int which) 
    	{
            dialog.dismiss();
        }
    });
    AlertDialog dialog = builder.create();
    return builder.create();
}
public void processFinish(String output) 
{
	addBeersToAdapter(output);
}

public void addBeersToAdapter(String response)
{
		parse.setString(response);
		String tokens[]=parse.beers().split(",");
		for(String s: tokens)
		{
			// add each person to the adapter list
			adapter.add(s);
			System.out.println(s);
		}
	}
		
}


