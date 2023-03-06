package com.geektrust.backend.commands;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import com.geektrust.backend.exceptions.DuplicateTopupFoundException;
import com.geektrust.backend.exceptions.SubscriptionsNotFoundException;
import com.geektrust.backend.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



@DisplayName("AddTopupCommandTest")
@ExtendWith(MockitoExtension.class)
public class AddTopupCommandTest{
    private final PrintStream standardOut= System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    UserService userServiceMock;

    @InjectMocks
    AddTopupCommand addTopupCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    @Test
    @DisplayName("execute method of AddTopupCommand Should Print Error Message To Console if Subscriptions Not Found")
    public void execute_ShouldPrintErrorMessage_SubscriptionsNotFound(){
        
        //Arrange
        String topupName = "FOUR_DEVICE";
        String validityInMonths = "2";
        String expectedOutput = "ADD_TOPUP_FAILED SUBSCRIPTIONS_NOT_FOUND";
        doThrow(new SubscriptionsNotFoundException(expectedOutput)).when(userServiceMock).addTopUP(topupName, Integer.parseInt(validityInMonths) );

        //Act
        addTopupCommand.execute(List.of("ADD_TOPUP",topupName,validityInMonths));
         
        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(userServiceMock,times(1)).addTopUP(topupName, Integer.parseInt(validityInMonths));
    }


    @Test
    @DisplayName("execute method of AddTopupCommand Should Print Error Message To Console if Topup Already Exists For User")
    public void execute_ShouldPrintErrorMessage_TopupAlreadyExistsForGivenUser(){
        
       //Arrange
       String topupName = "FOUR_DEVICE";
       String validityInMonths = "2";
       String expectedOutput = "ADD_TOPUP_FAILED DUPLICATE_TOPUP";
       doThrow(new DuplicateTopupFoundException(expectedOutput)).when(userServiceMock).addTopUP(topupName, Integer.parseInt(validityInMonths) );

       //Act
       addTopupCommand.execute(List.of("ADD_TOPUP",topupName,validityInMonths));
        
       //Assert
       Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
       verify(userServiceMock,times(1)).addTopUP(topupName, Integer.parseInt(validityInMonths));
    }


    @Test
    @DisplayName("execute method of AddTopupCommand Should Print Nothing When Add Topup Successful")
    public void execute_ShouldPrintNothing_AddTopupSuccessful(){
        
       //Arrange
       String topupName = "FOUR_DEVICE";
       String validityInMonths = "2";
       String expectedOutput = "";
       when(userServiceMock.addTopUP(topupName, Integer.parseInt(validityInMonths))).thenReturn(true);

       //Act
       addTopupCommand.execute(List.of("ADD_TOPUP",topupName,validityInMonths));
        
       //Assert
       Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
       verify(userServiceMock,times(1)).addTopUP(topupName, Integer.parseInt(validityInMonths));
    }


    @AfterEach
    public void tearDown(){
        System.setOut(standardOut);
    }
    
}
