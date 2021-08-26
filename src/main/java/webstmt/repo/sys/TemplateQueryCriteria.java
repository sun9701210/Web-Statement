package webstmt.repo.sys;

public class TemplateQueryCriteria {

	private String nameWildcard;
	private Integer priority;
	private String market;
	private String idSort;
	private Long typeId;
	public String getNameWildcard() {
		return nameWildcard;
	}
	public void setNameWildcard(String nameWildcard) {
		this.nameWildcard = nameWildcard;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getIdSort() {
		return idSort;
	}
	public void setIdSort(String idSort) {
		this.idSort = idSort;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
}
