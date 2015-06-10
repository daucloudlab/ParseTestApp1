package kz.abcsoft.parse.android;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity {

    private ParseObject testObject ;
    private TextView tvResult ;

    private Button button;
    private Button uploadButton ;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseAnalytics.trackAppOpened(getIntent());
        tvResult = (TextView)findViewById(R.id.textView) ;
        button = (Button)findViewById(R.id.button) ;
        uploadButton = (Button)findViewById(R.id.upload_button) ;

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

    public void onSendDataClick(View view) {
        testObject = new ParseObject("TestObject") ;
        testObject.put("cat", "Мурзик") ;
        testObject.saveInBackground() ;
        Toast.makeText(this, "Ready", Toast.LENGTH_SHORT).show();
    }

    public void onGetDataClick(View view){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    Log.d("LOG", parseObject.getString("cat"));
                    tvResult.setText(parseObject.getString("cat"));
                } else {

                }
            }
        });
    }

    public void onGetImage(View view){
        progressDialog = ProgressDialog.show(MainActivity.this, "","Downloading Image...", true);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Images") ;
        query.getInBackground("vl6P1M75tJ", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                ParseFile fileObject = (ParseFile) parseObject.get("ImageFile");
                fileObject.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] bytes, ParseException e) {
                        if (e == null) {
                            Log.d("TEST", "Данные получены");
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ImageView image = (ImageView) findViewById(R.id.image);
                            image.setImageBitmap(bmp);
                            progressDialog.dismiss();
                        } else {
                            Log.d("TEST", "У нас проблемы!");
                        }
                    }
                });
            }
        });
    }

    public void onSetImage(View view){

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher) ;
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream) ;
        byte [] image = stream.toByteArray() ;

        ParseFile parseFile = new ParseFile("icon.png", image) ;
        parseFile.saveInBackground() ;

        ParseObject imgupload = new ParseObject("Images") ;
        imgupload.put("ImageFile", parseFile) ;
        imgupload.saveInBackground() ;

        Toast.makeText(MainActivity.this, "Image Uploaded",Toast.LENGTH_SHORT).show();
    }
}
