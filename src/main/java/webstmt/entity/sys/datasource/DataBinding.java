package webstmt.entity.sys.datasource;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import webstmt.entity.sys.Template;

@Entity
public class DataBinding implements Serializable
{
	public static final String DATA_BINDING_TYPE_SINGLE_VALUE="single";
	public static final String DATA_BINDING_TYPE_LOOP_VALUE="loop";
	
	private static final long serialVersionUID = 5346374504784094753L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String placeholder;
	private String bindingType;
	private String processorClassName;
	
	@ManyToOne
	private DataDictionary dictionary;
	
	@ManyToOne
	private Template template;

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

	public String getBindingType() {
		return bindingType;
	}

	public void setBindingType(String bindingType) {
		this.bindingType = bindingType;
	}

	public String getProcessorClassName() {
		return processorClassName;
	}

	public void setProcessorClassName(String processorClassName) {
		this.processorClassName = processorClassName;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public DataDictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(DataDictionary dictionary) {
		this.dictionary = dictionary;
	}
}
