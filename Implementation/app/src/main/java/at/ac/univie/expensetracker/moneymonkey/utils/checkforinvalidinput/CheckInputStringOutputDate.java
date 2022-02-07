package at.ac.univie.expensetracker.moneymonkey.utils.checkforinvalidinput;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckInputStringOutputDate extends CheckForInvalidInput<String, Date>{

    private final String className = CheckInputStringOutputDate.class.getSimpleName();
    private List<String> valuesToCheck;
    private int temporaryInteger;
    private final int[] tempIntegerArray = new int[3];


    @Override
    protected void setInputForChecking(List<String> input) {
        try {
            if (input.size() > 3) {
                throw new Exception("You are only allowed to have three input values " + className);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        valuesToCheck = input;
    }

    @Override
    protected void checkInputForBeingEmpty() {
        for(String value : valuesToCheck) {
            if (TextUtils.isEmpty(value.trim())) {
                super.valueOK = false;
            }
        }
    }

    @Override
    protected void convertInputToGoalDatatype() {
        if(super.convertedValues == null) {
            super.convertedValues = new ArrayList<>();
        }
        for(int i = 0; i < 3;i++) {
            try {
                temporaryInteger = Integer.parseInt(valuesToCheck.get(i));
            } catch (Exception e) {
                e.printStackTrace();
                super.valueOK = false;
            }
            if(super.valueOK) {
                tempIntegerArray[i] = temporaryInteger;
            }
        }
        if(enteredDateValid()) {
            Date transactionDate = parseDate(tempIntegerArray[2]+"-"+tempIntegerArray[1]+"-"+tempIntegerArray[0]);
            if(transactionDate != null) {
                super.convertedValues.add(transactionDate);
            } else{
                super.valueOK = false;
            }
        } else {
            super.valueOK = false;
        }
    }

    @Override
    protected boolean getResultForCheck() {
        return super.valueOK;
    }

    private boolean enteredDateValid() {
        int dayInteger = tempIntegerArray[0];
        int monthInteger = tempIntegerArray[1];
        int yearInteger = tempIntegerArray[2];
        if(yearInteger >= 1970 && yearInteger < 2038) {
            switch(monthInteger) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if(dayInteger > 0 && dayInteger <= 31) {
                    return true;
                } break;
                case 2: if(dayInteger > 0 && dayInteger <= 29) {
                    return true;
                } break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if(dayInteger > 0 && dayInteger <= 30) {
                    return true;
                } break;
                default:
                    return false;
            }
        }
        return false;
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}

