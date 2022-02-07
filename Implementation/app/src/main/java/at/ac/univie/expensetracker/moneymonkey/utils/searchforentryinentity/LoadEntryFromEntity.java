package at.ac.univie.expensetracker.moneymonkey.utils.searchforentryinentity;

import java.util.List;

//Interface to load data from specific data typ lists
public interface LoadEntryFromEntity <T>{
    public void setList(List<T> t);
    public String getEntryFromList(int position);
    public int getSize();
    public T getCurrentEntity(int position);
}
