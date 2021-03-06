package webstmt.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//import lombok.Getter;
//import lombok.Setter;

@Entity
public class AccountDetailRecordSaIn implements Serializable, ForeachReplaceable
{
	private static final long serialVersionUID = -8480316209478031197L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
//	@Getter@Setter
	private String dateMonth;
//	@Getter@Setter
	private String relationshipNo;
//	@Getter@Setter
	private String accountNo;
//	@Getter@Setter
	private String accountDescription;
//	@Getter@Setter
	private String transactionDescription;
//	@Getter@Setter
	private String transactionDate;
//	@Getter@Setter
	private double deposit;
//	@Getter@Setter
	private double withdrawl;
//	@Getter@Setter
	private double balance;
//	@Getter@Setter
	private double balanceLocalCurrency;
	
	public String getAccountNo() {
		return accountNo;
	}

	public String getAccountDescription() {
		return accountDescription;
	}

	public double getDeposit() {
		return deposit;
	}

	public double getWithdrawl() {
		return withdrawl;
	}

	public double getBalance() {
		return balance;
	}

	public double getBalanceLocalCurrency() {
		return balanceLocalCurrency;
	}

	@Override
	public String getTextByPlaceholder(String placeholder) {
		
		
		if("{$desc}".equalsIgnoreCase(placeholder))
		{
			return transactionDescription;
		}
		
		if("{$date}".equalsIgnoreCase(placeholder))
		{
			return transactionDate;
		}
		
		if("{$deposit}".equalsIgnoreCase(placeholder))
		{
			if(deposit<=0) return "";
			
			return ""+deposit;
		}
		
		if("{$balance}".equalsIgnoreCase(placeholder))
		{
			return balanceLocalCurrency+"";
		}
		
		if("{$withdrawl}".equalsIgnoreCase(placeholder))
		{
			return ""+withdrawl;
		}
		
		if("{$acct-desc}".equalsIgnoreCase(placeholder))
		{
			return accountDescription;
		}
		
		if("{$acct-no}".equalsIgnoreCase(placeholder))
		{
			return accountNo;
		}
		
		return "";
	}
	
}
