package umn.ac.id.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class DaftarlaguActivity extends AppCompatActivity {
    ListView daftar_song;
    String[] items;
    EditText search;
    int test = 0;

    public void permission(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        display();
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftarlagu);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DaftarlaguActivity.this, R.style.AlertDialogCustom);
        alertDialog.setTitle("Selamat Datang");
        alertDialog.setMessage("Regina Fransisca Louisa\n00000029656");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }
    public ArrayList<File> findSong(File file){

        ArrayList<File> arraylist = new ArrayList<>();
        File[] files = file.listFiles();

        for(File singleFile: files){
            if (singleFile.isDirectory() && !singleFile.isHidden()){
                arraylist.addAll(findSong(singleFile));
            }
            else{
                if (singleFile.getName().endsWith(".mp3")){
                    arraylist.add(singleFile);
                }
            }
        }
        return arraylist;
    }

    void display(){
        final ArrayList<File> song = findSong(Environment.getExternalStorageDirectory());
        items = new String[song.size()];

        for(int i = 0; i<song.size();i++){

            items[i] = song.get(i).getName().toString().replace(".mp3", "");

        }

        final ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        daftar_song.setAdapter(myAdapter);
        search = findViewById(R.id.search_bar);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                myAdapter.getFilter().filter(s);

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        daftar_song.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String judul = daftar_song.getItemAtPosition(position).toString();
                Intent pindah = new Intent(DaftarlaguActivity.this, NowplayingActivity.class);
                startActivity(pindah.putExtra("lagu", song).putExtra("namalagu", judul)
                        .putExtra("pos", position));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.profil) {
            startActivity(new Intent(this, ProfilActivity.class));
        } else if (item.getItemId() == R.id.logout) {
            startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }
}
