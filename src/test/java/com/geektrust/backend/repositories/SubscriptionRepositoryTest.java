package com.geektrust.backend.repositories;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Repositories.SubscriptionRepository;
import com.geektrust.backend.entities.Plan;
import com.geektrust.backend.entities.Subscription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SubscriptionRepositoryTest")
public class SubscriptionRepositoryTest {
    private SubscriptionRepository subscriptionRepository;

    @BeforeEach
    void setup(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date1= LocalDate.parse("10-02-2020",formatter);
        LocalDate date2= LocalDate.parse("15-03-2020",formatter);
        LocalDate date3= LocalDate.parse("16-07-2020",formatter);
        Plan plan1= new Plan("1", "MUSIC", "FREE", 0, 1, 1);
        Plan plan2 = new Plan("2", "MUSIC", "PERSONAL", 100, 1, 1);
        Plan plan3 = new Plan("3", "PODCAST", "PREMIUM", 300, 3, 1);

        final HashMap<String,Subscription> subscriptionMap = new HashMap<String,Subscription>(){
            {
                put("1", new Subscription("1", "MUSIC", date1,plan1));
                put("2", new Subscription("2", "MUSIC", date2,plan2));
                put("3", new Subscription("3", "PODCAST", date3,plan3));
            }
        };
        subscriptionRepository = new SubscriptionRepository(subscriptionMap);
    }

    @Test
    @DisplayName("save method should create and return new Subscription")
    public void saveSubscription_ShouldSaveSubscription(){
        //Arrange
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date4= LocalDate.parse("25-08-2020",formatter);
        Plan plan4= new Plan("1", "PODCAST", "FREE", 0, 1, 1);
        Subscription subscription4 = new Subscription( "PODCAST", date4,plan4);
        Subscription expectedSubscription = new Subscription("4", "PODCAST", date4,plan4);
        //Act
        Subscription actualSubscription = subscriptionRepository.save(subscription4);
        //Assert
        Assertions.assertEquals(expectedSubscription,actualSubscription);
    }


    @Test
    @DisplayName("findAll method should return All Subscription")
    public void findAllSubscription_ShouldReturnAllSubscriptions(){
        //Arrange
        int expectedCount = 3;
        //Act
        List<Subscription> actualSubscriptions = subscriptionRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedCount,actualSubscriptions.size());
    }

    @Test
    @DisplayName("findAll method should return Empty List if No Subscriptions Found")
    public void findAllSubscription_ShouldReturnEmptyList(){
        //Arrange
        int expectedCount = 0;
        SubscriptionRepository emptySubscriptionRepository = new SubscriptionRepository();
        //Act
        List<Subscription> actualSubscriptions = emptySubscriptionRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedCount,actualSubscriptions.size());
    }


    @Test
    @DisplayName("findById method should return Subscription Given Id")
    public void findById_ShouldReturnSubscription_GivenSubscriptionId(){
        //Arrange
        String expectedSubscriptionId = "3";
        //Act
        Optional<Subscription> actualSubscription = subscriptionRepository.findById(expectedSubscriptionId);
        //Assert
        Assertions.assertEquals(expectedSubscriptionId,actualSubscription.get().fetchId());
    }

    @Test
    @DisplayName("findById method should return empty if Subscription Not Found")
    public void findById_ShouldReturnEmptyIfSubscriptionNotFound(){
        //Arrange
        Optional<Subscription> expected = Optional.empty();
        //Act
        Optional<Subscription> actual = subscriptionRepository.findById("5");
        //Assert
        Assertions.assertEquals(expected,actual);
    }


    @Test
    @DisplayName("Delete Method Should Delete Subscription From Repository")
    public void delete_ShouldDeleteSubscriptionFromRepository(){
        //Arrange
        int expectedCount = 2;
        //Act
        subscriptionRepository.deleteById("3");
        Long actualCount = subscriptionRepository.count();

        //Assert
        Assertions.assertEquals(expectedCount,actualCount);
    }
}
