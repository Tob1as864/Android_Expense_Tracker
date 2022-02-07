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
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.R;
import at.ac.univie.expensetracker.moneymonkey.utils.checkforinvalidinput.CheckInputStringOutputString;
import at.ac.univie.expensetracker.moneymonkey.viewmodel.AlterCategoryViewModel;


public class AlterCategoryActivity extends AppCompatActivity{

    private static final String LOG_TAG = AlterCategoryActivity.class.getSimpleName();
    private AlterCategoryViewModel mAlterCategoryViewModel;
    TextView textViewCategoryName;
    EditText editTextNewCategoryName;
    int mClickedCategoryID;
    String mClickedCategoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_category);

        mAlterCategoryViewModel = new ViewModelProvider(this).get(AlterCategoryViewModel.class);

        Intent intent = getIntent();
        mClickedCategoryID = intent.getIntExtra("id_category", 0);
        mClickedCategoryName = intent.getStringExtra("name_category");


        textViewCategoryName = findViewById(R.id.activityaltercategory_textview_categoryname);
        editTextNewCategoryName = findViewById(R.id.activityaltercategory_edittext_newcategoryname);
        textViewCategoryName.setText(mClickedCategoryName);
    }

    public void updateCategoryName(View view) {
        String enteredNewCategoryName = editTextNewCategoryName.getText().toString();

        //Create checker and list
        CheckInputStringOutputString checker = new CheckInputStringOutputString();
        List<String> inputList = new ArrayList<String>();
        inputList.add(enteredNewCategoryName);

        if(!checker.runCheck(inputList)) {
            Toast toast = Toast.makeText(this, "Please enter valid input data", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            mAlterCategoryViewModel.vmUpdateCategoryName(checker.getConvertedValues().get(0),mClickedCategoryID);
            super.finish();
        }
    }

    public void deleteCategory(View view) {
        mAlterCategoryViewModel.vmUpdateCategoryStatus(false,mClickedCategoryID);
        super.finish();
    }
}
