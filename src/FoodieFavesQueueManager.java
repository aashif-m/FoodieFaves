import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class FoodieFavesQueueManager {
    public final static int[] QUEUE_SIZES = {2, 3, 5};
    public final static int NUMBER_OF_QUEUES = QUEUE_SIZES.length;
    public final static int BURGER_PRICE = 650;
    public static FoodQueue[] queues = new FoodQueue[NUMBER_OF_QUEUES];
    public static WaitingQueue waitingQueue = new WaitingQueue(10);
    public static int burgerStock = 50;
    public final static String SAVE_FILE_NAME = "data.txt";
    public static void main(String[] args){
        setupQueues();
        boolean exit = false;

        while(!exit){
            printMenu();

            Scanner input = new Scanner(System.in);
            System.out.print("Enter your choice: ");
            String choice = input.nextLine();
            choice = choice.toUpperCase();

            switch (choice) {
                case "100", "VFQ" -> viewAllQueues(queues);
                case "101", "VEQ" -> viewEmptyQueues(queues);
                case "102", "ACQ" -> addCustomer(input);
                case "103", "RCQ" -> removeCustomerLocation(input);
                case "104", "PCQ" -> removeServedCustomer(input);
                case "105", "VCS" -> viewSortedCustomerNames();
                case "106", "SPD" -> storeProgramData(SAVE_FILE_NAME, queues, burgerStock, waitingQueue);
                case "107", "LPD" -> loadProgramData(SAVE_FILE_NAME, queues, waitingQueue);
                case "108", "STK" -> viewRemainingBurgerStock();
                case "109", "AFS" -> addBurgerStock(input);
                case "110", "IFQ" -> viewQueueIncome(queues);
                case "999", "EXT" -> {
                    exit = true;
                    System.out.println("Exiting Program");
                    input.close();
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public static void setupQueues(){
        for(int i = 0; i < queues.length; i++){
            queues[i] = new FoodQueue(QUEUE_SIZES[i]);
        }
    }
    public static void printMenu(){
        System.out.print("""
            
                100 or VFQ: View all Queues.
                101 or VEQ: View all Empty Queues.
                102 or ACQ: Add customer to a Queue.
                103 or RCQ: Remove a customer from a Queue.
                104 or PCQ: Remove a served customer.
                105 or VCS: View Customers Sorted in alphabetical order
                106 or SPD: Store Program Data into file.
                107 or LPD: Load Program Data from file.
                108 or STK: View Remaining burgers Stock.
                109 or AFS: Add burgers to Stock.
                110 or IFQ: View Income of Each Queue
                999 or EXT: Exit the Program

                """);
    }

    public static int getMaxValue(int[] array){
        int maxValue = 0;
        for(int value : array){
            if(value > maxValue){
                maxValue = value;
            }
        }
        return maxValue;
    }
    
    public static void viewAllQueues(FoodQueue[] queues){
        int maxQueueSize = getMaxValue(QUEUE_SIZES);
        for(int i = 0; i < maxQueueSize; i++){
            for(FoodQueue queue : queues){
                if(i < queue.getQueueSize()){
                    System.out.print("O ");
                } else if(i < queue.getMaxQueueSize()){
                    System.out.print("X ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    public static void viewEmptyQueues(FoodQueue[] queues){
        int maxQueueSize = getMaxValue(QUEUE_SIZES);
        for(int i = 0; i < maxQueueSize; i++){
            for(FoodQueue queue : queues){
                if(queue.isQueueEmpty()){
                    if(i < queue.getQueueSize()){
                        System.out.print("O ");
                    } else if(i < queue.getMaxQueueSize()){
                        System.out.print("X ");
                    } else {
                        System.out.print("  ");
                    }
                } else {
                    System.out.print("  ");}
            }
            System.out.println();
        }
    }

    public static FoodQueue getShortestQueue(FoodQueue[] queues){
        FoodQueue shortestQueue = null;
        for(FoodQueue queue : queues){
            if(queue.getQueueSize() < queue.getMaxQueueSize() && (shortestQueue == null || queue.getQueueSize() < shortestQueue.getQueueSize())){
                shortestQueue = queue;
            }
        }
        return shortestQueue;
    }

    public static boolean emptyQueuesExists(FoodQueue[] queues){
        for(FoodQueue queue : queues){
            if(!queue.isQueueFull()){
                return true;
            }
        }
        return false;
    }

    public static void addCustomer(Scanner input){
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter last name: ");
        String lastName = input.nextLine();
        System.out.print("Enter number of burgers: ");
        int burgersRequired = input.nextInt();
        if(emptyQueuesExists(queues)){
            FoodQueue shortestQueue = getShortestQueue(queues);
            shortestQueue.addCustomer(new Customer(firstName, lastName, burgersRequired));
        } else {
            System.out.println("No empty queues available. Adding customer to waiting queue.");
            waitingQueue.enqueue(new Customer(firstName, lastName, burgersRequired));
        }
    }

    public static void removeCustomerLocation(Scanner input){
        System.out.print("Enter Queue Number (1-3): ");
        int queueNumber = input.nextInt();
        System.out.print("Enter Customer Position: ");
        int customerPosition = input.nextInt();
        if(queueNumber > 0 && queueNumber <= NUMBER_OF_QUEUES){
            FoodQueue queue = queues[queueNumber - 1];
            if(customerPosition > 0 && customerPosition <= queue.getQueueSize()){
                System.out.println(queue.getCustomer(customerPosition - 1).getFullName() + " has been removed");
                queue.removeCustomer(customerPosition - 1);
                if(!waitingQueue.isEmpty()){
                    Customer nextCustomer = waitingQueue.dequeue();
                    queue.addCustomer(nextCustomer);
                    System.out.println(nextCustomer.getFullName() + " from the waiting queue has been added to Queue " + queueNumber);
                }
            } else {
                System.out.println("Invalid customer position");
            }
        } else {
            System.out.println("Invalid queue number");
        }
    }

    public static void removeServedCustomer(Scanner input){
        System.out.print("Enter Queue Number (1-3): ");
        int queueNumber = input.nextInt();
        if(queueNumber > 0 && queueNumber <= NUMBER_OF_QUEUES){
            FoodQueue queue = queues[queueNumber - 1];
            if(!queue.isQueueEmpty()){
                queue.addQueueIncome(queue.getCustomer(0).getBurgersRequired() * BURGER_PRICE);
                burgerStock -= queue.getCustomer(0).getBurgersRequired();
                System.out.println(queue.getCustomer(0).getFullName() + " has been served");
                queue.removeCustomer(0);
                if(!waitingQueue.isEmpty()){
                    Customer nextCustomer = waitingQueue.dequeue();
                    queue.addCustomer(nextCustomer);
                    System.out.println(nextCustomer.getFullName() + " from the waiting queue has been added to Queue " + queueNumber);
                }
            } else {
                System.out.println("Queue is empty");
            }
        } else {
            System.out.println("Invalid queue number");
        }
    }

    public static void arrayListSort(ArrayList<String> array){
        for (int i = 0; i < array.size(); i++) {
            for (int j = i + 1; j < array.size(); j++) {
                if (array.get(i).compareToIgnoreCase(array.get(j)) > 0) {
                    String temp = array.get(i);
                    array.set(i, array.get(j));
                    array.set(j, temp);
                }
            }
        }
    }

    
    public static void viewSortedCustomerNames(){
        ArrayList<String> customers = new ArrayList<String>();
        for(FoodQueue queue : queues){
            ArrayList<String> names = queue.getCustomerNames();
            customers.addAll(names);
        }

        arrayListSort(customers);
        for(String customer : customers){
            System.out.println(customer);
        }
    }

    public static void viewRemainingBurgerStock(){
        System.out.println("Remaining Burger Stock: " + burgerStock);
    }

    public static void addBurgerStock(Scanner input){
        viewRemainingBurgerStock();
        int burgersToAdd = 0;
        boolean validInput = false;
        int maxBurgersToAdd = 50 - burgerStock; // Maximum burgers you can add
        while (!validInput) {
            try {
                System.out.println("Enter number of burgers to add (maximum " + maxBurgersToAdd + ", enter -1 to go back to menu): ");
                burgersToAdd = input.nextInt();
                if (burgersToAdd == -1) {
                    return;
                }
                if (burgersToAdd > maxBurgersToAdd) {
                    System.out.println("Invalid input. Maximum burgers you can add is " + maxBurgersToAdd);
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {

                System.out.println("Invalid input. Enter an integer");
                input.nextLine();
            }
        }
        burgerStock += burgersToAdd;
    }

    public static void viewQueueIncome(FoodQueue[] queues){
        for(int i = 0; i < queues.length; i++){
            System.out.println("Queue " + (i + 1) + " Income: " + queues[i].getQueueIncome());
        }
    }

    public static void storeProgramData(String fileName, FoodQueue[] queues, int burgerStock, WaitingQueue waitingQueue) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (FoodQueue queue : queues) {
                writer.write(queue.getCustomerList().size() + "\n");
                for (Customer customer : queue.getCustomerList()) {
                    writer.write(customer.getFirstName() + "," + customer.getLastName() + "," + customer.getBurgersRequired() + "\n");
                }
                writer.write(queue.getQueueIncome() + "\n");
                writer.write("\n");
            }
            writer.write(burgerStock + "\n");
            writer.write(waitingQueue.size() + "\n");
            for (Customer customer : waitingQueue.getCustomerList()) {
                writer.write(customer.getFirstName() + "," + customer.getLastName() + "," + customer.getBurgersRequired() + "\n");
            }
            writer.close();  
        } catch (IOException e) {
            System.out.println("An error occurred while storing program data.");
        }
    }

    public static void loadProgramData(String fileName, FoodQueue[] queues, WaitingQueue waitingQueue) {
        try {
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            for (FoodQueue queue : queues) {
                int queueSize = input.nextInt();
                input.nextLine();
                for (int i = 0; i < queueSize; i++) {
                    String[] customerData = input.nextLine().split(",");
                    String firstName = customerData[0];
                    String lastName = customerData[1];
                    int burgersRequired = Integer.parseInt(customerData[2]);
                    queue.addCustomer(new Customer(firstName, lastName, burgersRequired));
                }
                int queueIncome = input.nextInt();
                input.nextLine();
                queue.setQueueIncome(queueIncome);
                input.nextLine();

            }
            burgerStock = input.nextInt();
            input.nextLine();
            int waitingQueueSize = input.nextInt();
            input.nextLine();
            waitingQueue.reset();
            for (int i = 0; i < waitingQueueSize; i++) {
                String[] waitingQueueData = input.nextLine().split(",");
                String firstName = waitingQueueData[0];
                String lastName = waitingQueueData[1];
                int burgersRequired = Integer.parseInt(waitingQueueData[2]);
                waitingQueue.enqueue(new Customer(firstName, lastName, burgersRequired));
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }


}

