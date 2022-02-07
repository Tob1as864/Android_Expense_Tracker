package at.ac.univie.expensetracker.moneymonkey.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.R;
import at.ac.univie.expensetracker.moneymonkey.utils.checkforinvalidinput.CheckInputStringOutputString;
import at.ac.univie.expensetracker.moneymonkey.utils.checktextforinvalidinput.HandleStringInputHelper;
import at.ac.univie.expensetracker.moneymonkey.viewmodel.AlterAccountViewModel;

public class AlterAccountActivity extends AppCompatActivity {

    private static final String LOG_TAG = AlterAccountActivity.class.getSimpleName();
    private AlterAccountViewModel mAlterAccountViewModel;
    TextView textViewAccountName;
    EditText editTextNewAccountName;
    int mClickedAccountID;
    String mClickedAccountName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_account);

        mAlterAccountViewModel = new ViewModelProvider(this).get(AlterAccountViewModel.class);

        Intent intent = getIntent();
        mClickedAccountID = intent.getIntExtra("entity_account_id", 0);
        mClickedAccountName = intent.getStringExtra("entity_account_name");

        textViewAccountName = findViewById(R.id.activityalteraccount_textview_accountname);
        editTextNewAccountName = findViewById(R.id.activityalteraccount_edittext_newaccountname);
        textViewAccountName.setText(mClickedAccountName);
    }

    public void updateAccountName(View view) {
        String enteredNewAccountName = editTextNewAccountName.getText().toString();

        // Create checker and list
        CheckInputStringOutputString checker = new CheckInputStringOutputString();
        List<String> inputList = new ArrayList<String>();
        inputList.add(enteredNewAccountName);

        if(!checker.runCheck(inputList)) {
            Toast toast = Toast.makeText(this, "Please enter valid input data", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            mAlterAccountViewModel.vmUpdateAccountName(checker.getConvertedValues().get(0), mClickedAccountID);
            super.finish();
        }
    }

    public void deleteAccount(View view) {
        mAlterAccountViewModel.vmDeleteAccount(mClickedAccountID);
        super.finish();
    }
}