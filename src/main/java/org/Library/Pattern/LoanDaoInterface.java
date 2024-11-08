package org.Library.Pattern;

import org.Library.models.Loans;

import java.util.List;

public interface LoanDaoInterface {

    public void addLoan(Loans loan);
    public void removeUserAndAssociations(String cartId);
    public boolean removeLoan(String isbn);
}
