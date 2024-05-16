package mizdooni.repository.user;

import mizdooni.model.user.User;
import org.springframework.data.repository.ListCrudRepository;

interface AbstractUserRepository<T extends User> extends ListCrudRepository<T, String> {
    T findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsernameOrEmail(String username, String email);
}
