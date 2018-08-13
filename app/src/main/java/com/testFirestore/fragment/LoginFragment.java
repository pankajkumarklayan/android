package com.testFirestore.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.testFirestore.MainActivity;
import com.testFirestore.R;

/**
 * Created by eWeb_A1 on 5/29/2018.
 */

public class LoginFragment extends Fragment implements View.OnClickListener
{

    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    EditText etEmail, etPassword;
    Button bLogin;
    String TAG="LoginFragment";
    ProgressDialog mProgressDialog;



    FirebaseFirestore db ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView= inflater.inflate(R.layout.fragment_login, container, false);


        mFirebaseAuth= mFirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        XML(mView);
        return mView;
    }


    private void XML(View view)
    {


        etPassword=(EditText)view.findViewById(R.id.etPassword);
        etEmail=(EditText)view.findViewById(R.id.etEmail);
        bLogin=(Button)view.findViewById(R.id.bLogin);
        bLogin.setOnClickListener(this);




        tvSignUp=(TextView)view.findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              MainActivity.mainActivity.flipCard();

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {


            case R.id.bLogin:
                String email= etEmail.getText().toString().trim();
                String pass= etPassword.getText().toString().trim();

                if(email.equalsIgnoreCase("") || pass.equalsIgnoreCase(""))
                {

                    showToast("All fields are requireds");

                }
                else
                {

                    mProgressDialog= new ProgressDialog(getContext());
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage("Please wait....");
                    mProgressDialog.show();

                    mFirebaseAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {


                                    if(task.isSuccessful())
                                    {
                                        Log.e(TAG, "=======isSuccessful===="+task);
                                        FirebaseUser mUser= mFirebaseAuth.getCurrentUser();
                                        Log.e(TAG, "===========mUser========"+mUser);
                                        String mUserID= mUser.getUid();
                                        String email=mUser.getEmail();

                                    }
                                    else if(task.isCanceled())
                                    {
                                        Log.e(TAG, "=======isCanceled===="+task);

                                    }
                                    else
                                    {


                                        showToast("Email and Password not match");
                                    }

                                    mProgressDialog.dismiss();
                                }
                            });

                }



        }
    }

    private void showToast(String msg)
    {

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();


    }
}
