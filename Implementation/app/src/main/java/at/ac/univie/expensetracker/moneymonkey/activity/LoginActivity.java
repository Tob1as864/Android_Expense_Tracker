package at.ac.univie.expensetracker.moneymonkey.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import at.ac.univie.expensetracker.moneymonkey.R;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private static final String userUsername = "User";
    private static final String userPassword = "1234";
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private TextView mLoginError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsernameEditText = findViewById(R.id.mainactivity_edittext_username);
        mPasswordEditText = findViewById(R.id.mainactivity_edittext_password);
        mLoginError = findViewById(R.id.mainactivity_textview_loginerror);
    }

    public void launchDashboard(View view) {
        Log.d(LOG_TAG, "Enter Button clicked");
        String enteredUsername = mUsernameEditText.getText().toString();
        String enteredPassword = mPasswordEditText.getText().toString();

        // Login check
        if ((enteredUsername.equals(userUsername)) && (enteredPassword.equals(userPassword))) {
            Intent intent = new Intent(this, TabOverviewActivity.class);
            startActivity(intent);
        }
        else {
            Log.d(LOG_TAG, "Wrong login data");
            mLoginError.setVisibility(View.VISIBLE);
        }
    }
}