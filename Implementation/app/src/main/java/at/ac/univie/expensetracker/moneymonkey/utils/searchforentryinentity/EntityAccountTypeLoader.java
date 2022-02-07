package at.ac.univie.expensetracker.moneymonkey.utils.searchforentryinentity;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccountType;

public class EntityAccountTypeLoader implements LoadEntryFromEntity <EntityAccountType>{

    List<EntityAccountType> accountTypesList;
    int sizeList;

    @Override
    public void setList(List<EntityAccountType> t) {
        accountTypesList = t;
        sizeList = accountTypesList.size();
    }

    @Override
    public String getEntryFromList(int position) {
        EntityAccountType current = accountTypesList.get(position);
        return current.getMAccountTypeName();
    }

    @Override
    public int getSize() {
        return sizeList;
    }

    @Override
    public EntityAccountType getCurrentEntity(int position) {
        return accountTypesList.get(position);
    }
}
