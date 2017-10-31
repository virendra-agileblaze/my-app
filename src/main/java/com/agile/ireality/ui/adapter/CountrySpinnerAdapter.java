package com.agile.ireality.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.agile.ireality.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarath on 10/2/16.
 */
public class CountrySpinnerAdapter extends BaseAdapter implements Filterable {

    List<Country> mCountries;
    private List<Country> mCountryFilterList;

    Context mContext;
    int mResource;
    private ValueFilter valueFilter;


    public CountrySpinnerAdapter(Context context,int resource,  List<Country> objects) throws CountrySpinnerAdapterException {
        mCountries = objects;
        mCountryFilterList = objects;
        mContext = context;
        mResource = resource;

    }

    @Override
    public int getCount() {
        return mCountries.size();
    }

    @Override
    public Country getItem(int position) {
        return mCountries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView ==null){
            convertView = LayoutInflater.from(mContext).inflate(mResource,
                    parent, false);
        }
        TextView txtCountryName = (TextView)convertView.findViewById(R.id.txtCountryName);
        ImageView imgCountryFlag = (ImageView)convertView.findViewById(R.id.imgCountryFlag);

        txtCountryName.setText(mCountries.get(position).getName());
        imgCountryFlag.setImageDrawable(mCountries.get(position).getFlag());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(valueFilter==null) {

            valueFilter=new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {

        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<Country> filterList=new ArrayList<>();
                for(int i=0;i<mCountryFilterList.size();i++){
                    if((mCountryFilterList.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        Country contacts = new Country();
                        contacts.setCode(mCountryFilterList.get(i).getCode());
                        contacts.setFlag(mCountryFilterList.get(i).getFlag());
                        contacts.setName(mCountryFilterList.get(i).getName());
                        filterList.add(contacts);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=mCountryFilterList.size();
                results.values=mCountryFilterList;
            }
            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mCountries=(ArrayList<Country>) results.values;
            notifyDataSetChanged();
        }
    }
    public List<Country> getCountries(){
        return mCountries;
    }
}
