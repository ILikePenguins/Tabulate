package table;

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
		this.colNames=colNames;
		this.rowNames=rowNames;
		
		rows=new HashMap<Integer,Row>();
		position=0;
	}
	
	public void buildTable()
	{
		int flag=1;
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
			             	   str3="Bot";
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
						else if(s.equals("PQ"))
						{
							getRowInt(tr,json_data,s);
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
//		}
	}
	
	public void addColumn(TableRow tr, String colName)
	{
		  TextView tv=new TextView(activity);
//		  if(colName.equals("dec" )
//				  ||colName.equals("#Bought"))
//			  tv.setPadding(4, 0, 0, 0);
//		  else
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
		//b.setLayoutParams(new RelativeLayout.LayoutParams(10, 100));
		//b.setLayoutParams(new LinearLayout.LayoutParams(500,500));
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
	    tv.setTextSize(15);
	    if(colName.equals("id"))
	    	row.setProductId(str);
	    else
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
			if(json_data.isNull(colName))
				str=0;
			else 
				str = Integer.valueOf(json_data.getInt(colName));

	    tv.setText(str+"");
	   
	    if(colName.equals("PQ"))
	    {
	    	row.setProduct_quantity(str);
	    	row.setTv_p_quantity(tv);
	    }
	    else if(colName.equals("quantity"))
	    {
	    	row.setQuantity(str);
	    	 row.setTvQuantity(tv);
	    }
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
			if(rows.get(v.getId()).getQuantity()>0)
				{
				//the row was changed
				rows.get(v.getId()).setChanged(true);
				//decrement the quantity
				rows.get(v.getId()).setQuantity(rows.get(v.getId()).getQuantity()-1);
				//update textview with current quantity
				rows.get(v.getId()).getTvQuantity().setText(rows.get(v.getId()).getQuantity()+"");
				
				//increment the product quantity
				rows.get(v.getId()).setProduct_quantity(rows.get(v.getId()).getProduct_quantity()+1);
				//update textview with current quantity
				rows.get(v.getId()).getTv_p_quantity().setText(rows.get(v.getId()).getProduct_quantity()+"");
			}
		}
	}
	
	public  class IncListener implements OnClickListener
	{
		public void onClick(View v) 
		{
			//the row was changed
			rows.get(v.getId()).setChanged(true);
			if(rows.get(v.getId()).getProduct_quantity()>0)
			{
				//Increment the quantity purchased
				rows.get(v.getId()).setQuantity(rows.get(v.getId()).getQuantity()+1);
			
				//update textview with current quantity
				rows.get(v.getId()).getTvQuantity().setText(rows.get(v.getId()).getQuantity()+"");
				
				//decrement the product quantity
				rows.get(v.getId()).setProduct_quantity(rows.get(v.getId()).getProduct_quantity()-1);
				//update textview with current quantity
				rows.get(v.getId()).getTv_p_quantity().setText(rows.get(v.getId()).getProduct_quantity()+"");
			}
			
		}
	}
	
	public HashMap<Integer, Row> getRows() {
		return rows;
	}

}
