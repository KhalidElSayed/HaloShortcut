package com.zst.xhfw.addon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ShortcutReceiver extends Activity{
	@Override
	protected void onCreate(Bundle paramBundle){
	    super.onCreate(paramBundle);
	    Intent main = new Intent(this, MainActivity.class);
	    main.putExtra("value", 0);//Place value so MainActivity will return result.
	    startActivityForResult(main, 100);
	  }
	
	@Override
	protected void onActivityResult(int args1, int args2, Intent paramIntent){
    if (paramIntent != null){
      setResult(RESULT_OK, paramIntent); // send intent to system
      Toast.makeText(this, "Added system shortcut" + paramIntent.getPackage(), Toast.LENGTH_SHORT).show();
    }else{
    	Toast.makeText(this, "No shortcut was selected", Toast.LENGTH_SHORT).show();
    }
      finish();
    }
}

  
