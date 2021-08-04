package webstmt.service.sys;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webstmt.entity.sys.Role;
import webstmt.entity.sys.Route;
import webstmt.repo.sys.RoleRepository;
import webstmt.repo.sys.RouteRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repo;
	@Autowired
	private RouteRepository routeRepo;
	@Autowired
	private RouteService routeService;
	
	public List<Role> list() {
		
		List<Role> allRoles = repo.findAll();
		
		for (Role role : allRoles) {
			
			role.setRoutes(routeService.treeByRoute(role.getRoutes()));
		}
		
		return repo.findAll();
	}
	
	public Role get(long id) {
		
		Role role = repo.getOne(id);
		
		role.setRoutes(routeService.treeByRoute(role.getRoutes()));
		
		return role;
	}
	
	public Role saveOrUpdate(Role role) {
		
		role.setKey(role.getName().toLowerCase());
		
		System.out.println(role);
		
		List<Route> savedNodes = new ArrayList<Route>();
		
		for (Route node : role.getRoutes()) {
			
			findNodes(node, savedNodes);
			
		}
		
		for (Route route : savedNodes) {
			
			System.out.println(route);
		}
		
		role.setRoutes(savedNodes);
		
		System.out.println("Saved Routes Count: "+savedNodes.size());
		System.out.println(role);
		
		Role savedRole = repo.save(role);
		
		savedRole.setRoutes(routeService.treeByRoute(savedRole.getRoutes()));
		
		return savedRole;
	}
	
	private List<Route> findNodes(Route parentNode, List<Route> result) {
		
		if(parentNode!=null&&parentNode.getChildren()!=null) {
			
			for (Route node : parentNode.getChildren()) {
				
				findNodes(node, result);
			}	
		}
		
		if(parentNode!=null) {
			
			Route originalRoute = routeRepo.getOne(parentNode.getId());
			
			result.add(originalRoute);
		}
		
		return result;
	}
	
//	public Role update(Role role) {
//		
//		return repo.save(role);
//	}
	
	@Transactional
	public void delete(long id) {
		
		Role deletingRole = repo.getOne(id);
		
		deletingRole.setRoutes(null);
		
		repo.delete(deletingRole);
		
//		repo.deleteById(id);
	}
}
