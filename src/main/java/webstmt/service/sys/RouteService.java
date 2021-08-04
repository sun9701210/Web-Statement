package webstmt.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webstmt.entity.sys.Route;
import webstmt.repo.sys.RouteRepository;

@Service
public class RouteService {
	
	@Autowired
	private RouteRepository repo;

	public List<Route> tree() {
		
		//TODO 
		//1. get top level routes
		//2. find children of top level routes, recursively
		
		List<Route> topNodes = repo.getTopLevelRoutes();
		
		for (Route topNode : topNodes) {
			
			topNode.setChildren(findChildren(topNode.getId()));
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
