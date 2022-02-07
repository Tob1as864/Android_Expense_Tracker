package at.ac.univie.expensetracker.moneymonkey.utils.testmaterial;

import android.app.Application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import at.ac.univie.expensetracker.moneymonkey.database.contentprovider.CategoryEntityContentProvider;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransactionType;

/*
class ExampleDataTest was build to generate data before we had a database and test all the different layouts
and recycler which have been implemented
 */

public class ExampleDataTest<i> {

    public static final LinkedList<String> mAccountNamesList = new LinkedList<>();
    public static final LinkedList<String> mAccountTypeList = new LinkedList<>();
    public static final LinkedList<Integer> mAccountBalanceList = new LinkedList<>();
    public static final LinkedList<Double> mAccountTransaction = new LinkedList<>();
    LinkedList<String> mTypeCollection = new LinkedList<>();
    private List<String> mTransactionTypeName = new ArrayList<>();
    List<EntityTransactionType> mCollectionOfInserts;
    public int count() {
        return mTransactionTypeName.size();
    }


    public ExampleDataTest() {

        mTypeCollection.addLast("Card");
        mTypeCollection.addLast("Cash");
        mTypeCollection.addLast("Bank Account");
        mTypeCollection.addLast("Stock");

        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            int n = random.nextInt(4);
            mAccountTypeList.addLast(mTypeCollection.get(n));
        }

        for (int i = 0; i < 20; i++) {
            mAccountNamesList.addLast("Account " + i);
        }

        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            int n = random.nextInt(99999);
            mAccountBalanceList.addLast(n);
        }

        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            double n = random.nextInt(99999);
            mAccountTransaction.addLast(n);
        }

        mTransactionTypeName.add("household");
        mTransactionTypeName.add("car");
        mTransactionTypeName.add("school");
        mTransactionTypeName.add("bar");
        mTransactionTypeName.add("food");
        mTransactionTypeName.add("clothes");
        mTransactionTypeName.add("holidays");
        mTransactionTypeName.add("asdafd");



    }

    public String getterCategory (int i){ return mTransactionTypeName.get(i); }
    public String getterName (int i) {
            return mAccountNamesList.get(i);
    }
    public void setterName (String i) {
        mAccountNamesList.addLast(i);
        Random random = new Random();
        int n = random.nextInt(20);
        mAccountTypeList.addLast(getterType(n));
        n = random.nextInt(20);
        mAccountBalanceList.addLast(getterBalance(n));
    }
    public String getterType (int i) {
        return mAccountTypeList.get(i);
    }
    public void setterType (String i) {
        mAccountTypeList.addLast(i);
    }
    public int getterBalance (int i) {
        return mAccountBalanceList.get(i);
    }
    public double getterAmount (int i) { return mAccountTransaction.get(i); }
    public void setterAmount () {
        Random random = new Random();
        double n = random.nextInt(99999);
        mAccountTransaction.addLast(n);
    }

    public void insertExample (Application application) {
        CategoryEntityContentProvider mCategoryEntityContentProvider;
        List<EntityTransactionType> mAllTypes;
        EntityTransactionType firstType = new EntityTransactionType(createTransactionType(), true);
        boolean emptyList = false;
        mCategoryEntityContentProvider = new CategoryEntityContentProvider(application);
        mCategoryEntityContentProvider.insert(firstType);
        //mAllTypes = mContentProvider.getAllTransactionTypes();
        //EntityTransactionTypes entity = mAllTypes.get(0);
        //String a = entity.getMTransactionTypeName();
        //boolean b = entity.getMActive();
    }

    public void setmTransactionTypeName() {
        Random r = new Random();
        mTransactionTypeName.add(this.mTransactionTypeName.get(r.nextInt(this.mTransactionTypeName.size())));
    }

    public String createTransactionType() {
        Random random = new Random();
        int counter = random.nextInt(9999);
        return "Weihnachtsmarkt" + counter;
    }
}
