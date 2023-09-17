package com.greemlock.derprojektforadvisors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(view -> {

            EditText edittextEmail = findViewById(R.id.editTextEmail);
            String email = edittextEmail.getText().toString();

            EditText editTextPassword = findViewById(R.id.editTextPassword);
            String password = editTextPassword.getText().toString();

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(MainActivity.this, task ->{
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                            Query findAdvisor = FirebaseDatabase.getInstance().getReference("advisors")
                                    .orderByChild("advisorUID").equalTo(firebaseUser.getUid());

                            findAdvisor.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    boolean isAdvisor = false;
                                    if (snapshot.getValue() != null){
                                        isAdvisor = true;
                                        Intent intent = new Intent(MainActivity.this,BaseActivity.class);
                                        startActivity(intent);
                                    }
                                    if (!isAdvisor){
                                        Toast.makeText(MainActivity.this, "You are not advisor!!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }else{
                            Toast.makeText(MainActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

        });


    }
}