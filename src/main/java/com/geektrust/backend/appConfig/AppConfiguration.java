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
import com.geektrust.backend.services.IPrintService;
import com.geektrust.backend.services.ISubscriptionService;
import com.geektrust.backend.services.ITopupService;
import com.geektrust.backend.services.IUserService;
import com.geektrust.backend.services.PlanService;
import com.geektrust.backend.services.PrintService;
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

    /*  Constants */
    private final int MUSIC_FREE_COST=0;
    private final int MUSIC_PERSONAL_COST=100;
    private final int MUSIC_PREMIUM_COST=250;
    private final int VIDEO_FREE_COST=0;
    private final int VIDEO_PERSONAL_COST=200;
    private final int VIDEO_PREMIUM_COST=500;
    private final int PODCAST_FREE_COST=0;
    private final int PODCAST_PERSONAL_COST=100;
    private final int PODCAST_PREMIUM_COST=300;
    private final int FREE_DURATION=1;
    private final int PERSONAL_DURATION=1;
    private final int PREMIUM_DURATION=3;
    private final int FOUR_DEVICE_TOPUP_NUMBER_OF_DEVICES=4;
    private final int FOUR_DEVICE_TOPUP_COST=50;
    private final int TEN_DEVICE_TOPUP_NUMBER_OF_DEVICES=10;
    private final int TEN_DEVICE_TOPUP_COST=100;
   

    private final IUserRepository userRepository = new UserRepository();
    private final ISubscriptionRepository subscriptionRepository = new SubscriptionRepository();
    private final IPlanRepository planRepository = new PlanRepository();
    private final ITopupRepository topupRepository = new TopupRepository();

    private final IPlanService planService = new PlanService(planRepository);
    private final ISubscriptionService subscriptionService = new SubscriptionService(subscriptionRepository,planService,userRepository);
    private final ITopupService topupService = new TopupService(topupRepository,userRepository);
    private final IUserService userService = new UserService(userRepository,subscriptionService,topupService);
    private final IPrintService printService = new PrintService(userRepository, subscriptionService);

    /* Adding all the predefined Plans to the database */
    Plan plan1= planService.addPlan("MUSIC","FREE", MUSIC_FREE_COST,FREE_DURATION);
    Plan plan2 = planService.addPlan("MUSIC","PERSONAL", MUSIC_PERSONAL_COST,PERSONAL_DURATION);
    Plan plan3 = planService.addPlan("MUSIC","PREMIUM", MUSIC_PREMIUM_COST,PREMIUM_DURATION);
    Plan plan4 = planService.addPlan("VIDEO","FREE", VIDEO_FREE_COST,FREE_DURATION);
    Plan plan5 = planService.addPlan("VIDEO","PERSONAL", VIDEO_PERSONAL_COST,PERSONAL_DURATION);
    Plan plan6 = planService.addPlan("VIDEO","PREMIUM", VIDEO_PREMIUM_COST,PREMIUM_DURATION);
    Plan plan7 = planService.addPlan("PODCAST","FREE", PODCAST_FREE_COST,FREE_DURATION);
    Plan plan8 = planService.addPlan("PODCAST","PERSONAL", PODCAST_PERSONAL_COST,PERSONAL_DURATION);
    Plan plan9 = planService.addPlan("PODCAST","PREMIUM", PODCAST_PREMIUM_COST,PREMIUM_DURATION);


    /* Adding all the predefined topups to the database */
    TopUp topUp1 = topupService.addTopup("FOUR_DEVICE", FOUR_DEVICE_TOPUP_NUMBER_OF_DEVICES, FOUR_DEVICE_TOPUP_COST);
    TopUp topUp2 = topupService.addTopup("TEN_DEVICE", TEN_DEVICE_TOPUP_NUMBER_OF_DEVICES, TEN_DEVICE_TOPUP_COST);
    
    
    private final ICommand startSubcriptionCommand = new StartSubcriptionCommand(userService);
    private final ICommand addSubscriptionCommand = new AddSubscriptionCommand(subscriptionService);
    private final ICommand addTopupCommand = new AddTopupCommand(topupService);
    private final ICommand printRenewalDatesCommand = new PrintRenewalDatesCommand(printService);

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
