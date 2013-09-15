package com.zst.xhfw.addon;

import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	
	public void click(View v){  // android:onclick from XML
		EditText d = (EditText)findViewById(R.id.editText2); // gets value of shortcut name
		String name = d.getText().toString();
		
		EditText e = (EditText)findViewById(R.id.editText1); // ditto for package name
		String pkg = e.getText().toString();

		
		add(pkg,name);
	}
	public void add(String pkg  , String name ){
		Intent intent = new Intent(getPackageManager().getLaunchIntentForPackage(pkg)); // get launch intent from string
		
		intent.addFlags(0x00002000); // FLAG_FLOATING_WINDOW or FLAG_MULTI_WINDOW or FLAG_HALO_WINDOW from ParanoidAndroid Sources
        intent.setFlags(intent.getFlags() & ~Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        intent.setFlags(intent.getFlags() & ~Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(intent.getFlags() & ~Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS); 
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        // Finally add all the intent Flags
        
        
        try {
			addShortcut(intent , name , pkg);
		} catch (Exception e) {     // Add shortcut // Error occurs when package name is wrong or not available
			Toast.makeText(this, "Error Occured \n " + e.toString() , Toast.LENGTH_LONG).show();;;
		}
	}
	private void addShortcut(Intent shortcutIntent, String name , String pkg) throws Exception { // 90% code from StackOverflow
	    //Adding shortcut for MainActivity 
	    //on Home screen
	    shortcutIntent.setAction(Intent.ACTION_MAIN); // set the received intent as MAIN

	    Intent addIntent = new Intent(); // These is a broadcast intent to send to Android API of our target intent (shortcutIntent)
	    addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent); // Places target intent in Extra to let out broadcast intent know our target
	    addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name); // Ditto but for name of Shortcut
	    
	    Drawable iconDrawable =  this.getPackageManager().getApplicationIcon(pkg); // get App icon as a drawable.
	    addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, ((BitmapDrawable) iconDrawable).getBitmap()); //Convert  drawable TO bitmapdrawable TO bitmap & add in EXTRA
	    
	    addIntent .setAction("com.android.launcher.action.INSTALL_SHORTCUT"); // Set action as the INSTALL_SHORTCUT broadcast
	    getApplicationContext().sendBroadcast(addIntent); // INSTALL_SHORTCUT needs to be declared in AndroidManifest !!
		Toast.makeText(this, "Shortcut Added", Toast.LENGTH_LONG).show();;;

	}
}
