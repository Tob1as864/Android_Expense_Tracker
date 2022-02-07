package at.ac.univie.expensetracker.moneymonkey.utils.decorator;

import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransactionType;

/**
 * This is a debug-decorator
 */
public class UpdateActiveActiveDecorator extends ChangeFieldDecorator {
    public UpdateActiveActiveDecorator(ChangeField source) {
        super(source);
    }

    @Override
    public void updateField(Object data_1, Object data_2, DAOTransactionType mDAOTransactionType) {
        System.out.println(mDAOTransactionType.toString());
        super.updateField(data_1, data_2, mDAOTransactionType);
    }





}
