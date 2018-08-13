package com.testFirestore.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.testFirestore.MainActivity;
import com.testFirestore.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eWeb_A1 on 5/29/2018.
 */

public class SignupFragment extends Fragment implements View.OnClickListener
{

    Button btLogin, bSignUp;
    FirebaseAuth mFirebaseAuth;
    EditText etFname, etLname, etPhoneNumber, etEmail, etPassword, etConfirmPassword, etCountry, etstate,
            etCity, etCompanyname;
    boolean mCheck;
    private String email = "";
    private String pass = "";
    private String TAG = "SignupFragment";
    ProgressDialog mProgressDialog;
    FirebaseFirestore mFirestore;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View mView = inflater.inflate(R.layout.fragment_signup, container, false);
        mFirestore = FirebaseFirestore.getInstance();
        DocumentReference mDocumentReference= mFirestore.collection("sampleData").document("User");

        CollectionReference restaurants = mFirestore.collection("restaurants");
        Map<String, Object> city = new HashMap<>();
        city.put("name", "Los Angeles");
        city.put("state", "CA");
        city.put("country", "USA");
        restaurants.add(city);



       /* Map<String, Object> city = new HashMap<>();
        city.put("name", "Los Angeles");
        city.put("state", "CA");
        city.put("country", "USA");

        mDocumentReference
                .set(city)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });*/



        XML(mView);
        return mView;
    }
    private void onAddItemsClicked()
    {
        // Get a reference to the restaurants collection
        CollectionReference restaurants = mFirestore.collection("restaurants");
        Map<String, Object> city = new HashMap<>();
        city.put("name", "Los Angeles");
        city.put("state", "CA");
        city.put("country", "USA");
        restaurants.add(city);


    }
    private void XML(View view) {
        etFname = (EditText) view.findViewById(R.id.etFname);
        etLname = (EditText) view.findViewById(R.id.etLname);
        etPhoneNumber = (EditText) view.findViewById(R.id.etPhoneNumber);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) view.findViewById(R.id.etConfirmPassword);
        etCountry = (EditText) view.findViewById(R.id.etCountry);
        etstate = (EditText) view.findViewById(R.id.etstate);

        etCity = (EditText) view.findViewById(R.id.etCity);
        etCompanyname = (EditText) view.findViewById(R.id.etCompanyname);
        bSignUp = (Button) view.findViewById(R.id.bSignUp);
        bSignUp.setOnClickListener(this);


        btLogin = (Button) view.findViewById(R.id.bLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.mainActivity.flipCard();

            }
        });

    }

    private boolean validation() {
        String password = etPassword.getText().toString().trim();
        String conpassword = etConfirmPassword.getText().toString().trim();

        if (etFname.getText().toString().trim().length() == 0) {
            etFname.requestFocus();
            etFname.setError(getString(R.string.firstname));
            return false;
        } else if (etLname.getText().toString().trim().length() == 0) {
            etLname.requestFocus();
            etLname.setError(getString(R.string.lastname));
            return false;
        } else if (etPhoneNumber.getText().toString().trim().length() == 0) {
            etPhoneNumber.requestFocus();
            etPhoneNumber.setError(getString(R.string.phonenumber));
            return false;
        } else if (etEmail.getText().toString().trim().length() == 0) {
            etEmail.requestFocus();
            etEmail.setError(getString(R.string.enteremail));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()) {
            etEmail.requestFocus();
            etEmail.setError(getString(R.string.validemail));

            return false;
        } else if (etPassword.getText().toString().trim().length() == 0) {
            etPassword.requestFocus();
            etPassword.setError(getString(R.string.enterPassword));
            return false;
        } else if (etConfirmPassword.getText().toString().trim().length() == 0) {

            etConfirmPassword.requestFocus();
            etConfirmPassword.setError(getString(R.string.confirmPassword));
            return false;
        } else if (!password.equalsIgnoreCase(conpassword)) {
            etConfirmPassword.requestFocus();
            etConfirmPassword.setError(getString(R.string.passwordnotMatch));
            return false;
        } else if (etCity.getText().toString().trim().length() == 0) {

            etCity.requestFocus();
            etCity.setError(getString(R.string.enterCity));
            return false;
        } else if (etstate.getText().toString().trim().length() == 0) {
            etstate.requestFocus();
            etstate.setError(getString(R.string.enterState));
            return false;

        } else if (etCountry.getText().toString().trim().length() == 0) {
            etCountry.requestFocus();
            etCountry.setError(getString(R.string.enterCountry));
            return false;

        }
        else if (mCheck)
        {


           /* if(mAddressFile ==null ||  mPhotoFile == null)
            {

                Toast.makeText(getActivity(), R.string.BothPhotoRequired, Toast.LENGTH_SHORT).show();

                return  false;
            }*/


        }
        return true;
    }


    private void showToast(String msg) {

        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bSignUp:
                if (validation()) {

                    final String FName = etFname.getText().toString().trim();
                    final String Lname = etLname.getText().toString().trim();
                    final String mCountry = etCountry.getText().toString().trim();
                    final String mState = etstate.getText().toString().trim();
                    final String mCity = etCity.getText().toString().trim();
                    final String mPhone = etPhoneNumber.getText().toString().trim();


                    email = etEmail.getText().toString().trim();
                    pass = etPassword.getText().toString().trim();

                    mProgressDialog = new ProgressDialog(getActivity());
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage("Please Wait...");
                    mProgressDialog.show();


                    mFirebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isCanceled()) {


                            } else if (task.isComplete()) {


                                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                                Log.e(TAG, "==================" + mFirebaseUser);
                                String mUserID = mFirebaseUser.getUid();
                                String email = mFirebaseUser.getEmail();


                                Map<String, Object> user = new HashMap<>();
                                user.put("Fname", FName);
                                user.put("Lname", Lname);
                                user.put("Country", mCountry);
                                user.put("State", mState);
                                user.put("City", mCity);
                                user.put("Email", email);
                                user.put("Password", pass);
                                user.put("Phone", mPhone);
                                user.put("UserId", mUserID);

                                mfwrw

                                // Add a new document with a generated ID
                               /* mDocumentReference.set(user)
                                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {


                                                Log.e(TAG, "=========addOnSuccessListener========"+aVoid);
                                            }
                                        }).addOnFailureListener(getActivity(), new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {


                                        Log.e(TAG, "=========onFailure==========="+e.getMessage());
                                    }
                                });*/




                            } else {
                                Log.e(TAG, "==========Errorr======" + task.getException());
                            }


                            mProgressDialog.dismiss();

                        }
                    });

                }

        }
    }
}
