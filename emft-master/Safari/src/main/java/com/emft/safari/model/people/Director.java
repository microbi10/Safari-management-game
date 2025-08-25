package com.emft.safari.model.people;


public class Director {
    
    private String name;
    private int money;
    private int touristInActMonth;
    
    
    public Director(String name){
        this.name = name;
        this.money = 1000;
        this.touristInActMonth = 0;
    }
    
    public String getName(){
        return name;
    }
    
    public int getMoney(){
        return money;
    }
    
    public void spendMoney(int sum){
        money -= sum;
    }

    public void addTourist(int n){
        touristInActMonth += n;
    }
    
    public void setTourist(){
        touristInActMonth = 0;
    }
    public int getTouristInLastMonth(){
        return touristInActMonth;
    }
    public void earnMoney(int sum) {
        money += sum;
    }
}
