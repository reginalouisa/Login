package umn.ac.id.login;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
    Button Profil, Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Profil = (Button) findViewById(R.id.btnProfil);
        Login = (Button) findViewById(R.id.btnLogin);
        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Login();
            }
        });
        Profil = findViewById(R.id.btnProfil);
        Profil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Profile();
            }
        });
    }

    public void Login(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void Profile(){
        Intent intent = new Intent(this, ProfilActivity.class);
        startActivity(intent);
    }

    public void runtimePermission(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

}