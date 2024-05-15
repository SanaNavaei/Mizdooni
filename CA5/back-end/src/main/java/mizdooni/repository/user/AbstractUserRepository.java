package mizdooni.repository.user;

import mizdooni.model.user.User;
import org.springframework.data.repository.CrudRepository;

interface AbstractUserRepository<T extends User> extends CrudRepository<T, String> {
    T findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsernameOrEmail(String username, String email);
}
