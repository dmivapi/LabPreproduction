package com.epam.dmivapi;

public class ServiceA {
    public int getAllA() {
                System.out.println("ServiceA::getAllA invoked");
                getABySomeValue(5);
                return 25;
    }

    public void getABySomeValue(int someValue) {
        System.out.println("ServiceA::getABySomeValue invoked");
    }
}
