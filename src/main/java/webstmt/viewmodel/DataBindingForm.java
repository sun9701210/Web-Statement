package webstmt.viewmodel;

public class DataBindingForm {

	private long id;
	private String placeholder;
	private long dictionaryId;
	private long templateId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	public long getDictionaryId() {
		return dictionaryId;
	}
	public void setDictionaryId(long dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	public long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}
	@Override
	public String toString() {
		return "DataBindingForm [placeholder=" + placeholder + ", dictionaryId=" + dictionaryId + ", templateId="
				+ templateId + "]";
	}
	
	
}
