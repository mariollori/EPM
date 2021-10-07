package com.example.examenparcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
  Button btn;
    TextInputEditText loginem;
    TextInputEditText loginpass;
    FirebaseAuth mAuth;
    TextView registrartext;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.login);
        loginem = findViewById(R.id.usuario);
        loginpass = findViewById(R.id.contraseña);
        registrartext = findViewById(R.id.registrarbtn);
        mAuth = FirebaseAuth.getInstance();
        dialog = new SpotsDialog.Builder().setContext(this).setMessage("Espere unos momentos ...").setCancelable(false).build();
        btn.setOnClickListener(view -> {
           login();
        });
        registrartext.setOnClickListener(view -> {
            Intent i  = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(i);
        });
    }

    private void login() {
        String email = loginem.getText().toString();
        String password = loginpass.getText().toString();
        if(email.isEmpty() && password.isEmpty()){
            Toast.makeText(MainActivity.this, "Debe rellenar los campos...", Toast.LENGTH_SHORT).show();
        }else{
            dialog.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    dialog.dismiss();
                    if(task.isSuccessful()){
                        Intent intent2 = new Intent(MainActivity.this,Principal.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2);

                    }else{
                        Toast.makeText(MainActivity.this, "Email o password incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            Log.d("Campo","email:" + email);
            Log.d("Campo","Password:" + password);

        }

    }
}