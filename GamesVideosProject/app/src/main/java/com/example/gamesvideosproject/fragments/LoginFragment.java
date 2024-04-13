package com.example.gamesvideosproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.gamesvideosproject.R;
import com.example.gamesvideosproject.activites.MainActivity;
import com.example.gamesvideosproject.adapters.ItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    ItemClickListener itemClickListener;
    private FirebaseAuth mAuth;

    public LoginFragment(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button loginButton = view.findViewById(R.id.btn_login_on_login);
        Button registerButton = view.findViewById(R.id.btn_register_on_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFunc();
            }

        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(MainActivity.FragmentType.register);
            }
        });

        return view;
    }

       /* public void loginFunc() {
            View view = getView();
            if (view != null) {
                String email = ((EditText) view.findViewById(R.id.login_email)).getText().toString().trim();
                String password = ((EditText) view.findViewById(R.id.login_password)).getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getContext(), "login ok!", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    Toast.makeText(getContext(), "login failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }*/

    public void loginFunc() {
        View view = getView();

        if (view != null)
        {
            String email = ((EditText) view.findViewById(R.id.login_email)).getText().toString().trim();
            String password = ((EditText) view.findViewById(R.id.login_password)).getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getContext(), "login ok!", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                itemClickListener.onClick(MainActivity.FragmentType.gamelist);

                            }
                            else
                            {
                                Toast.makeText(getContext(), "login failed!", Toast.LENGTH_SHORT).show();
                            }
                        }


                    });
        }
    }
}