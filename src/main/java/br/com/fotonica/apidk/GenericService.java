package br.com.fotonica.apidk;

import java.lang.reflect.ParameterizedType;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.gson.internal.LinkedTreeMap;

import br.com.fotonica.apiql.APIQueryParams;
import br.com.fotonica.apiql.ObjectToJPQL;
import br.com.fotonica.exception.NegocioException;
import br.com.fotonica.exception.ResourceNotFoundException;

public abstract class GenericService<T extends GenericEntity> {
	
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	EntityManager em;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * CRUD Methods
	 * 
	 * @throws NegocioException
	 */
	@Transactional
	public T save(T entity) throws NegocioException {
//		logger.info(String.format("save %s", getClass().getName()));
		validate(entity);
		em.persist(entity); // anexar entidade ao contexto persistente
		em.flush();
		return entity;
	}
	
	@Transactional
	public T update(T entity) throws NegocioException {
		T v = em.merge(entity);
		return v;
	}

	public T findById(Integer id) throws ResourceNotFoundException {
		Class<T> clazz = entityClass();
		T v = em.find(clazz, id);
		if(v != null) return v;
		throw new ResourceNotFoundException(String.format("%s with id %d not found", clazz.getSimpleName(), id));
	}

//	public boolean existsById(Integer id) {
//		return repository.existsById(id);
//	}

//	public Page<T> findAll(Pageable page) {
//		return repository.findAll(page);
//	}
	
	protected String getOnwerFromSecurityHolder() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	/**
	 * Return list with username from admin account associeted with the colab
	 * @param username
	 * @return
	 */
	private Optional<String> getOnwerFromColabUser() {
		String username = ((Principal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName();
		Query query = em.createQuery("select s.owner from Settings s join s.collaborators c where c.keycloakUsername=:username");
		query.setParameter("username", username);
		return query.getResultList().stream().findFirst();
	}
	
	private Collection<? extends GrantedAuthority> getAuthoritiesFromSecurityHolder(){
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	}
	
	private Boolean hasRoleAdmin() {
		List<String> authorities = getAuthoritiesFromSecurityHolder().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());
		return authorities.contains("admin");
	}
	
	private Boolean hasRoleColab() {
		List<String> authorities = getAuthoritiesFromSecurityHolder().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());
		return authorities.contains("colab");
	}

	public List<T> findAll(APIQueryParams params) {
		logger.info(String.format("findAll with params %s", params.toString()));
//		String owner = getOnwerFromSecurityHolder();
//		System.err.println("owner: " + owner);
//		if(hasRoleColab()) {
//			Optional<String> manager = getOnwerFromColabUser();
//			if(!manager.isEmpty()) {
//				owner = manager.get();
//			}
//		}
		
		ObjectToJPQL objectToJPQL = new ObjectToJPQL();

		if (params.getSort() == null)
			params.setSort(Arrays.asList("createdAt desc"));

		// query by ativo true by default
		if (params.getFilter() != null) {
			params.getFilter().put("ativo", true);
//			params.getFilter().put("owner", owner);
		} else {
			LinkedTreeMap<String, Object> filter = new LinkedTreeMap<String, Object>();
			filter.put("ativo", true);
//			filter.put("owner", owner);
			params.setFilter(filter);
		}
		
		params.setEntityName(entityName());
		String jpql = objectToJPQL.build(params);
//		System.err.println(jpql);
		
		Query query = objectToJPQL.execute(em);
		// https://wiki.eclipse.org/EclipseLink/Examples/JPA/Caching
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setHint("eclipselink.refresh", "true");

		
		if (params.getSize() != null)
			query.setMaxResults(params.getSize());
		query.setFirstResult(params.getPage() != null ? params.getPage() * params.getSize() : 0);

		List<T> result = query.getResultList();
//		System.err.println(result);
//		result.forEach((r)->{
//			System.err.println(JSON.stringify(r));
//		});
		
		return result;
	}
	
	public long count(APIQueryParams params) {
		ObjectToJPQL objectToJPQL = new ObjectToJPQL();
		params.setEntityName(entityName());
		String jpql = objectToJPQL.build(params);
//		System.err.println(jpql);

//		loggerHelper.generateLog(this.getClass(), String.format("COUNT {filter %s; page: %d, size: %d}",
//				params.getFilter(), params.getPage(), params.getSize()), inferirTipoGenericoNome());

		Query query = objectToJPQL.count(em);

		return (long)query.getSingleResult();
	}
	
	protected String entityName() {
		return entityClass().getSimpleName();
	}

	@SuppressWarnings("unchecked")
	protected Class<T> entityClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

//	public Iterable<T> findAllById(Iterable<Integer> ids) {
//		return repository.findAllById(ids);
//	}

	@Transactional
	public void deleteById(Integer id) {
		T entity = em.getReference(entityClass(), id);
		em.remove(entity);
	}

	public void delete(T entity) {
		em.remove(entity);
	}

//	public void deleteAll(Iterable<T> entities) {
//		entities.forEach(e -> em.remove(e));
//	}

//	public void deleteAll() {
//		repository.deleteAll();
//	}

	/**
	 * Custom methods
	 * 
	 * @throws NegocioException
	 */
	public abstract void validate(T entity) throws NegocioException;
	
	protected EntityManager entityManager() {
		return em;
	}
}