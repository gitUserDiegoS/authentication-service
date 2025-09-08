package co.com.pragma.r2dbc;

import co.com.pragma.r2dbc.entity.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface MyReactiveRepository extends ReactiveCrudRepository<UserEntity, Long>, ReactiveQueryByExampleExecutor<UserEntity> {

    Mono<UserEntity> findByEmail(String email);

    Mono<UserEntity> findByIdDocument(String documentId);

    Flux<UserEntity> findAllByEmailIn(List<String> emails);


}
