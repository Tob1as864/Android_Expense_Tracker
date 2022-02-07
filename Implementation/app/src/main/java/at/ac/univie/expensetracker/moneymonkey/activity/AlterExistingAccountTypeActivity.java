package at.ac.univie.expensetracker.moneymonkey.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import at.ac.univie.expensetracker.moneymonkey.R;
import at.ac.univie.expensetracker.moneymonkey.utils.checktextforinvalidinput.HandleStringInputHelper;
import at.ac.univie.expensetracker.moneymonkey.viewmodel.AlterExistingAccountTypeViewModel;

public class AlterExistingAccountTypeActivity extends AppCompatActivity {

    private static final String LOG_TAG = AlterExistingAccountTypeActivity.class.getSimpleName();
    private AlterExistingAccountTypeViewModel mAlterExistingAccountTypeViewModel;
    TextView textViewAccountTypeName;
    EditText editTextAccountTypeNameUpdate;
    int mClickedAccountTypeID;
    String mClickedAccountTypeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_existing_account_type);

        mAlterExistingAccountTypeViewModel = new ViewModelProvider(this).get(AlterExistingAccountTypeViewModel.class);

        Intent intent = getIntent();
        mClickedAccountTypeID = intent.getIntExtra("entity_account_type_id", 0);
        mClickedAccountTypeName = intent.getStringExtra("entity_account_type_name");

        textViewAccountTypeName = findViewById(R.id.activityalterexistingaccounttype_textview_displayname);
        editTextAccountTypeNameUpdate = findViewById(R.id.activityalterexistingaccounttype_edittext_newname);
        textViewAccountTypeName.setText(mClickedAccountTypeName);

    }

    public void updateAccountTypeName(View view) {
        Log.d(LOG_TAG, "Update AccountTypeName Button clicked");

        // Get input data
        String enteredNewAccountTypeName = editTextAccountTypeNameUpdate.getText().toString();

        // Check for data being valid input
        HandleStringInputHelper checker = new HandleStringInputHelper(enteredNewAccountTypeName);

        if(checker.mStringOK == false) {
            Toast toast = Toast.makeText(this, "Please enter valid input data", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            mAlterExistingAccountTypeViewModel.updateAccountTypeName(enteredNewAccountTypeName, mClickedAccountTypeID);
            super.finish();
        }
    }
}