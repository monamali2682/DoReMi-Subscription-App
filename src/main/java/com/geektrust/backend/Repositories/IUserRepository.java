package com.geektrust.backend.Repositories;

import java.util.Optional;
import com.geektrust.backend.entities.User;

public interface IUserRepository extends CRUDRepository<User, String>{
    public User save(User entity);
    public Optional<User> findByName(String name);
    public User getUser(String string);
}
