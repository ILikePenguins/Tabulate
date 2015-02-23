package form;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tabulate.R;

import database.AsyncResponse;
import database.Database;

 public class FormDialog extends DialogFragment implements AsyncResponse
 {
	 boolean isBottle;
	 private Activity activity;
	 private static LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
	 private String event_id;
	 public FormDialog(boolean isBottle, Activity activity, String event_id)
	 {
		 this.isBottle=isBottle;
		 this.activity= activity;
		 this.event_id=event_id;
	 }
	 
	    public Dialog onCreateDialog(Bundle savedInstanceState)
	    {
	      
	    	return form();
	    }
 
public AlertDialog form()
{
	//Preparing views
    LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View layout = inflater.inflate(R.layout.add_beer_inventory, (ViewGroup) activity.findViewById(R.id.name_list));
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
    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    builder.setView(layout);
    
    //save button
    builder.setPositiveButton("Save", new DialogInterface.OnClickListener() 
    {

    	public void onClick(DialogInterface dialog, int which)
        {//save info
            dialog.dismiss();
            
            if(isBottle)
            	addToMap(nameBox.getText().toString(),"",beerCost.getText().toString(),beerCost.getText().toString(),beerQuantity.getText().toString(),"");
            else
            	addToMap(nameBox.getText().toString(),"1",beerCost.getText().toString(),beerCost.getText().toString(),"","");
            
            new Database(map,"beer/create");
            
            
            System.out.println("Asdasdsadsad");
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

public  void addToMap(String name, String keg, String costp,String costb,String qb,String product_id)
{
	  map.put("name", name);
      map.put("quantity_keg", keg);
      map.put("cost_pint", costp);
      map.put("cost_bottle", costb);
      map.put("quantity_bottle", qb);
      map.put("product_id", product_id);
      map.put("event_id", event_id);
}

public void processFinish(String output) 
{
	System.out.println(output);
	
}

}
 
 