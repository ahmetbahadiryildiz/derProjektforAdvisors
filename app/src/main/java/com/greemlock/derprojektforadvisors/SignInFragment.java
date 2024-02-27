package com.greemlock.derprojektforadvisors;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.greemlock.derprojektforadvisors.Objects.Advisor;

import java.util.ArrayList;

public class SignInFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText editTextName = getActivity().findViewById(R.id.editTextName);
        EditText editTextSurname = getActivity().findViewById(R.id.editTextSurname);
        EditText editTextEmail = getActivity().findViewById(R.id.editTextEmail);
        EditText editTextPassword = getActivity().findViewById(R.id.editTextPassword);
        Button buttonSignIn = getActivity().findViewById(R.id.buttonSignIn);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        buttonSignIn.setOnClickListener(view1 -> {
            String advisorName = editTextName.getText().toString();
            String advisorSurname = editTextSurname.getText().toString();
            String advisorEmail = editTextEmail.getText().toString();
            String advisorPassword = editTextPassword.getText().toString();

            firebaseAuth.createUserWithEmailAndPassword(advisorEmail,advisorPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            assert firebaseUser != null;
                            firebaseUser.sendEmailVerification();

                            Advisor newAdvisor = new Advisor(firebaseUser.getUid(),advisorName,advisorSurname,advisorEmail,new ArrayList<>());

                        }
                    });
        });


    }
}