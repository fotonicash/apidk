# apidk
Kit de desenvolvimento de API REST com Spring

Para usar no projeto, fazer download deste repositório e compilar usando `mvn package` e `mvn install`. Depois é só adicionar esta dependência ao projeto.

```xml
<dependency>
	<groupId>br.com.fotonica</groupId>
	<artifactId>apidk</artifactId>
	<version>1.0.0</version>
</dependency>
```

## Classes genéricas

1. **GenericEntity** - define uma entidade
2. **GenericService** - define um serviço
3. **GenericRestController** - define uma interface REST

----

### 1. GenericEntity

Classe anotacada com `@MappedSuperclass` para servir como pai de todos os modelos. Ela define 5 propriedades por padrão:

1. **createdAt** - que define um timestamp do instante em que o modelo foi salvo.
2. **updatedAt** - que define um timestamp do intestante em que o modelo foi atualizado.
3. **ativo** - flag para soft delete.
4. **username** - propriedade que indica quem fez a ultima atualização.
5. **onwer** - pripriedade que indica quem criou a informação.

Para configurar as propriedades **createdAt** e **updatedAt**, a classe `GenericEntity` implementa os métodos `prePersist ` e `preUpdate` , respectivamente anotados com `@PrePersist` e `PreUpdate` do pacote `javax.persistence.*`.

#### Como usar GenericEntity

Exemplo de como aplicar num modelo de produto:

```java
@Entity
@Table(name = "produto", schema = "public")
@AttributeOverride(name = "id", column = @Column(name = "id_produto"))
public class ProdutoModel extends GenericEntity {
  private String name;
  // demais props and methods
}
```

Esta definição gerará uma tabela no BD o nome `produto` no schema `public` com 6 propriedades, sendo 5 do próprio `GenericEntity` e a propriedade `name` de `Produto`.

--------

### 2. GenericService

Classe de suporte a criação de serviços, elementos responsáveis pela busca de dados do BD, e implementação de regras de negócio. *GenericService* usa a classe *EntityManager* para interagir com o BD com o auxilio da bilioteca *APIQL*.

#### Principais métodos de persistencia

| Método                                             | Descrição                                                    |
| -------------------------------------------------- | ------------------------------------------------------------ |
| `T save(T entity) throws NegocioException`         | Método `@Transactional` para criar o primeiro registro de uma entidade no BD. |
| `void update(T entity) throws NegocioException`    | Método `@Transactional` para atualizar o registro de uma entidade no BD. |
| `List<T> findAll(APIQueryParams params)`           | Método de consulta que retorna uma lista de dados.           |
| `Optional<T> findById(Integer id)`                 | Método de consulta pelo identificador principal do registro. |
| `count(APIQueryParams params)`                     | Método de contagem de registros.                             |
| `void delete(T entity)`                            | Método de exclusão de dados.                                 |
| `void deleteById(Integer id) `                     | Método de exclusão a partir do identificador principal.      |
| `void  validate(T entity)throws NegocioException ` | Método para implementar validação. Esté método não está implementado na classe genérica. O que a torna abstrata. Sendo necessária sua implementação em cada classe derivada de *GenericService*. |
| `protected EntityManager() entityManager`          | Método que retorna o *EntityManager* capturado do contexto pela classe **GenericService* |
| `Class<T> entityClass()`                           | Método que retorna o *Class* em tempo de execução baseado no tipo definido na construção de um filho de *GenericService*. |
| `String entityName()`                              | Método que retorna o nome da classe em tempo de execução.    |

#### Como usar *GenericService*?

A entidade usada para definir um serviço do tipo *GenericService* precisa ser do tipo *GenericEntity*.

Exemplo de como usar *GenericService* na construção da classe de serviço de produto:

```java
@Service
@Transactional
public class ProdutoService extends GenericService<ProdutoModel>{
	public void validate(Produto p) throw NegocioExceptions {
		// implementacao da validaçao
	}
}
```

----

### 3. GenericRestController

A classe *GenericRestController* define uma interface REST baseado num modelo do tipo *GenericEntity* e num serviço *GenericService*.

Está classe aceita requisições vindo de qualquer source, desabilitando o uso CORS. As interfaces HTTP's criadas automaticamente para o recurso produto são:

##### Definição do controlador de produtos

```java
@RestController
@RequestMapping("/produto")
public class ProdutoController extends GenericRestController<ProdutoModel, ProdutoService>{
	// metodos adicinoais
}
```

##### Lista de interfaces criados a partir de *GenericRestController*

| Interface                                             | Método                                                       |
| ----------------------------------------------------- | ------------------------------------------------------------ |
| `POST /produto`                                       | `T save(@RequestBody T entity)`                              |
| `PUT /produto`                                        | `void update(@RequestBody T entity)`                         |
| `GET /produto?size=<size>&page<page>&filter=<filter>` | `Optional<T> findById(@PathVariable Integer id)`             |
| `GET /produto/<ID>`                                   | `Page<T> findAll(@RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "size", required = false) Integer size, @RequestParam(name = "filter", required = false) String filter) ` |
| `GET /produto/count?filter=<filter>`                  | `long count(@RequestParam(name = "filter", required = false) String filter)` |
| `DELETE /produto/<ID>`                                | `void deleteById(@PathVariable("id") Integer id)`            |
| `DELETE /produto`                                     | `void delete(@RequestBody T entity)`                         |
| `OPTIONS /produto`                                    | `void options()`                                             |

----

## Utilitários

1. ExceptionUtil
2. Generate
3. JSON
4. ObjectUtil



----

## Exceptions

1. AnonymousUserException
2. ExceptionGlobalHandler
3. NegocioException



----

## Email

