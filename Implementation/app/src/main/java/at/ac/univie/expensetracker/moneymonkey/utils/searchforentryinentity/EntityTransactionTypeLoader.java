package at.ac.univie.expensetracker.moneymonkey.utils.searchforentryinentity;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransactionType;

public class EntityTransactionTypeLoader implements LoadEntryFromEntity <EntityTransactionType>{

    List<EntityTransactionType> transactionTypesList;
    int sizeList;

    public EntityTransactionTypeLoader() {

    }

    //public EntityTransactionTypeLoader (List<EntityTransactionType> transactionTypesList) {
    //    this.transactionTypesList = transactionTypesList;
    //    this.sizeList = this.transactionTypesList.size();
    //}

    @Override
    public void setList(List<EntityTransactionType> o) {
        transactionTypesList = o;
        sizeList = transactionTypesList.size();
    }

    @Override
    public String getEntryFromList(int position) {
        EntityTransactionType current = transactionTypesList.get(position);
        return current.getMTransactionTypeName();
    }

    @Override
    public int getSize() {
        return sizeList;
    }

    @Override
    public EntityTransactionType getCurrentEntity(int position) {
        return transactionTypesList.get(position);
    }
}
