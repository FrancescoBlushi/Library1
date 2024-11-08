package org.Library.Proxy;

import org.Library.Pattern.LoanDaoInterface;
import org.Library.models.Loans;

import java.util.List;

public class LoanDaoProxy implements LoanDaoInterface {

    private LoanDaoInterface loanDao;
    private String userRole;

    public LoanDaoProxy(LoanDaoInterface loanDao, String userRole) {
        this.loanDao = loanDao;
        this.userRole = userRole;
    }

    @Override
    public void addLoan(Loans loan){
        if(userRole.equals("user")){
            loanDao.addLoan(loan);
        }else {
            System.out.println("Access denied");
        }
    }
    @Override
    public void removeUserAndAssociations(String cartId){
        if(userRole.equals("admin")){
            loanDao.removeUserAndAssociations(cartId);
        }else {
            System.out.println("Access denied");
        }
    }

    @Override
    public boolean removeLoan(String isbn){
        if(userRole.equals("admin")){
            return loanDao.removeLoan(isbn);
        }else return false;
    }


}
