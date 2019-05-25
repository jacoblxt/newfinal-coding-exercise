package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import org.apache.poi.ss.formula.functions.FinanceLib;
import java.time.LocalDate;

public class LoanResolver {
    private final ObservableList<PaymentItem> data = FXCollections.observableArrayList();
    private double totalPayments;
    private double totalInterests;

    public double getTotalPayments() {
        totalPayments=0;
        for(int i=1;i<data.size();++i){
            totalPayments+=Double.parseDouble(data.get(i).getPayment());
        }
        totalPayments=Rounding2(totalPayments);
        return totalPayments;
    }

    public double getTotalInterests() {
        totalInterests=0;
        for(int i=1;i<data.size();++i){
            totalInterests+=Double.parseDouble(data.get(i).getInterest());
        }
        totalInterests=Rounding2(totalInterests);
        return totalInterests;
    }

    public ObservableList<PaymentItem> CalculatePayment(double loanAmount, double interestRate, double numberOfYears, double additionalPayment, LocalDate date){
        double balance=Rounding2(loanAmount-additionalPayment);
        data.add(new PaymentItem(null,null,null,null,null,null,String.format("%.2f",balance)));
        double PMT = Rounding2(Math.abs(FinanceLib.pmt(interestRate/12.00, numberOfYears*12.00, loanAmount, 0, false)));

        int paymentNumber=0;
        while(balance>0){
            double interest=Rounding2(balance*interestRate/12.00);
            double principal;
            double payment;
            if(Rounding2(PMT-(balance+interest))>=-0.01){
                principal=balance;
                payment=Rounding2(balance+interest);
                balance=0;
            }else{
                principal=Rounding2(PMT-interest);
                payment=PMT;
                balance=Rounding2(balance-principal);
            }
            paymentNumber++;
            data.add(
                    new PaymentItem(Integer.toString(paymentNumber),date.toString(),String.format("%.2f",payment),null,String.format("%.2f",interest),String.format("%.2f",principal),String.format("%.2f",balance)));
            date=date.plusMonths(1);
        }
        return data;
    }

    public static double Rounding2(double value) {
        return ((double)Math.round(value*100))/100;
    }

    public static class PaymentItem {
        private final SimpleStringProperty paymentNumber;
        private final SimpleStringProperty date;
        private final SimpleStringProperty payment;
        private final SimpleStringProperty additionalPayment;
        private final SimpleStringProperty interest;
        private final SimpleStringProperty principle;
        private final SimpleStringProperty balance;

        public PaymentItem(String paymentNumber, String date, String payment, String additionalPayment, String interest, String principle, String balance) {
            this.paymentNumber = new SimpleStringProperty(paymentNumber);
            this.date = new SimpleStringProperty(date);
            this.payment = new SimpleStringProperty(payment);
            this.additionalPayment = new SimpleStringProperty(additionalPayment);
            this.interest = new SimpleStringProperty(interest);
            this.principle = new SimpleStringProperty(principle);
            this.balance = new SimpleStringProperty(balance);
        }

        public String getPaymentNumber() {
            return this.paymentNumber.get();
        }

        public void setPaymentNumber(String paymentNumber) {
            this.paymentNumber.set(paymentNumber);
        }

        public String getDate() {
            return this.date.get();
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getPayment() {
            return this.payment.get();
        }

        public void setPayment(String payment) {
            this.payment.set(payment);
        }

        public String getAdditionalPayment() {
            return this.additionalPayment.get();
        }

        public void setAdditionalPayment(String additionalPayment) {
            this.additionalPayment.set(additionalPayment);
        }

        public String getInterest() {
            return this.interest.get();
        }

        public void setInterest(String interest) {
            this.interest.set(interest);
        }

        public String getPrinciple() {
            return this.principle.get();
        }

        public void setPrinciple(String principle) {
            this.principle.set(principle);
        }

        public String getBalance() {
            return this.balance.get();
        }

        public void setBalance(String balance) {
            this.balance.set(balance);
        }
    }
}
