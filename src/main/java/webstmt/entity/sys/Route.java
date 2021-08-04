package webstmt.entity.sys;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="sys_route")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Route {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name="";
	private String path="";
	private String component="";
	private String redirect="";
	private boolean alwaysShow;
	private boolean hidden;
	
	@OneToOne(cascade = CascadeType.ALL)
	private RouteMeta meta;
	
	@OneToOne(optional=true, cascade = { CascadeType.MERGE })
	private Route parent;
	
//	@OneToMany(cascade = CascadeType.ALL)
	@Transient
	private List<Route> children=new ArrayList<Route>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public boolean isAlwaysShow() {
		return alwaysShow;
	}

	public void setAlwaysShow(boolean alwayShow) {
		this.alwaysShow = alwayShow;
	}

	public Route getParent() {
		return parent;
	}

	public void setParent(Route parent) {
		this.parent = parent;
	}

	public List<Route> getChildren() {
		return children;
	}

	public void setChildren(List<Route> children) {
		this.children = children;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public RouteMeta getMeta() {
		return meta;
	}

	public void setMeta(RouteMeta meta) {
		this.meta = meta;
	}

	@Override
	public String toString() {
		return "Route [id=" + id + ", hasParent=" + (parent!=null) + ", path=" + path + ", component=" + component + ", redirect=" + redirect
				+ ", alwayShow=" + alwaysShow + ", hidden=" + hidden + ", meta=" + meta + "]";
	}
}