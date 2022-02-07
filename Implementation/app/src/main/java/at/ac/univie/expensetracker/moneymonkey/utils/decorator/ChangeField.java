package at.ac.univie.expensetracker.moneymonkey.utils.decorator;

import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransactionType;

public interface ChangeField {
    void updateField (Object data_1, Object data_2, DAOTransactionType mDAOTransactionType);


}

