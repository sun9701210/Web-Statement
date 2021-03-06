package webstmt.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//import lombok.Getter;
//import lombok.Setter;

@Entity
public class ProductTotalSummary implements Serializable, ForeachReplaceable
{
	private static final long serialVersionUID = -919858668791497355L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
//	@Getter@Setter
	private String statementDate;
//	@Getter@Setter
	private String relationshipNo;
//	@Getter@Setter
	private String name;
//	@Getter@Setter
	private String amount;
//	@Getter@Setter
	private String type;
	
	public String getType() {
		return type;
	}

	@Override
	public String getTextByPlaceholder(String placeholder) {
		
		if("{$name}".equalsIgnoreCase(placeholder))
		{
			return name;
		}
		
		if("{$amount}".equalsIgnoreCase(placeholder))
		{
			return amount;
		}
		
		return "";
	}
}
