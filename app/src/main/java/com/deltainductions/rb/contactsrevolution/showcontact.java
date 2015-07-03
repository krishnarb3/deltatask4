package com.deltainductions.rb.contactsrevolution;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class showcontact extends ActionBarActivity
{
    RelativeLayout layout;
    ImageView image;
    TextView name,number,address,email;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcontact);
        layout = (RelativeLayout)findViewById(R.id.layout);
        layout.setBackgroundColor(Color.BLACK);
        name = (TextView)findViewById(R.id.name);
        number = (TextView)findViewById(R.id.number);
        address = (TextView)findViewById(R.id.address);
        email = (TextView)findViewById(R.id.email);
        image = (ImageView)findViewById(R.id.image);
        Bundle bundle = getIntent().getExtras();
        name.setText(bundle.getString("name"));
        number.setText(bundle.getString("number"));
        address.setText(bundle.getString("address"));
        email.setText(bundle.getString("email"));
        Bitmap thumbnail = (BitmapFactory.decodeFile(bundle.getString("image")));
        image.setImageBitmap(thumbnail);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_showcontact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
