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
public class OneGlanceSummary implements Serializable
{
	private static final long serialVersionUID = -2237959156113112331L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
//	@Getter@Setter
	private String statementDate;
//	@Getter@Setter
	private String sectionId;
//	@Getter@Setter
	private String relationshipNo;
//	@Getter@Setter
	private String dueDate;
//	@Getter@Setter
	private boolean hasCheckingAccount;
//	@Getter@Setter
	private BigDecimal totalAmountCheckingAccount;
//	@Getter@Setter
	private boolean hasSavingInvestmentAccount;
//	@Getter@Setter
	private BigDecimal totalAmountSavingInvestment;
//	@Getter@Setter
	private boolean hasLoanAccount;
//	@Getter@Setter
	private BigDecimal totalAmountLoan;
//	@Getter@Setter
	private boolean hasCreditCard;
//	@Getter@Setter
	private BigDecimal totalAmountCreditCard;
//	@Getter@Setter
	private boolean hasInsurance;
//	@Getter@Setter
	private BigDecimal totalAmountInsurance;
	
	public String getDueDate() {
		return dueDate;
	}
	public boolean isHasCheckingAccount() {
		return hasCheckingAccount;
	}
	public BigDecimal getTotalAmountCheckingAccount() {
		return totalAmountCheckingAccount;
	}
	public boolean isHasSavingInvestmentAccount() {
		return hasSavingInvestmentAccount;
	}
	public BigDecimal getTotalAmountSavingInvestment() {
		return totalAmountSavingInvestment;
	}
	public boolean isHasLoanAccount() {
		return hasLoanAccount;
	}
	public BigDecimal getTotalAmountLoan() {
		return totalAmountLoan;
	}
	public boolean isHasCreditCard() {
		return hasCreditCard;
	}
	public BigDecimal getTotalAmountCreditCard() {
		return totalAmountCreditCard;
	}
	public boolean isHasInsurance() {
		return hasInsurance;
	}
	public BigDecimal getTotalAmountInsurance() {
		return totalAmountInsurance;
	}
}
