package com.example.tabulate;

import java.util.ArrayList;

import com.example.tabulate.NamesActivity.AddPersonListener;
import com.example.tabulate.NamesActivity.OnItemClickListenerListViewItem;
import com.example.tabulate.NamesActivity.RemovePersonListener;

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

public class InventoryActivity extends Activity
{
	private ArrayAdapter<String>   adapter;
	private ArrayList<String> list = new ArrayList<String>();
	private EditText etName ;
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_list);

      Button  btnAdd = (Button)findViewById(R.id.addBeer);
      btnAdd.setOnClickListener(new AddBeerListener());
        
        //etName = (EditText)findViewById(R.id.etAdd);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        // set the lv variable to your list in the xml
        ListView  lv=(ListView)findViewById(R.id.beer_list);  
        lv.setAdapter(adapter);
       // lv.setOnItemClickListener(new OnItemClickListenerListViewItem());
    }

    class AddBeerListener implements OnClickListener
    {

    	  public void onClick(View v)
    	    {
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
    //Building dialog
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setView(layout);
    
    //save button
    builder.setPositiveButton("Save", new DialogInterface.OnClickListener() 
    {

    	public void onClick(DialogInterface dialog, int which)
        {//save info
        	String beer = "name: "+nameBox.getText()+" quantity: "+beerQuantity.getText()+" cost: $"
        			+beerCost.getText();
        	adapter.add(beer);
            dialog.dismiss();
            System.out.println(beer);
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
}
