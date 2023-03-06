package com.geektrust.backend.appConfig;

import com.geektrust.backend.commands.AddSubscriptionCommand;
import com.geektrust.backend.commands.AddTopupCommand;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.commands.ICommand;
import com.geektrust.backend.commands.PrintRenewalDatesCommand;
import com.geektrust.backend.commands.StartSubcriptionCommand;
import com.geektrust.backend.entities.Plan;
import com.geektrust.backend.entities.TopUp;
import com.geektrust.backend.Repositories.IPlanRepository;
import com.geektrust.backend.Repositories.ISubscriptionRepository;
import com.geektrust.backend.Repositories.ITopupRepository;
import com.geektrust.backend.Repositories.IUserRepository;
import com.geektrust.backend.Repositories.PlanRepository;
import com.geektrust.backend.Repositories.SubscriptionRepository;
import com.geektrust.backend.Repositories.TopupRepository;
import com.geektrust.backend.Repositories.UserRepository;
import com.geektrust.backend.services.IPlanService;
import com.geektrust.backend.services.ISubscriptionService;
import com.geektrust.backend.services.ITopupService;
import com.geektrust.backend.services.IUserService;
import com.geektrust.backend.services.PlanService;
import com.geektrust.backend.services.SubscriptionService;
import com.geektrust.backend.services.TopupService;
import com.geektrust.backend.services.UserService;

public class AppConfiguration {

    // singleton object
    private static AppConfiguration instance= new AppConfiguration();
    private final CommandInvoker commandInvoker;

    AppConfiguration(){
        commandInvoker= new CommandInvoker();
    }

    public static AppConfiguration getInstance(){
        return instance;
    }

    private final IUserRepository userRepository = new UserRepository();
    private final ISubscriptionRepository subscriptionRepository = new SubscriptionRepository();
    private final IPlanRepository planRepository = new PlanRepository();
    private final ITopupRepository topupRepository = new TopupRepository();

    private final IPlanService planService = new PlanService(planRepository);
    private final ISubscriptionService subscriptionService = new SubscriptionService(subscriptionRepository,planService);
    private final ITopupService topupService = new TopupService(topupRepository);
    private final IUserService userService = new UserService(userRepository,subscriptionService,topupService);
    

    /* Adding all the predefined Plans to the database */
    Plan plan1= planService.addPlan("MUSIC","FREE", 0,1);
    Plan plan2 = planService.addPlan("MUSIC","PERSONAL", 100,1);
    Plan plan3 = planService.addPlan("MUSIC","PREMIUM", 250,3);
    Plan plan4 = planService.addPlan("VIDEO","FREE", 0,1);
    Plan plan5 = planService.addPlan("VIDEO","PERSONAL", 200,1);
    Plan plan6 = planService.addPlan("VIDEO","PREMIUM", 500,3);
    Plan plan7 = planService.addPlan("PODCAST","FREE", 0,1);
    Plan plan8 = planService.addPlan("PODCAST","PERSONAL", 100,1);
    Plan plan9 = planService.addPlan("PODCAST","PREMIUM", 300,3);


    /* Adding all the predefined topups to the database */
    TopUp topUp1 = topupService.addTopup("FOUR_DEVICE", 4, 50);
    TopUp topUp2 = topupService.addTopup("TEN_DEVICE", 10, 100);
    
    
    private final ICommand startSubcriptionCommand = new StartSubcriptionCommand(userService);
    private final ICommand addSubscriptionCommand = new AddSubscriptionCommand(userService);
    private final ICommand addTopupCommand = new AddTopupCommand(userService);
    private final ICommand printRenewalDatesCommand = new PrintRenewalDatesCommand(userService);

    public CommandInvoker getCommandInvoker(){
        registerCommands();
        return commandInvoker;
    }

    private void registerCommands() {
        commandInvoker.register("START_SUBSCRIPTION", startSubcriptionCommand);
        commandInvoker.register("ADD_SUBSCRIPTION", addSubscriptionCommand);
        commandInvoker.register("ADD_TOPUP", addTopupCommand);
        commandInvoker.register("PRINT_RENEWAL_DETAILS", printRenewalDatesCommand);
    }

}
