package br.com.fotonica.apidk;

import org.springframework.data.jpa.repository.JpaRepository;

interface GenericRespository<T extends GenericEntity> extends JpaRepository<T, Integer>{

}
