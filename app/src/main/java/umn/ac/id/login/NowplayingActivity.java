package umn.ac.id.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class NowplayingActivity extends AppCompatActivity {

    TextView judul_lagu,seekbar1,seekbar2;
    SeekBar seekbar;
    Button BtnNext, BtnPrev, BtnPause;
    static MediaPlayer putarlagu;
    int position;
    String nama_lagu;
    ArrayList<File> song;
    Thread updateseekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowplaying);
        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        BtnNext = findViewById(R.id.next);
        BtnPause = findViewById(R.id.pause);
        BtnPrev = findViewById(R.id.prev);
        judul_lagu = findViewById(R.id.lagu);
        seekbar = findViewById(R.id.seekBar);

        updateseekBar = new Thread() {
            @Override
            public void run() {
                int durasi = putarlagu.getDuration();
                int start= 0;
                while (start < durasi){
                    try{
                        sleep(500);
                        start = putarlagu.getCurrentPosition();
                        seekbar.setProgress(start);
                    }
                    catch (InterruptedException e){

                    }
                }
            }

        };
        if (putarlagu!=null){
            putarlagu.stop();
            putarlagu.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        song = (ArrayList)bundle.getParcelableArrayList("lagu");
        position = bundle.getInt("pos");
        nama_lagu = song.get(position).getName().toString();
        String namalagu = i.getStringExtra("namalagu");
        judul_lagu.setText(namalagu);
        judul_lagu.setSelected(true);
        Uri u = Uri.parse(song.get(position).toString());
        putarlagu =  MediaPlayer.create(getApplicationContext(),u);
        putarlagu.start();
        seekbar.setMax(putarlagu.getDuration());
        updateseekBar.start();

        seekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.MULTIPLY);
        seekbar.getThumb().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                putarlagu.seekTo(seekbar.getProgress());
            }
        });
        BtnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekbar.setMax(putarlagu.getDuration());
                if (putarlagu.isPlaying()){
                    BtnPause.setBackgroundResource(R.drawable.play);
                    putarlagu.pause();
                }
                else
                {
                    BtnPause.setBackgroundResource(R.drawable.pause);
                    putarlagu.start();
                }
            }
        });
        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putarlagu.stop();
                putarlagu.release();
                position = ((position+1)%song.size());
                Uri u = Uri.parse(song.get(position).toString());
                putarlagu = putarlagu.create(getApplicationContext() ,u);
                nama_lagu = song.get(position).getName().toString();
                judul_lagu.setText(nama_lagu);
                try{
                    putarlagu.start();
                }catch(Exception e){}
            }
        });
        BtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putarlagu.stop();
                putarlagu.release();
                position = ((position-1)<0)?(song.size()-1):(position-1);
                Uri u = Uri.parse(song.get(position).toString());
                putarlagu = putarlagu.create(getApplicationContext() ,u);
                nama_lagu = song.get(position).getName().toString();
                judul_lagu.setText(nama_lagu);
                putarlagu.start();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), DaftarlaguActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}