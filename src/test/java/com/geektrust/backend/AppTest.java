package com.geektrust.backend;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("App Test")
class AppTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @BeforeEach
    public void preTestSetup(){
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Integration Test")
    void forGivenInputcommandsShouldPrintExpectedOutput(){

        //Arrange
        List<String> commnadLineArgs = new ArrayList<>(List.of("input1.txt"));
        String expectedOutput = "INVALID_DATE\n" +
        "ADD_SUBSCRIPTION_FAILED INVALID_DATE\n" +
        "SUBSCRIPTIONS_NOT_FOUND\n" +
        "ADD_SUBSCRIPTION_FAILED DUPLICATE_CATEGORY\n"+
        "ADD_TOPUP_FAILED DUPLICATE_TOPUP\n"+
        "RENEWAL_REMINDER MUSIC 10-03-2022\n"+
        "RENEWAL_REMINDER VIDEO 10-05-2022\n"+
        "RENEWAL_REMINDER PODCAST 10-03-2022\n"+
        "RENEWAL_AMOUNT 800";

        //Act
        App.run(commnadLineArgs);

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown(){
        System.setOut(standardOut);
    }

}

