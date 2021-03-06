package webstmt.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//import lombok.Getter;
//import lombok.Setter;

@Entity
public class AccountSummaryRecord implements Serializable, ForeachReplaceable
{
	private static final long serialVersionUID = 2817468080995212879L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
//	@Getter@Setter
	private String date;
//	@Getter@Setter
	private String relationshipNo;
//	@Getter@Setter
	private String type;
//	@Getter@Setter
	private String accountNo;
//	@Getter@Setter
	private String currency;
//	@Getter@Setter
	private double foreignBalance;
//	@Getter@Setter
	private double balance;
	
	public double getBalance()
	{
		return balance;
	}
	
	@Override
	public String getTextByPlaceholder(String placeholder) {
		
		if("{$type}".equals(placeholder))
		{
			return type;
		}
		
		if("{$acct-no}".equals(placeholder))
		{
			return accountNo;
		}
		
		if("{$currency}".equals(placeholder))
		{
			return currency;
		}
		
		if("{$foreign-balance}".equals(placeholder))
		{
			return foreignBalance+"";
		}
		
		if("{$balance}".equals(placeholder))
		{
			return balance+"";
		}
		
		return "";
	}

}
