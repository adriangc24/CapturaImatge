package com.example.capturaimatge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.example.capturaimatge.R;

public class MainActivity extends AppCompatActivity {
    Button boto1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boto1=findViewById(R.id.button);

    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private View.OnClickListener butonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (i.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
            }
        }
    };

}