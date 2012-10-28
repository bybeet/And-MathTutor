package com.csci422.tutor;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MenuScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_screen, menu);
        return true;
    }
}
