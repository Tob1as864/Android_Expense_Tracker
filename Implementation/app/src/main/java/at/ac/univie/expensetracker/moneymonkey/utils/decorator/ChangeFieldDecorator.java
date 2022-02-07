package at.ac.univie.expensetracker.moneymonkey.utils.decorator;

import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransactionType;

public class ChangeFieldDecorator implements ChangeField{
    private ChangeField wrappee;

    ChangeFieldDecorator(ChangeField source){
        this.wrappee = source;
    }

    @Override
    public void updateField(Object data_1, Object data_2, DAOTransactionType mDAOTransactionType) {
        wrappee.updateField(data_1, data_2, mDAOTransactionType);
    }
}
