package com.scanshop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.PrintStream;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<RowData> 
{
	private Integer[] imgid;
	LayoutInflater mInflater;
	Context c;
	  
	public CustomAdapter(Context context, int resource, int textViewResourceId, List<RowData> objects, LayoutInflater mInflater, Integer[] imgid) 
	{               
		super(context, resource, textViewResourceId, objects);
		this.mInflater = mInflater;
		
	    this.imgid = imgid;
	    this.c=context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{   
	    ViewHolder holder = null;
	    TextView title = null;
	    TextView detail = null;
	    ImageView i11=null;
	    RowData rowData= getItem(position);
	    if(null == convertView)
	    {
	    	Log.v("null == convertView ", "null");
	    	convertView = mInflater.inflate(R.layout.list, null);
	        holder = new ViewHolder(convertView);
	        convertView.setTag(holder);
	    }

	    holder = (ViewHolder) convertView.getTag();
	    title = holder.gettitle();
	    title.setText(rowData.mTitle);
	    detail = holder.getdetail();
	    detail.setText(rowData.mDetail);                                                     
	    
	    Log.v("rri11 is ", "null");
	    
	    i11=holder.getImage();
	    if(i11==null)
	    {
	    	Log.v("i11 is ", "null");
	    }
	    if(imgid[rowData.mId]==null)
	    {
	    	Log.v("img Id is ", "null");
	    }
	    
	    i11.setImageResource(imgid[rowData.mId]);
	    convertView.setBackgroundColor(this.c.getResources().getColor(R.color.grey));
	    return convertView;
	}
	
	
	
	private class ViewHolder 
	{
		private View mRow;
	    private TextView title = null;
	    private TextView detail = null;
	    private ImageView i11=null; 

	    public ViewHolder(View row) 
	    {
	    	mRow = row;
	    }
	    
		 public TextView gettitle() 
		 {
			 if(null == title)
			 {
				 title = (TextView) mRow.findViewById(R.id.title);
			 }
		     return title;
		 }     
	
		 public TextView getdetail() 
		 {
			 if(null == detail)
			 {
				 detail = (TextView) mRow.findViewById(R.id.detail);
				 detail.setTextColor(c.getResources().getColor(R.color.skyblue));
			 }
		     return detail;
		 }
		 
		 public ImageView getImage() 
		 {
			 if(null == i11)
			 {
				 i11 = (ImageView) mRow.findViewById(R.id.img);
				 
			 }
		     return i11;
		 }
	}
}
