package table;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.tabulate.R;
public class SalesTable 
{
	private JSONArray jArray;
	private TableLayout tl;
	private Activity activity;
	private int flag;
	private String[] colNames;
	private String[] rowNames;
	private HashMap<Integer,Row> rows;
	private int position;
	private Row row;
	public SalesTable(String output, TableLayout tl,Activity activity, String[] colNames, String[] rowNames)
	{
		try 
		{
			jArray = new JSONArray(output);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.tl=tl;
		this.activity=activity;
		tl.removeAllViewsInLayout();
		flag=1;
		this.colNames=colNames;
		this.rowNames=rowNames;
		
		rows=new HashMap<Integer,Row>();
		position=0;
	}
	
	public void buildTable()
	{
	    for(int i=-1;i<jArray.length();i++)
        {

            TableRow tr=new TableRow(activity);
            tr.setLayoutParams(new LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT));

            // this will be executed once
            if(flag==1)
            {
            	//set column titles for table
            	for(String s: colNames)
            	{
            		addColumn(tr,s);
            	}
            	//add the to view
                tl.addView(tr);

                final View vline = new View(activity);
                vline.setLayoutParams(new       
                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
                vline.setBackgroundColor(Color.BLUE);
                tl.addView(vline); // add line below heading
                flag=0;
            }
            else 
            {
         	   // populate rows
                JSONObject json_data;
				try 
				{
					json_data = jArray.getJSONObject(i);
					row=new Row();
					for(String s: rowNames)
	            	{
						
						if(s.equals("type"))
						{
			                //type 
			                TextView b3=new TextView(activity);
			                b3.setPadding(10, 0, 0, 0);
			                int type=json_data.getInt("type");
			                String str3="";
			                if(type==1)
			                {
			             	   str3= "Keg";
			             	 //  b3.setTextColor(Color.CYAN);
			                }
			                else
			                {
			             	   str3="Bottle";
			             	   //b3.setTextColor(Color.DKGRAY);
			                }
			                b3.setText(str3);
			                b3.setTextSize(15);
			                tr.addView(b3);
						}
						else if(s.equals("quantity"))
						{
							addButtonDec(tr);	
							getRowInt(tr,json_data,s);
							addButtonInc(tr);
						}
						else
							getRow(tr,json_data,s);
						
						
	            	}
					
					rows.put(position, row);
					position++;
                tl.addView(tr);
                
                final View vline1 = new View(activity);
                vline1.setLayoutParams(new                
                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                vline1.setBackgroundColor(Color.DKGRAY);
                tl.addView(vline1);  // add line below each row   
                
				} catch (JSONException e) 
				{
					e.printStackTrace();
				}
            }
        }
	   
//	    for (Entry<Integer, Row> entry : rows.entrySet()) 
//	    {
//		    Integer key = entry.getKey();
//		    Row value = (Row)entry.getValue();
//		    System.out.println("key: "+key+value.toString());
//		    // ...
//		}
	}
	
	public void addColumn(TableRow tr, String colName)
	{
		  TextView tv=new TextView(activity);
		  tv.setPadding(10, 0, 0, 0);
		  tv.setText(colName);
		  tv.setTextColor(Color.BLUE);
		  tv.setTextSize(15);
	      tr.addView(tv);
	}

	public void addButtonDec(TableRow tr)
	{
		ImageButton b= new ImageButton(activity);
		b.setImageResource(R.drawable.dec);
		b.setId(position);
//		if(position==0)
//		{
			b.setOnClickListener(new DecListener());
//			//b.setOnClickListener(new AddBeerActivity.DecListener());
//			System.out.println("set");
//		}
		//b.setText("d");
		tr.addView(b);
		
	}
	
	public void addButtonInc(TableRow tr)
	{
		ImageButton b= new ImageButton(activity);
		b.setImageResource(R.drawable.inc);
		b.setId(position);
		b.setOnClickListener(new IncListener());
		//b.setOnClickListener(l)
		//b.setText("i");
		tr.addView(b);
		
	}
	public void getRow(TableRow tr, JSONObject json_data, String colName)
	{
		//get rows from the json array to populate table
		TextView tv=new TextView(activity);
	    String str;
		try {
			str = String.valueOf(json_data.getString(colName));

	    tv.setText(str);
	    if(colName.equals("id"))
	    	row.setId(str);
	    
	    tv.setTextSize(15);
	    tr.addView(tv);
	    
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void getRowInt(TableRow tr, JSONObject json_data, String colName)
	{
		//get rows from the json array to populate table
		TextView tv=new TextView(activity);
	    int str;
		try {
			str = Integer.valueOf(json_data.getInt(colName));

	    tv.setText(str+"");
	    row.setTv(tv);
	    row.setQuantity(str);
	    tv.setTextSize(15);
	    tr.addView(tv);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public  class DecListener implements OnClickListener
	{
		public void onClick(View v) 
		{
			//the row was changed
			rows.get(v.getId()).setChanged(true);
			//decrement the quantity
			rows.get(v.getId()).setQuantity(rows.get(v.getId()).getQuantity()-1);
			//update textview with current quantity
			rows.get(v.getId()).getTv().setText(rows.get(v.getId()).getQuantity()+"");
		}
	}
	
	public  class IncListener implements OnClickListener
	{
		public void onClick(View v) 
		{
			//the row was changed
			rows.get(v.getId()).setChanged(true);
			//decrement the quantity
			rows.get(v.getId()).setQuantity(rows.get(v.getId()).getQuantity()+1);
			//update textview with current quantity
			rows.get(v.getId()).getTv().setText(rows.get(v.getId()).getQuantity()+"");
			
		}
	}
	
	public HashMap<Integer, Row> getRows() {
		return rows;
	}

}
