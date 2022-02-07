package at.ac.univie.expensetracker.moneymonkey.utils.iterator;

import java.util.List;

// Iterator for generic lists
public class IteratorEnvironmentHelper<T> implements Environment {

    List<T> iterationList;

    public IteratorEnvironmentHelper(List<T> list) {
        this.iterationList = list;
    }

    @Override
    public Iterator createIterator() {
        return new GenericIterator();
    }

    private class GenericIterator implements Iterator {

        int index = 0;

        @Override
        public boolean nextElement() {
            return index < iterationList.size();
        }

        @Override
        public Object getNextElement() {
            if(nextElement()) {
                return iterationList.get(index++);
            }
            return null;
        }
    }
}
