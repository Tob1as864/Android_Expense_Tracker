package at.ac.univie.expensetracker.moneymonkey.utils.listcollection;

public interface CollectionInterface {
    String getElement(int position);
    void setElementAt(int position, String element);
    void addElement(String element);
    void deleteAt(int position);
    void deleteElement(String element);
}
