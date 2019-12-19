package com.example.capturaimatge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.capturaimatge.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button boto1;
    ImageView iv;
    Bitmap imageBitmap;
    String paths[];
    String currentPhotoPath;
    Uri photoURI;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leerXml();
        boto1=findViewById(R.id.button);
        iv=findViewById(R.id.imageView);

        try {
            if (paths.length != 0) {
                if (i < paths.length) {
                    iv.setImageURI(Uri.parse(paths[i]));
                    i++;
                    if (i == paths.length) {
                        i = 0;

                    }
                }

            }
        } catch (Exception ex) {
            System.out.println("no has guardado ninguna foto");
        }

    }
    static final int REQUEST_IMAGE_CAPTURE = 1;


    public void dispatchTakePictureIntent(View view) {
        leerXml();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.capturaimatge.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir=getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            iv.setImageURI(photoURI);
            apuntaarRuta(photoURI);

        }
    }
    public void leerXml() {
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("rutas.txt")));
            String texto = fin.readLine();
            fin.close();


            paths = texto.split("@");
        } catch (Exception ex) {

        }
    }
    public void apuntaarRuta(Uri ruta) {
        try {
            FileWriter fw = new FileWriter(new File(this.getFilesDir(), "rutas.txt"), true);
            //OutputStreamWriter fout=
            //new OutputStreamWriter(
            //openFileOutput("rutas.txt", Context.MODE_PRIVATE));
            fw.write(ruta.toString() + "@");
            //fout.write(currentPhotoPath+"@");
            fw.close();
            //fout.write(ruta.toString() + "@");
            //fout.close();
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }
    }


}