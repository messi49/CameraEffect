package com.messi.cameraeffect;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import jp.co.cyberagent.android.gpuimage.*;

public class PreviewActivity extends Activity implements View.OnClickListener{

    public void onClick(View v) {
        Log.v("Log", "onClick!!!");
        //Intent intent = new Intent(getApplication(), CameraActivity.class);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        //imageView.setImageResource(R.drawable.ic_launcher);
        imageView.setImageURI(Uri.parse("file:///storage/emulated/0/image/handmade_flowers.jpg"));



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preview);

        Button select_button = (Button)findViewById(R.id.select_button);
        select_button.setOnClickListener(this);


        // GPUImage で写真加工
        GPUImage gpuImage = new GPUImage(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
