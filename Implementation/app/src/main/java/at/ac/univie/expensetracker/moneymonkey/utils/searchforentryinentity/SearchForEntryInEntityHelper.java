package at.ac.univie.expensetracker.moneymonkey.utils.searchforentryinentity;

import at.ac.univie.expensetracker.moneymonkey.customexception.NullExceptionSearchForEntryInEntity;

//Class takes a specific loader and is searching for a specific value in this list from objects
public class SearchForEntryInEntityHelper<T>{

    public LoadEntryFromEntity<T> loadEntryFromEntity;
    public T entityObject;

    public SearchForEntryInEntityHelper(LoadEntryFromEntity<T> loadEntryFromEntity) {
        this.loadEntryFromEntity = loadEntryFromEntity;
    }

    public boolean compareTextAndEntry(String searchForText) throws NullExceptionSearchForEntryInEntity {

        if (loadEntryFromEntity == null) {
            throw new NullExceptionSearchForEntryInEntity("Loader is null. Check your loader.");
        }

        for (int i = 0; i < loadEntryFromEntity.getSize(); i++ ) {
            String entityValue = loadEntryFromEntity.getEntryFromList(i);
            if (searchForText.equals(entityValue)) {
                entityObject = loadEntryFromEntity.getCurrentEntity(i);
                return true;
            }
        }
        return false;
    }
}
