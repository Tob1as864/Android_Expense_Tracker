package at.ac.univie.expensetracker.moneymonkey.utils.observer;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityTransaction;

public class CustomObservableListEntityTransaction implements CustomObservable {
    private List<EntityTransaction> listEntityTransaction;
    private List<CustomObserver> channels = new ArrayList<>();

    @Override
    public void register(CustomObserver observer) {
        this.channels.add(observer);
    }

    @Override
    public void customNotify(List<EntityTransaction> l) {
        this.listEntityTransaction = l;
        for (CustomObserver customObserver : this.channels){
            customObserver.update(this.listEntityTransaction);
        }
    }

    @Override
    public void remove(CustomObserver observer) {
        this.channels.remove(observer);
    }
}
