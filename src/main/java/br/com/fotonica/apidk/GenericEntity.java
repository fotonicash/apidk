package br.com.fotonica.apidk;

import java.io.Serializable;
import java.security.Principal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.fotonica.exception.AnonymousUserException;

@MappedSuperclass
public class GenericEntity implements Serializable{

	/**
	 * AUTO - deixa o provedor de persistencia escolher a estrategia mais adequada em funcao do banco
	 * IDENTITY - o id sera incrementado pela coluna de autoincrement do banco de dados
	 * SEQUENCE - o id sera gerado a partir de uma sequence. Caso nao seja especificado um sequence, sera usado a sequence padrao global para o banco
	 * TABLE - o valor e baseado numa tabela de keys
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, updatable = false, name = "created_at")
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	@Audited
	protected Date createdAt = new Date();

	@LastModifiedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	@JsonIgnore
	@Audited
	private Date updatedAt;
	
	// Atributo de controle para exclusão lógica TRUE = ativo FALSE = inativo
	@Column(nullable = false)
	@JsonIgnore
	@Audited
	private Boolean ativo = true;
	
	@JsonIgnore
	@Audited
	private String username; // who did the action
	
	@JsonIgnore
	@Audited
	private String owner; // resource owner

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	@PreUpdate
	public void preUpdate() throws Exception {
		setOwner();
		this.updatedAt = new Date();
	}

	@PrePersist
	public void prePersist() throws Exception {
		setOwner();
	}
	
	public void setOwner() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() != null) {
			if(auth.getPrincipal().equals("anonymousUser")) {
				throw new AnonymousUserException();
			}
			
			UserDetails principal = (UserDetails)auth.getPrincipal();
			this.owner = principal.getUsername();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
