package co.com.pragma.r2dbc;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.r2dbc.entity.UserEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User/* change for domain model */,
        UserEntity/* change for adapter model */,
        Long,
        MyReactiveRepository
        > implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(MyReactiveRepositoryAdapter.class);


    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, entity -> mapper.map(entity, User.class));
    }

    @Override
    public Mono<User> save(User user) {
        log.trace("MyReactiveRepositoryAdapter, create user with email: {}", user.getEmail());
        return super.save(user)
                .doOnNext(savedUser -> log.trace("User created with id: {}", savedUser.getIdUser()))
                .doOnError(error -> log.error("Error in MyReactiveRepositoryAdapter: {}", error));
    }

    @Override
    public Mono<User> findByEmail(String email) {
        log.trace("MyReactiveRepositoryAdapter, validate if email is already registered");
        return repository.findByEmail(email)
                .map(entity -> mapper.map(entity, User.class))
                .doOnNext(user -> log.trace("User found: {}", user.getEmail()))
                .doOnError(error -> log.error("Error finding user by email: {}", error));
    }
}
