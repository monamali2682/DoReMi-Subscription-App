package com.geektrust.backend.Repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.entities.User;


public class UserRepository implements IUserRepository{
    private final Map<String,User> userMap;
    private Integer autoIncrement;

    public UserRepository(){
        userMap = new HashMap<String,User>();
        this.autoIncrement = 0;
    }

    public UserRepository(Map<String, User> userMap) {
        this.userMap = userMap;
        this.autoIncrement = userMap.size();
    }

    @Override
    public User save(User entity) {
        if( entity.getId() == null ){
            autoIncrement++;
            User u = new User(Integer.toString(autoIncrement),entity.getName(),entity.getStartDateOfSubscription(),entity.getSubscriptions(),entity.getTopUp(),entity.getTopupValidityInMonths());
            userMap.put(u.getId(),u);
            return u;
        }
        userMap.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public List<User> findAll() {
     return userMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return userMap.containsKey(id);
    }

    @Override
    public void delete(User entity) {
        userMap.remove(entity.getId());
    }

    @Override
    public void deleteById(String id) {
        userMap.remove(id);
        
    }

    @Override
    public long count() {
        return userMap.size();
    }

    @Override
    public Optional<User> findByName(String name) {
     return userMap.values().stream().filter(u -> u.getName().equals(name)).findFirst();
    }

    @Override
    public User getUser(String string){
        User user;
        if(!findByName("user").isPresent()){
            user = new User();
            save(user);
        }
        else{
            user = findByName("user").orElseThrow(()-> new RuntimeException("User not found"));
        }
        return user;
    }
    
}
