import java.util.ArrayList;

public class FoodQueue {
     private ArrayList<Customer> customerList = new ArrayList<Customer>();
     private final int maxQueueSize;
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

     public ArrayList<String> getCustomerNames() {
          ArrayList<String> names = new ArrayList<String>();
          for (int i = 0; i < customerList.size(); i++) {
               names.add(customerList.get(i).getFullName());
          }
          return names;
     }
     
     public void addCustomer(Customer customer) {
               customerList.add(customer);
     }

     public boolean isQueueEmpty() {
          return customerList.size() < maxQueueSize;
     }

     public void removeCustomer(int customerPosition) {
          customerList.remove(customerPosition);
     }

     public void addQueueIncome(int queueIncome) {
          this.queueIncome += queueIncome;
     }

     public int getQueueIncome() {
          return queueIncome;
     }

     public Customer getCustomer(int i) {
          return customerList.get(i);
     }

     
}