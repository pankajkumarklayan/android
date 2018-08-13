package com.testFirestore;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.testFirestore.fragment.LoginFragment;
import com.testFirestore.fragment.SignupFragment;

public class MainActivity extends AppCompatActivity
{


    private boolean mShowingBack= false;
    public static   MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainActivity= this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (savedInstanceState == null)
        {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new LoginFragment())
                    .commit();

        }

    }
    public void flipCard()
    {
        if (mShowingBack)
        {
            getSupportFragmentManager().popBackStack();

            mShowingBack = false;
        }
        else
        {
            // Flip to the back.
            mShowingBack = true;
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in,
                            R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in,
                            R.animator.card_flip_left_out)

                    .replace(R.id.container, new SignupFragment())
                    .addToBackStack("")
                    .commit();

        }

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
      getSupportFragmentManager().popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
