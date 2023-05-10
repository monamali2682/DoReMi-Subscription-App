DoReMi is a streaming app which allows users to listen to music, podcasts and watch videos. They offer different subscription plans for different categories of services. Users can   subscribe to any of these plans. 
- A user can choose only one plan per category. 
- All plans, by default, can only be streamed on one device. 
- DoReMi allows users to add a top up to increase the number of devices that they can stream to for an additional cost.
- A user can choose only one top up.  
- The subscribed top up is applicable for all subscriptions. 
- A top up can be added only when a subscription exists.
- Renewal Reminder
  Once a user subscribes to a plan, the user needs to be notified 10 days before the plan expires. 

**Commands**
- START_SUBSCRIPTION DD-MM-YYYY 
- ADD_SUBSCRIPTION SUBSCRIPTION_CATEGORY PLAN_NAME 
- ADD_TOPUP TOP_UP_NAME NO_OF_MONTHS 
- PRINT_RENEWAL_DETAILS 
 
**SAMPLE INPUT**
START_SUBSCRIPTION20-02-2022

ADD_SUBSCRIPTIONMUSIC PERSONAL

ADD_SUBSCRIPTIONVIDEO PREMIUM

ADD_SUBSCRIPTIONPODCAST FREE

ADD_TOPUPFOUR_DEVICE 3

PRINT_RENEWAL_DETAILS

**SAMPLE OUTPUT**

RENEWAL_REMINDERMUSIC 10-03-2022

RENEWAL_REMINDERVIDEO 10-05-2022

RENEWAL_REMINDERPODCAST 10-03-2022

RENEWAL_AMOUNT750

**Error Scenarios**
 When a user adds the same category of subscription or top up twice or more, error_code should be printed. Error code should be printed when the date format is wrong.

**SAMPLE INPUT**

START_SUBSCRIPTION07-19-2022

ADD_SUBSCRIPTIONMUSIC PREMIUM

**SAMPLE INPUT**

INVALID_DATE

ADD_SUBSCRIPTION_FAILEDINVALID_DATE



# Pre-requisites
* Java 1.8/1.11/1.15
* Gradle 6
# How to run the code
Use `run.sh` if you are Linux/Unix/macOS Operating systems and `run.bat` if you are on Windows.
# How to execute the unit tests
`gradle clean test --no-daemon` will execute the unit test cases.

