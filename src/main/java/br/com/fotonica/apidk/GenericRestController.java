package br.com.fotonica.apidk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.fotonica.apiql.APIQueryParams;
import br.com.fotonica.exception.NegocioException;
import br.com.fotonica.exception.ResourceNotFoundException;
import br.com.fotonica.util.JSON;

@CrossOrigin(origins = "*")
public class GenericRestController<T extends GenericEntity, S extends GenericService<T>> {

	@Autowired
	protected S service;

	@PostMapping
	public T save(@RequestBody T entity) throws NegocioException {
		System.err.println(entity);
		return service.save(entity);
	}
	
	@PutMapping
	public T update(@RequestBody T entity) throws NegocioException {
		return service.update(entity);
	}
	
//	@PostMapping("/each")
//	public Iterable<T> saveAll(Iterable<T> entities) {
//		return service.saveAll(entities);
//	}

	@GetMapping("{id}")
	public T findById(@PathVariable Integer id) throws ResourceNotFoundException {
		return service.findById(id);
	}

//	@GetMapping("/{id}/exist")
//	public boolean existsById(Integer id) {
//		return service.existsById(id);
//	}

	@GetMapping
	public Page<T> findAll(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size, 
			@RequestParam(name = "filter", required = false) String filter) {
		
		Integer p = page != null ? page : 0;
		Integer s = size != null ? size : 10;
		
		Pageable pageable = PageRequest.of(p, s);
		
		APIQueryParams params = new APIQueryParams();
		params.setSize(s);
		params.setPage(p);
		
		if(filter != null) {
			params.setFilter(JSON.toMap(filter));
		}
		
		return new PageImpl<T>(service.findAll(params), pageable, count(filter));
		
//		return service.findAll(params);
	}

//	@GetMapping("/each")
//	public Iterable<T> findAllById(@RequestParam("ids") Iterable<Integer> ids) {
//		return service.findAllById(ids);
//	}

	@GetMapping("/qtn")
	public long count(@RequestParam(name = "filter", required = false) String filter) {
		APIQueryParams params = new APIQueryParams();
		if(filter != null) {
			params.setFilter(JSON.toMap(filter));
		}
		return service.count(params);
	}

	@DeleteMapping("{id}")
	public void deleteById(@PathVariable("id") Integer id) {
		service.deleteById(id);
	}

	@DeleteMapping("/one")
	public void delete(@RequestBody T entity) {
		service.delete(entity);
	}

//	@DeleteMapping("/each")
//	public void deleteAll(@RequestBody Iterable<T> entities) {
//		service.deleteAll(entities);
//	}

//	@DeleteMapping
//	public void deleteAll() {
//		service.deleteAll();
//	}
	
	@RequestMapping(method = RequestMethod.OPTIONS)
	public void options() {
		System.err.println("OPTIONS");
	}

}
