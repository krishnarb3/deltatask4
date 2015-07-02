package com.deltainductions.rb.contactsrevolution;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by RB on 01-07-2015.
 */
class contactsadapter extends ArrayAdapter<String>
{   public static String TAG="TAG";
    public static String contactname;
    customButtonListener customListner;
    editButtonListener editlistener;
    ArrayList<String> newarray = new ArrayList<>();
    public interface customButtonListener
    {
        public void onButtonClickListner(String value);
    }
    public interface editButtonListener
    {
        public void onEditButtonClickListener(String Value);
    }
    public void setCustomButtonListner(customButtonListener listener)
    {
        this.customListner = listener;
    }
    public void setEditButtonListener(editButtonListener listener)
    {
        this.editlistener = listener;
    }
    public contactsadapter(Context context,ArrayList<String> arrayList)
    {
        super(context,R.layout.contactsrow,arrayList);
        this.newarray = arrayList;
    }

    @Override
    public View getView(final int position,final View convertView,final ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.contactsrow,parent,false);
        TextView name = (TextView)view.findViewById(R.id.name);
        name.setText(newarray.get(position));
        Button button = (Button)view.findViewById(R.id.deletecontact);
        button.setTag(position);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int pos = (int)view.getTag();
                contactname = newarray.get(pos);
                customListner.onButtonClickListner(contactname);
            }
        });
        final Button editbutton = (Button)view.findViewById(R.id.editbutton);
        editbutton.setTag(position);
        editbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int pos = (int)view.getTag();
                contactname = newarray.get(pos);
                editlistener.onEditButtonClickListener(contactname);
            }
        });
        return view;
    }
}
