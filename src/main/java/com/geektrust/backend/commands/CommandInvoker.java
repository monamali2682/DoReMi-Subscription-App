package com.geektrust.backend.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandInvoker {
    private static final Map<String, ICommand> commandMap= new HashMap<>();
    
    // Register the command into the HashMap
    public void register(String commandName, ICommand command){
        commandMap.put(commandName,command);
    }

    // Get the registered Command
    private ICommand getCommand(String commandName){
        return commandMap.get(commandName);
    }

    // Invoke the registered Command
    public void invokeCommand(String line) {
        List<String> tokens = parse(line.trim());
        ICommand command = getCommand(tokens.get(0));
        if(command == null){
            // Handle Exception
            throw new RuntimeException("INVALID COMMAND");
        } 
        command.execute(tokens);
        return;
    }


    private List<String> parse(String line){
        return Arrays.stream(line.split(" ")).collect(Collectors.toList());
    }
    
}
