package at.ac.univie.expensetracker.moneymonkey.utils.datapreparationgraphs;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransaction;

public class DataPreparationBarChart {
    private List<EntityTransaction> listBarChart;
    public float valuesIn;
    public float valuesOut;

    public void setListBarChart (List<EntityTransaction> allTransactions){
        listBarChart = allTransactions;
    }

    public void selectInOutTransactions (){
        double sumIn=0;
        double sumOut=0;
        for (EntityTransaction transaction : listBarChart){
            if (transaction.getMTransactionInOut().equals("in")) {
                sumIn = sumIn + transaction.getMAmount();
            }else{
                sumOut = sumOut + transaction.getMAmount();
            }
        }
        valuesIn = (float)sumIn;
        valuesOut = (float)sumOut;
    }
}
