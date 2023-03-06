package com.geektrust.backend.services;

import com.geektrust.backend.entities.TopUp;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import com.geektrust.backend.Repositories.ITopupRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("TopupServiceTest")
@ExtendWith(MockitoExtension.class)
public class TopupServiceTest{
    @Mock
    private ITopupRepository topupRepositoryMock;

    @InjectMocks
    private TopupService topupService;

    @Test
    @DisplayName("Add Topup Method Should Create Topup")
    public void addTopup_ShouldReturnTopup(){
        //Arrange
        TopUp expectedTopup = new TopUp("FOUR_DEVICE",4,50);
        when(topupRepositoryMock.save(any(TopUp.class))).thenReturn(expectedTopup);
        //Act
        TopUp actualTopUp = topupService.addTopup("FOUR_DEVICE", 4,50);

        //Assert
        Assertions.assertEquals(expectedTopup,actualTopUp);
        verify(topupRepositoryMock,times(1)).save(any(TopUp.class));
    }

    @Test
    @DisplayName("Get Topup Method Should Return Topup Given Topup Name")
    public void getTopup_ShouldReturnTopupGivenTopupName(){
        //Arrange
        String topupName = "FOUR_DEVICE";
        TopUp expectedTopUp = new TopUp(topupName,4,50);
        when(topupRepositoryMock.findByname(anyString())).thenReturn(Optional.of(expectedTopUp));

        //Act
        TopUp actualTopUp = topupService.getTopUp(topupName);

        //Assert
        Assertions.assertEquals(expectedTopUp, actualTopUp);
        verify(topupRepositoryMock,times(1)).findByname(anyString());
    }
}
