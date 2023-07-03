import java.util.Scanner;

public class FoodieFavesQueueManager {
    public final static int NUMBER_OF_QUEUES = 3;
    public final static int[] QUEUE_SIZES = {2, 3, 5};
    public static FoodQueue[] queues = new FoodQueue[NUMBER_OF_QUEUES];
    public static int burgerStock = 50;
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
//                case "102", "ACQ" -> addCustomer(queues, input);
//                case "103", "RCQ" -> removeCustomerLocation(queues, input);
//                case "104", "PCQ" -> removeServedCustomer(queues, input);
//                case "105", "VCS" -> viewSortedCustomerNames();
//                case "106", "SPD" -> storeProgramData(SAVE_FILE_NAME, queues, burgerStock);
//                case "107", "LPD" -> loadProgramData(SAVE_FILE_NAME, queues, input);
//                case "108", "STK" -> viewRemainingBurgerStock();
//                case "109", "AFS" -> addBurgerStock(input);
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
                if(!queue.isQueueEmpty()){
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

    public static void addCustomer(){

    }





}

