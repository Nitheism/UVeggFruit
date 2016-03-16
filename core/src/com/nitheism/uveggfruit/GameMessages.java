package com.nitheism.uveggfruit;


public class GameMessages {
    private final String welcomeMessage = "Welcome, to the world. The war between the 2 factions, fruits and" +
            " vegetables started long ago, when humans started to throw out food, and the space for living" +
            " left for those fruits and vegetables turned to be too small, so they began fighting each other." +
            " Let me introduce you to the TOMATOS this are our first warriors, you can call them by taping on the circle" +
            " with their face on it. This is intoductory level so there will be 1 to none enemies,try yourself and let's us start" +
            " the war. Tap the NEXT button to hide this dialog.";
    private final String winFirstLevel = "Congratulations!!! You won your first challange, but there is much that awaits you, and there are" +
            " new friends waiting for you on the next level, if you want to continue to the next level tap Next, if you want to quit" +
            " tap Menu";
    private final String loseFirstLevel = "Ohhh, you have been defeated, don't worry it was a small battle not the war, you can try again taping" +
            " on Menu and then play again";

    private final String welcomeMessagSecond = "Hello, brave warrior let me introduce you to our new friend the onion, he is " +
            " very strong, and can kill a pear with less then 2 hits so use it wisely. Now go and take the enemy base";
    private final String winSecondLevel = "I see fierce commander in you, our teams will greatly benefit from you. I have" +
            " an offer for you - go to the next level, there you will meet the newest fruit warriors, they are really Strong, but I " +
            " believe that you can manage";
    private final String loseSecondLevel = "Ohh, I see you need more training, don't get upset, you will be able to do it the next time." +
            " tap next to try again! ";

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public String getWinFirstLevel() {
        return winFirstLevel;
    }

    public String getLoseFirstLevel() {
        return loseFirstLevel;
    }

    public String getWelcomeMessagSecond() {
        return welcomeMessagSecond;
    }

    public String getWinSecondLevel() {
        return winSecondLevel;
    }

    public String getLoseSecondLevel() {
        return loseSecondLevel;
    }
}
