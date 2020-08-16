package co.com.alejoloopez5.entrega.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IGenericRepo<T,ID> extends ReactiveMongoRepository<T,ID> {

}
