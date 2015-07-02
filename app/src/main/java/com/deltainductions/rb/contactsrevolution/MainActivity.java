package com.deltainductions.rb.contactsrevolution;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements contactsadapter.customButtonListener,contactsadapter.editButtonListener
{
    public static String TAG="TAG";
    ArrayList<String> contactnames=new ArrayList<String>(),contactnumbers = new ArrayList<String>(),contactemails = new ArrayList<String>(),contactaddresses = new ArrayList<String>();
    RelativeLayout layout;
    ImageView newcontact;
    databaseclass helper;
    String oldname,deletename;
    ArrayList<ArrayList<String>> dataarray = new ArrayList<ArrayList<String>>();
    int pos;
    Boolean oncreate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new databaseclass(this);
        dataarray = helper.getAllData();
        int noofcontacts = dataarray.size();
        EditText editText = (EditText)findViewById(R.id.searchbar);
        editText.setHint(noofcontacts+" Contacts");
        if(!oncreate)
        if(dataarray.size()>0)
        {
        contactnames = dataarray.get(0);
        contactnumbers = dataarray.get(1);
        contactaddresses = dataarray.get(2);
        contactemails = dataarray.get(3);
        ListView listView = (ListView)findViewById(R.id.contactslist);
        final contactsadapter adapter = new contactsadapter(getApplicationContext(),contactnames);
        adapter.setCustomButtonListner(MainActivity.this);
        adapter.setEditButtonListener(MainActivity.this);
        listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    String itemname = (String)adapterView.getItemAtPosition(i);
                    Toast.makeText(getApplicationContext(),itemname,Toast.LENGTH_SHORT).show();
                    int pos=contactnames.indexOf(itemname);
                    Intent intent = new Intent(MainActivity.this,showcontact.class);
                    intent.putExtra("name",contactnames.get(pos));
                    intent.putExtra("number",contactnumbers.get(pos));
                    intent.putExtra("address",contactaddresses.get(pos));
                    intent.putExtra("email",contactemails.get(pos));
                    startActivity(intent);
                    //intent.putExtra("image",contactimages.get(pos));

                }
            });
        }
        layout = (RelativeLayout)findViewById(R.id.layout);
        newcontact = (ImageView)findViewById(R.id.newcontactbutton);
        newcontact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this,newcontactactivity.class);
                i.putExtra("Check",false);
                startActivityForResult(i,1);
                oncreate = true;
            }
        });
    }
    public void onListitemclick()
    {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==1)
        {
            Bundle bundle = data.getExtras();
            long id = helper.insert(bundle.getString("name"),bundle.getString("number"),bundle.getString("address"),bundle.getString("email"));
            dataarray = helper.getAllData();
            if(dataarray.size()>0)
            {
                Log.d(TAG,dataarray.toString());
                contactnames = dataarray.get(0);
                contactnumbers = dataarray.get(1);
                contactaddresses = dataarray.get(2);
                contactemails = dataarray.get(3);
                ListView listView = (ListView)findViewById(R.id.contactslist);
                final contactsadapter adapter = new contactsadapter(getApplicationContext(),contactnames);
                adapter.setCustomButtonListner(MainActivity.this);
                adapter.setEditButtonListener(MainActivity.this);
                listView.setAdapter(adapter);
            }
            if(id<0)
                Toast.makeText(this,"Error in creating database",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"Written to database",Toast.LENGTH_SHORT).show();
        }
        if(resultCode==2)
        {
            Bundle bundle = data.getExtras();
            helper.update(oldname,bundle.getString("name"),bundle.getString("number"),bundle.getString("address"),bundle.getString("email"));

            dataarray = helper.getAllData();
            Log.d(TAG,dataarray.toString());
            if(dataarray.size()>0)
            {
                contactnames = dataarray.get(0);
                contactnumbers = dataarray.get(1);
                contactaddresses = dataarray.get(2);
                contactemails = dataarray.get(3);
                ListView listView = (ListView)findViewById(R.id.contactslist);
                final contactsadapter adapter = new contactsadapter(getApplicationContext(),contactnames);
                adapter.setCustomButtonListner(MainActivity.this);
                adapter.setEditButtonListener(MainActivity.this);
                listView.setAdapter(adapter);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onButtonClickListner(String value)
    {
        if(contactnames.contains(value))
        {
            int pos = contactnames.indexOf(value);
            Log.d(TAG,contactnames.get(pos));
            contactnames.remove(pos);
            contactnumbers.remove(pos);
            contactemails.remove(pos);
            contactaddresses.remove(pos);
            deletename = value;
        }
        int status = helper.delete(value);
        if(status>0)
        {
        dataarray = helper.getAllData();
        contactnames = dataarray.get(0);
        Log.d(TAG,contactnames.toString());
        contactnumbers = dataarray.get(1);
        contactaddresses = dataarray.get(2);
        contactemails = dataarray.get(3);
        ListView listView = (ListView)findViewById(R.id.contactslist);
        final contactsadapter adapter = new contactsadapter(getApplicationContext(),contactnames);
        adapter.setCustomButtonListner(MainActivity.this);
        adapter.setEditButtonListener(MainActivity.this);
        listView.setAdapter(adapter);
        }
    }

    @Override
    public void onEditButtonClickListener(String Value)
    {
        if(contactnames.contains(Value))
        {
            pos = contactnames.indexOf(Value);
            contactnames.remove(pos);
            contactnumbers.remove(pos);
            contactemails.remove(pos);
            contactaddresses.remove(pos);

        }
        Intent i = new Intent(MainActivity.this,newcontactactivity.class);
        i.putExtra("Check",true);
        oldname = Value;
        startActivityForResult(i,2);
    }
}
