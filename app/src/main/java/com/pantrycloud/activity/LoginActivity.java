package com.pantrycloud.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import app.pantry.R;

public class LoginActivity extends Activity{

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_layout);
        
//        //Initialize by casting the findViewById 
//        // to a button object
//        button_login = (Button)findViewById(R.id.button_login);
//        button_new_account = (Button)findViewById(R.id.button_new_account);
//        username = (EditText)findViewById(R.id.username);
//        password = (EditText)findViewById(R.id.password);

    }
}
