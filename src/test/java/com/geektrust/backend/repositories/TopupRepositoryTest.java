package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Repositories.TopupRepository;
import com.geektrust.backend.entities.TopUp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TopupRepositoryTest")
public class TopupRepositoryTest{
    private TopupRepository topupRepository;

    @BeforeEach
    void setup(){
        final HashMap<String,TopUp> topupMap = new HashMap<String,TopUp>(){
            {
                put("1", new TopUp("1", "FOUR_DEVICE", 4,50));
            }
        };
        topupRepository = new TopupRepository(topupMap);
    }

    @Test
    @DisplayName("save method should create and return new Topup")
    public void saveTopup_ShouldCreateAndSaveTopup(){
        //Arrange
        TopUp topup2 =  new TopUp( "TEN_DEVICE",10,100); 
        TopUp expectedTopUp = new TopUp("2", "TEN_DEVICE",10,100);
        //Act
        TopUp actualTopUp = topupRepository.save(topup2);
        //Assert
        Assertions.assertEquals(expectedTopUp,actualTopUp);
    }


    @Test
    @DisplayName("findAll method should return All Topup")
    public void findAll_ShouldReturnAllTopups(){
        //Arrange
        int expectedCount = 1;
        //Act
        List<TopUp> actualTopups = topupRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedCount,actualTopups.size());
    }

    @Test
    @DisplayName("findAll method should return Empty List if No Topups Found")
    public void findAll_ShouldReturnEmptyList(){
        //Arrange
        int expectedCount = 0;
        TopupRepository emptyTopupRepository = new TopupRepository();
        //Act
        List<TopUp> actualTopups = emptyTopupRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedCount,actualTopups.size());
    }


    @Test
    @DisplayName("findById method should return Topup Given Id")
    public void findById_ShouldReturnTopup_GivenTopupId(){
        //Arrange
        String expectedTopupId = "1";
        //Act
        Optional<TopUp> actualTopup = topupRepository.findById(expectedTopupId);
        //Assert
        Assertions.assertEquals(expectedTopupId,actualTopup.get().fetchId());
    }

    @Test
    @DisplayName("findById method should return empty if Topup Not Found")
    public void findById_ShouldReturnEmptyIfTopupNotFound(){
        //Arrange
        Optional<TopUp> expected = Optional.empty();
        //Act
        Optional<TopUp> actual = topupRepository.findById("2");
        //Assert
        Assertions.assertEquals(expected,actual);
    }

    @Test
    @DisplayName("Delete Method Should Delete Topup From Repository")
    public void delete_ShouldDeleteTopupFromRepository(){
        //Arrange
        int expectedCount = 0;
        //Act
        topupRepository.deleteById("1");
        Long actualCount = topupRepository.count();

        //Assert
        Assertions.assertEquals(expectedCount,actualCount);
    }
}
