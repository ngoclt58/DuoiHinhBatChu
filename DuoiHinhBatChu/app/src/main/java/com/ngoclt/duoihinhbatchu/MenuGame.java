package com.ngoclt.duoihinhbatchu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MenuGame extends AppCompatActivity implements View.OnClickListener{
    private Button mBtnReplay, mBtnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_game);
        initView();
    }

    private void initView() {
        mBtnExit = (Button) findViewById(R.id.btn_exit);
        mBtnReplay = (Button) findViewById(R.id.btn_replay);

        mBtnExit.setOnClickListener(this);
        mBtnReplay.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                finish();
                break;
            case R.id.btn_replay:
                finish();
                Intent intent = new Intent(MenuGame.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
