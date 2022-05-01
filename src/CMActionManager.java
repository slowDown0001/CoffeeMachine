/**
 * A simple action manager for the Coffee Machine project from JetBrains Academy.
 * Some logic may not be very efficient, however it works as intended!
 * CMActionManager class talks to CoffeeMachine class via the method setStatus().
 * All status are listed in enum CMStatus.
 */

import java.util.Scanner;
public class CMActionManager {
    CoffeeMachine cm;

    public CMActionManager(CoffeeMachine cm){
        this.cm = cm;
    }

    public void fill(int water, int milk, int beans, int cups){

        cm.setWater(water);
        cm.setMilk(milk);
        cm.setBeans(beans);
        cm.setCups(cups);

    }

    public void action(){

        Scanner scanner = new Scanner(System.in);
        String action = " ";

        while(!action.equals("exit")){
            System.out.println("Write action (buy, fill, take, remaining, exit): ");
            action = scanner.next();
            System.out.println();
            cm.setStatus(action);

            if(cm.getStatus() == CoffeeMachine.CMStatus.PENDING){
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
                action = scanner.next();
                cm.setStatus(action);

                if(cm.getStatus() == CoffeeMachine.CMStatus.REQUEST) {
                    System.out.println();
                    continue;
                }

                doStuff();

            }

            if(cm.getStatus() == CoffeeMachine.CMStatus.FILL){
                System.out.println("Write how many ml of water you want to add: ");
                int water = scanner.nextInt();
                System.out.println("Write how many ml of milk you want to add ");
                int milk = scanner.nextInt();
                System.out.println("Write how many grams of coffee beans you want to add: ");
                int beans = scanner.nextInt();
                System.out.println("Write how many disposable cups of coffee you want to add: ");
                int cups = scanner.nextInt();
                fill(water, milk, beans, cups);
                System.out.println();
            }
            else if(cm.getStatus() == CoffeeMachine.CMStatus.WITHDRAW){
                cm.withdraw();
                System.out.println();
            }
            else if(cm.getStatus() == CoffeeMachine.CMStatus.INFO){
                cm.getInfo();
                System.out.println();
            }
            else if(cm.getStatus() == CoffeeMachine.CMStatus.EXIT)
                break;

        }
    }

    public void doStuff(){

        if(cm.getStatus() ==  CoffeeMachine.CMStatus.MAKING_ESPRESSO){
            cm.setWater(-250);
            cm.setBeans(-16);
            cm.setMoney(4);
            cm.setCups(-1);
        }
        else if(cm.getStatus() ==  CoffeeMachine.CMStatus.MAKING_LATTE){
            cm.setWater(-350);
            cm.setMilk(-75);
            cm.setBeans(-20);
            cm.setMoney(7);
            cm.setCups(-1);
        }
        else if(cm.getStatus() ==  CoffeeMachine.CMStatus.MAKING_CAPPUCCINO){
            cm.setWater(-200);
            cm.setMilk(-100);
            cm.setBeans(-12);
            cm.setMoney(6);
            cm.setCups(-1);
        }

        if(cm.getStatus() != CoffeeMachine.CMStatus.REQUEST){
            if(cm.getWater() >= 0 && cm.getBeans() >= 0 && cm.getMilk() >= 0 && cm.getCups() >= 0)
                System.out.println("I have enough resources, making you a coffee!\n");
            else{
                if(cm.getWater() < 0)
                    System.out.println("Sorry, not enough water!\n");
                if(cm.getMilk() < 0)
                    System.out.println("Sorry, not enough milk!\n");
                if(cm.getBeans() < 0)
                    System.out.println("Sorry, not enough beans!\n");
                if(cm.getCups() < 0)
                    System.out.println("Sorry, not enough cups!\n");
                switch (cm.getStatus()) {
                    case MAKING_ESPRESSO -> {
                        cm.setWater(250);
                        cm.setBeans(16);
                        cm.setMoney(-4);
                    }
                    case MAKING_LATTE -> {
                        cm.setWater(350);
                        cm.setMilk(75);
                        cm.setBeans(20);
                        cm.setMoney(-7);
                    }
                    case MAKING_CAPPUCCINO -> {
                        cm.setWater(200);
                        cm.setMilk(100);
                        cm.setBeans(12);
                        cm.setMoney(-6);
                    }
                }
                cm.setCups(1);
            }
        }

    }

    public static void main(String[] args) {
        CoffeeMachine cm = new CoffeeMachine();
        CMActionManager actionManager = new CMActionManager(cm);
        actionManager.action();
    }


}

class CoffeeMachine{

    private CMStatus cmstatus;
    public int water, milk, beans, cups, money;

    public enum CMStatus {
        REQUEST,
        PENDING,
        MAKING_ESPRESSO,
        MAKING_LATTE,
        MAKING_CAPPUCCINO,
        FILL,
        WITHDRAW,
        INFO,
        EXIT
    }

    public CoffeeMachine(){
        water = 400;
        milk = 540;
        beans = 120;
        cups = 9;
        money = 550;
    }

    public CMStatus getStatus(){
        return cmstatus;
    }

    public void setStatus(String action){

        switch (action) {
            case "buy" -> cmstatus = CMStatus.PENDING;
            case "back" -> cmstatus = CMStatus.REQUEST;
            case "1" -> cmstatus = CMStatus.MAKING_ESPRESSO;
            case "2" -> cmstatus = CMStatus.MAKING_LATTE;
            case "3" -> cmstatus = CMStatus.MAKING_CAPPUCCINO;
            case "fill" -> cmstatus = CMStatus.FILL;
            case "take" -> cmstatus = CMStatus.WITHDRAW;
            case "remaining" -> cmstatus = CMStatus.INFO;
            case "exit" -> cmstatus = CMStatus.EXIT;
        }
    }

    public void withdraw(){
        System.out.printf("I gave you $%d%n", money);
        money = 0;
    }

    public void getInfo(){
        System.out.println("The coffee machine has: ");
        System.out.printf("%d ml of water%n", water);
        System.out.printf("%d ml of milk%n", milk);
        System.out.printf("%d g of coffee beans%n", beans);
        System.out.printf("%d disposable cups%n", cups);
        System.out.printf("$%d of money%n", money);
    }

    public int getWater(){return water;}
    public int getMilk(){return milk;}
    public int getBeans(){return beans;}
    public int getCups(){return cups;}
    //public int getMoney(){return money;}
    public void setWater(int w){water += w;}
    public void setMilk(int m){milk += m;}
    public void setBeans(int b){beans += b;}
    public void setCups(int c){cups += c;}
    public void setMoney(int m){money += m;}
}


