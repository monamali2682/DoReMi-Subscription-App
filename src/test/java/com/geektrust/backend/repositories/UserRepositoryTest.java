package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Repositories.UserRepository;
import com.geektrust.backend.entities.Subscription;
import com.geektrust.backend.entities.TopUp;
import com.geektrust.backend.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserRepositoryTest")
public class UserRepositoryTest{
    private UserRepository userRepository;

    @BeforeEach
    void setup(){
        List<Subscription> subscriptions1= new ArrayList<>();
        List<Subscription> subscriptions2 = new ArrayList<>();
        TopUp topup1 =new TopUp("1", "FOUR_DEVICE", 4, 50);
        TopUp topup2= new TopUp("2", "TEN_DEVICE", 10, 100);
        final HashMap<String,User> userMap = new HashMap<String,User>(){
            {
                put("1", new User("1", "user1","04-02-2020",subscriptions1,topup1,1));
                put("2", new User("2", "user2","24-08-2020",subscriptions2,topup2,2));
            }
        };
        //User(String id,String name, String startDateOfSubscription,List<Subscription> subscriptions, TopUp topUp, int topupValidityInMonths)
        userRepository = new UserRepository(userMap);
    }

    @Test
    @DisplayName("save method should create and return new User")
    public void saveTopup_ShouldCreateAndSaveUser(){
        //Arrange
        List<Subscription> subscriptions = new ArrayList<>();
        TopUp topup= new TopUp("2", "TEN_DEVICE", 10, 100);
        User user = new User("user3","04-08-2020",subscriptions,topup,2);
        User expectedUser = new User("3","user3","04-08-2020",subscriptions,topup,2);
        
        //Act
        User actualUser = userRepository.save(user);
        //Assert
        Assertions.assertEquals(expectedUser,actualUser);
    }


    @Test
    @DisplayName("findAll method should return All User")
    public void findAll_ShouldReturnAllUsers(){
        //Arrange
        int expectedCount = 2;
        //Act
        List<User> actualUsers = userRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedCount,actualUsers.size());
    }

    @Test
    @DisplayName("findAll method should return Empty List if No Users Found")
    public void findAll_ShouldReturnEmptyList(){
        //Arrange
        int expectedCount = 0;
        UserRepository emptyUserRepository = new UserRepository();
        //Act
        List<User> actualUsers = emptyUserRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedCount,actualUsers.size());
    }


    @Test
    @DisplayName("findById method should return User Given Id")
    public void findById_ShouldReturnUser_GivenUserId(){
        //Arrange
        String expectedUserId = "1";
        //Act
        Optional<User> actualUser = userRepository.findById(expectedUserId);
        //Assert
        Assertions.assertEquals(expectedUserId,actualUser.get().fetchId());
    }

    @Test
    @DisplayName("findById method should return empty if User Not Found")
    public void findById_ShouldReturnEmptyIfUserNotFound(){
        //Arrange
        Optional<User> expected = Optional.empty();
        //Act
        Optional<User> actual = userRepository.findById("3");
        //Assert
        Assertions.assertEquals(expected,actual);
    }

    @Test
    @DisplayName("Delete Method Should Delete User From Repository")
    public void delete_ShouldDeleteUserFromRepository(){
        //Arrange
        int expectedCount = 1;
        //Act
        userRepository.deleteById("2");
        Long actualCount = userRepository.count();

        //Assert
        Assertions.assertEquals(expectedCount,actualCount);
    }
}
