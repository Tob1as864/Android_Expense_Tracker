package at.ac.univie.expensetracker.moneymonkey.utils.listcollection;

import java.util.ArrayList;
import java.util.List;

// Not used yet
public class NotificationCollectionHelper implements CollectionInterface {

    private List<String> notificationList = new ArrayList<>();

    public NotificationCollectionHelper() {
        notificationList.add("Please enter valid input data");
        notificationList.add("This element already exist \nPlease choose another one");
        notificationList.add("Input successful");
    }

    @Override
    public String getElement(int position) {
        return notificationList.get(position);
    }

    @Override
    public void setElementAt(int position, String element) {
        notificationList.add(position, element);
    }

    @Override
    public void addElement(String element) {
        notificationList.add(element);
    }

    @Override
    public void deleteAt(int position) {
        notificationList.remove(position);
    }

    @Override
    public void deleteElement(String element) {

    }
}
