package com.deltainductions.rb.contactsrevolution;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class newcontactactivity extends ActionBarActivity
{
    public static String contactname,contactnumber,contactaddress,contactemail,imagepath;
    EditText name,number,address,email;
    public static String TAG="TAG";
    Button savebutton;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcontactactivity);
        imageView = (ImageView)findViewById(R.id.contactimage);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
        Bundle bundle = getIntent().getExtras();
        final Boolean check = bundle.getBoolean("Check");
        name = (EditText)findViewById(R.id.editname);
        number = (EditText)findViewById(R.id.editphone);
        address = (EditText)findViewById(R.id.editaddress);
        email = (EditText)findViewById(R.id.editemail);
        savebutton = (Button)findViewById(R.id.button);
        savebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                contactname = name.getText().toString();
                contactnumber = number.getText().toString();
                contactemail = email.getText().toString();
                contactaddress = address.getText().toString();
                Intent intent = new Intent(newcontactactivity.this,MainActivity.class);
                intent.putExtra("name",contactname);
                intent.putExtra("number",contactnumber);
                intent.putExtra("address",contactaddress);
                intent.putExtra("email",contactemail);
                intent.putExtra("image",imagepath);
                if(check)
                    setResult(2,intent);
                else
                    setResult(1,intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                imagepath = picturePath;
                Log.d(TAG,picturePath + "");
                imageView.setImageBitmap(thumbnail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_newcontactactivity, menu);
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
