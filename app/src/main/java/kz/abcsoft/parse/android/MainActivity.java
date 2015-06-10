package kz.abcsoft.parse.android;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class MainActivity extends AppCompatActivity {

    private ParseObject testObject ;
    private TextView tvResult ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseAnalytics.trackAppOpened(getIntent());
        tvResult = (TextView)findViewById(R.id.textView) ;

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
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject") ;

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if(e == null){
                    Log.d("LOG", parseObject.getString("cat")) ;
                    tvResult.setText(parseObject.getString("cat"));
                }else{

                }
            }
        });
    }
}
