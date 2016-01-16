package com.nitheism.uveggfruit.Players;


public class VeggiePlayer {

    private int health = 1000;
    private int money = 30;
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
