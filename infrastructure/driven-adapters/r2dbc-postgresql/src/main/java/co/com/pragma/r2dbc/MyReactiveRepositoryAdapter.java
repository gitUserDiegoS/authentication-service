package co.com.pragma.r2dbc;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.r2dbc.entity.UserEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;

import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User/* change for domain model */,
        UserEntity/* change for adapter model */,
        Long,
        MyReactiveRepository
        > implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(MyReactiveRepositoryAdapter.class);


    private final TransactionalOperator operator;

    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper, TransactionalOperator operator) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, entity -> mapper.map(entity, User.class));
        this.operator = operator;

    }

    @Override
    public Mono<User> save(User user) {
        log.trace("Create user with email: {}", user.getEmail());
        return super.save(user)
                .as(operator::transactional)
                .doOnNext(savedUser -> log.trace("User created successfully with id: {}", savedUser.getIdUser()))
                .doOnError(error -> log.error("Error in user Creation, failed with message: {}", error.getMessage()));
    }

    @Override
    public Mono<User> findByEmail(String email) {
        log.trace("Validate if email is already registered");
        return repository.findByEmail(email)
                .map(entity -> mapper.map(entity, User.class))
                .doOnNext(user -> log.trace("User found with email: {}", user.getEmail()))
                .doOnError(error -> log.error("Error searching user by email, failed with message: {}", error.getMessage()));
    }

    @Override
    public Mono<User> findByIdDocument(String documentId) {
        log.trace("Start search of user by id document {}", documentId);
        return repository.findByIdDocument(documentId)
                .map(entity -> mapper.map(entity, User.class))
                .doOnNext(user -> log.trace("User found with id: {}", user.getIdDocument()))
                .doOnError(error -> log.error("Error searching user by id document, failed with message: {}", error.getMessage()));
    }

    @Override
    public Flux<User> findAllByEmailIn(List<String> emails) {
        return repository.findAllByEmailIn(emails)
                .map(entity -> mapper.map(entity, User.class));
    }
}
