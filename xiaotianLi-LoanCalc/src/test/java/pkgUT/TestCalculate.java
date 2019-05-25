package pkgUT;

import app.LoanResolver;
import javafx.collections.ObservableList;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TestCalculate {
    @Test
    public void testCalculate(){
        double loanAmount=2000.00;
        double interestRate=2.00*0.01;
        int termOfYears=2;
        LocalDate firstPaymentDate= LocalDate.of(2019,1,1);
        double additionalPayment=0.00;

        double expectedPaymentPerMonth[]={
                85.08, 85.08, 85.08, 85.08, 85.08, 85.08, 85.08, 85.08, 85.08, 85.08, 85.08, 85.08,
                85.08, 85.08, 85.08, 85.08, 85.08, 85.08, 85.08, 85.08, 85.08, 85.08, 85.08, 85.09
        };
        double expectedInterestPerMonth[]={
                3.33, 3.20, 3.06, 2.92, 2.79, 2.65, 2.51, 2.37, 2.24, 2.10, 1.96, 1.82,
                1.68, 1.54, 1.41, 1.27, 1.13, 0.99, 0.85, 0.71, 0.56, 0.42, 0.28, 0.14
        };
       double expectedPrincipalPerMonth[]={
               81.75, 81.88, 82.02, 82.16, 82.29, 82.43, 82.57, 82.71, 82.84, 82.98, 83.12, 83.26,
               83.40, 83.54, 83.67, 83.81, 83.95, 84.09, 84.23, 84.37, 84.52, 84.66, 84.80, 84.95,
       };
       double expectedBalancePerMonth[]={
               1918.25, 1836.37, 1754.35, 1672.19, 1589.90, 1507.47, 1424.90, 1342.19, 1259.35, 1176.37, 1093.25, 1009.99,
               926.59, 843.05, 759.38, 675.57, 591.62, 507.53, 423.30, 338.93, 254.41, 169.75, 84.95, 0.00,
       };

       double expectedTotalPayment=2041.93;
       double expectedTotalInterest=41.93;
        LoanResolver loanResolver = new LoanResolver();
        ObservableList<LoanResolver.PaymentItem> data
                = loanResolver.CalculatePayment(loanAmount,interestRate,termOfYears,additionalPayment,firstPaymentDate);

        for(int i=1;i<data.size();++i){
            assertEquals(Double.parseDouble(data.get(i).getPayment()),expectedPaymentPerMonth[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getInterest()),expectedInterestPerMonth[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getPrinciple()),expectedPrincipalPerMonth[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getBalance()),expectedBalancePerMonth[i-1],0.01);
        }
        assertEquals(expectedTotalInterest,loanResolver.getTotalInterests(),0.01);
        assertEquals(expectedTotalPayment,loanResolver.getTotalPayments(),0.01);
    }
}
