package com.example.examenparcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.examenparcial.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText registeremail;
    TextInputEditText registeruser;
    TextInputEditText registerpass;
    TextInputEditText registerpassconf;
    Button registerbtn;
    FirebaseAuth mAuth;
    AlertDialog dialog;
    FirebaseFirestore mFire;
    CircleImageView circleback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registeruser= findViewById(R.id.registeruser);
        registerpass = findViewById(R.id.registerpass);
        registeremail = findViewById(R.id.registeremail);
        registerpassconf = findViewById(R.id.registerpassconf);
        registerbtn = findViewById(R.id.registerbutton);
        mAuth = FirebaseAuth.getInstance();
        mFire = FirebaseFirestore.getInstance();
        dialog = new SpotsDialog.Builder().setContext(this).setMessage("Espere unos momentos ...").setCancelable(false).build();
        circleback= findViewById(R.id.circleback);
        circleback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //este metodo permite ir a la ultima pagina anterior
                finish();
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerificarData();
            }
        });
    }

    private void VerificarData() {
        String user = registeruser.getText().toString();
        String email = registeremail.getText().toString();
        String pasword = registerpass.getText().toString();
        String passwordconf = registerpassconf.getText().toString();
        if(!user.isEmpty() && !email.isEmpty() && !passwordconf.isEmpty() && !pasword.isEmpty()){
            if(VerificarEmail(email)){
                if(pasword.equals(passwordconf)){
                    if(pasword.length() >= 6){
                        crearusuario(email,pasword,user);
                    }else{
                        Toast.makeText(this,"La contraseña debe tener mas de 6 caracteres",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this,"El email no es valido",Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this,"Falto completar algun campo",Toast.LENGTH_LONG).show();
        }

    }

    private void crearusuario(String email, String password, String username) {
        dialog.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //Obtendra el id del usuario recientemente creando/ ode la sesion actual
                    String id = mAuth.getCurrentUser().getUid();
                    User user = new User();
                    user.setId(id);
                    user.setEmail(email);
                    user.setUsername(username);

                    mFire.collection("Users").document(user.getId()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.dismiss();
                            if(task.isSuccessful()){
                                Intent intent2 = new Intent(RegisterActivity.this,MainActivity.class);
                                //Para limpiar el historial de pantallas en las que a navegado el usuario, asi
                                //al dar click en el boton de atras no regresara a la pantalla de registro.
                                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent2);
                            }else{
                                dialog.dismiss();
                                Toast.makeText(RegisterActivity.this,"No se pudo almacenar en la base de datos",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else{
                    dialog.dismiss();
                    Toast.makeText(RegisterActivity.this,"No se pudo registrar el usuario completamente", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private boolean VerificarEmail(String email) {
        String validacion = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(validacion,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}