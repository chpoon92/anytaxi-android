package comp3111h.anytaxi.customer;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "comp3111h.anytaxi.customer.MESSAGE";
	
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/** Called when the user clicks the Send button */
	public void sendMessage(View view) {
	    // Do something in response to button
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		
		EditText editText1 = (EditText) findViewById(R.id.edit_message);
		EditText editText2 = (EditText) findViewById(R.id.edit_message2);
		String message1 = editText1.getText().toString();
		String message2 = editText2.getText().toString();
		
		// ServerCon will return whether connection to the sever is successful or not
		// If success http://byronyi-diet.appspot.com/ will add one line of comment, 'test from Android'
		// Using real smart phone to run, emulator will fail!
		String serverCon = excutePost("http://byronyi-diet.appspot.com/sign" , "content=testFromAndroid&guestbookName=default");
		String message = message1+" "+message2+" "+serverCon;
		
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	public static String excutePost(String targetURL, String urlParameters){
		try {
	        URL url = new URL(targetURL); 
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setInstanceFollowRedirects(false); 
	        connection.setRequestMethod("POST"); 
	        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
	        connection.setRequestProperty("charset", "utf-8");
	        connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
	        connection.setUseCaches (false);

	        connection.connect();

	        DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
	        wr.writeBytes(urlParameters);
	        wr.flush();
	        wr.close();
	        
	        connection.disconnect();
	        return "success"; 
	    } catch (Exception e) {
	      System.out.println("Exception");
	      e.printStackTrace();
	      return "failed";
	    }
	}
}
