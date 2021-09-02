package webstmt.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import webstmt.entity.sys.Role;
import webstmt.entity.sys.Route;
import webstmt.repo.sys.RoleRepository;
import webstmt.repo.sys.RouteRepository;

@Service
public class RouteService {

	@Value("${app.dev-mode}")
	private boolean isDevMode;
	
	@Autowired
	private RouteRepository repo;
	@Autowired
	private RoleRepository roleRepo;

	public List<Route> tree() {
		
		//1. get top level routes
		//2. find children of top level routes, recursively
		
		List<Route> topNodes = repo.getTopLevelRoutes();
		
		for (Route topNode : topNodes) {
			
			topNode.setChildren(findChildren(topNode.getId()));
			
			if(isDevMode) {
				System.out.println("Dev Mode will ignore role check.");
			} else {
				
				System.out.println("Route ID: " + topNode.getId());
				
				List<Role> roles= roleRepo.getBindingRolesByRoute(topNode.getId());
				
				for (Role role : roles) {
					
					System.out.println("Role: "+role);
				}
				
				topNode.setRoles(roleRepo.getBindingRolesByRoute(topNode.getId()));		
				System.out.println("Top Route ID: "+topNode.getId() +" - "+topNode.getRoles());		
			}
			
		}
		
		return topNodes;
	}
	
	public List<Route> treeByRoute(List<Route> topNodes) {
		
		for (Route topNode : topNodes) {
			
			topNode.setParent(null);
			
			List<Route> allChildrenUnderNode = findChildren(topNode.getId());
			
			for (Route child : allChildrenUnderNode) {
				
				if(topNodes.contains(child)) {
					topNode.getChildren().add(child);
				}
			}
		}
		
		return topNodes;
	}
	
	private List<Route> findChildren(long parentId) {
		
		List<Route> children = repo.getChildren(parentId);
		
		for (Route child : children) {
			
			child.setChildren(findChildren(child.getId()));
			child.setParent(null);


			if(isDevMode) {
				System.out.println("Dev Mode will ignore role check.");
			} else {
				child.setRoles(roleRepo.getBindingRolesByRoute(child.getId()));
				System.out.println("Route ID: "+child.getId() +" - "+child.getRoles());	
			}
		}
		
//		System.out.println("Finding children for "+parentId);
//		System.out.println("Result size: "+children.size());
		
		return children;
	}
	
	public List<Route> allRoutes() {
	
		return repo.findAll();
	}
	
	public Route get(long id) {
		return repo.getOne(id);
	}
	
	public Route saveOrUpdate(Route route) {
		
		return repo.save(route);
	}
	
	public void delete(long id) {
		repo.deleteById(id);
	}
	
}
