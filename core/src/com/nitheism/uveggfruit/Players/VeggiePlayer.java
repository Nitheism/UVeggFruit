package com.nitheism.uveggfruit.Players;

//defining the vegetable player parameters
public class VeggiePlayer {

    private int health = 500;
    private int money = 70;
    private int points = 0;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health -= health;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money += money;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points += points;
    }
}
