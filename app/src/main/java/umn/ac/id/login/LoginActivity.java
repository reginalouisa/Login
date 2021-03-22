package umn.ac.id.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView Text;
    EditText TxUsername, TxPassword;
    Button BtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Text = findViewById(R.id.text);
        TxUsername = (EditText) findViewById(R.id.txUsername);
        TxPassword = (EditText) findViewById(R.id.txPassword);
        BtnLogin = (Button) findViewById(R.id.btnLogin);

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }
    public void Login(){
        String username = TxUsername.getText().toString();
        String password = TxPassword.getText().toString();
        if(username.equals("uasmobile") && password.equals("uasmobilegenap")){
            Intent intent = new Intent(this, DaftarlaguActivity.class);
            startActivity(intent);
            } else {
                Text.setText("Username atau password salah");
            }
        }
    }

