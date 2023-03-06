package com.geektrust.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import com.geektrust.backend.appConfig.AppConfiguration;
import com.geektrust.backend.commands.CommandInvoker;

public class App {

	public static void main(String[] args) {
		List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
        run(commandLineArgs);
	}

	static void run(List<String> commandLineArgs) {
		AppConfiguration conf = AppConfiguration.getInstance();
	
		CommandInvoker commandInvoker = conf.getCommandInvoker();
		
		// commandLineArgs = ["sample_input/input1.txt"]
		String inputFile = commandLineArgs.get(0);
		 
		try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line = reader.readLine();
            while(line!=null){
                commandInvoker.invokeCommand(line);
                line = reader.readLine();
            }
        }
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
