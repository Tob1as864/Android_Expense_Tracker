package at.ac.univie.expensetracker.moneymonkey.utils.observer;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransaction;

public interface CustomObservable {
    public void register(CustomObserver observer);
    public void customNotify(List<EntityTransaction> l);
    public void remove(CustomObserver observer);
}
