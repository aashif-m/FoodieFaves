import java.util.ArrayList;

public class FoodQueue {
     private ArrayList<Customer> customerList = new ArrayList<Customer>();
     private int maxQueueSize;
     private int queueIncome = 0;

     public FoodQueue(int maxQueueSize) {
          this.maxQueueSize = maxQueueSize;
     }

     public int getQueueSize() {
          return customerList.size();
     }

     public int getMaxQueueSize() {
          return maxQueueSize;
     }

     public ArrayList<Customer> getCustomerList() {
          return customerList;
     }

     public String[] getCustomerNames() {
          String[] names = new String[customerList.size()];
          for (int i = 0; i < customerList.size(); i++) {
               names[i] = customerList.get(i).getFullName();
          }
          return names;
     }
     
     public void addCustomer(Customer customer) {
               customerList.add(customer);
     }

     public boolean isQueueEmpty() {
          return customerList.isEmpty();
     }

     
}