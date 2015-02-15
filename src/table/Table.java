package table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Table 
{
	private JSONArray jArray;
	private TableLayout tl;
	private Activity activity;
	private int flag;
	private String[] colNames;
	private String[] rowNames;
	
	public Table(String output, TableLayout tl,Activity activity, String[] colNames, String[] rowNames)
	{
		try {
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
                System.out.println("run");
            }
            else 
            {
         	   // populate rows
                JSONObject json_data;
				try 
				{
					json_data = jArray.getJSONObject(i);
				
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
						else
							getRow(tr,json_data,s);
	            	}
                tl.addView(tr);
                
                final View vline1 = new View(activity);
                vline1.setLayoutParams(new                
                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                vline1.setBackgroundColor(Color.DKGRAY);
                tl.addView(vline1);  // add line below each row   
                
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }
        }
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

	public void getRow(TableRow tr, JSONObject json_data, String colName)
	{
		//get rows from the json array to populate table
		TextView tv=new TextView(activity);
	    String str;
		try {
			str = String.valueOf(json_data.getString(colName));

	    tv.setText(str);
	    //check if keg or bottle
	   // checkType(tv,json_data);
	    tv.setTextSize(15);
	    tr.addView(tv);
	    System.out.println(str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
}
