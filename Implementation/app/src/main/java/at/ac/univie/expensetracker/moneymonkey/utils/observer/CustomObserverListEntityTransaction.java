package at.ac.univie.expensetracker.moneymonkey.utils.observer;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransaction;

public class CustomObserverListEntityTransaction implements CustomObserver {
    private List<EntityTransaction> entityTransactionList;

    @Override
    public void update(Object object) {
        this.entityTransactionList = (List<EntityTransaction>) object;
    }
}
