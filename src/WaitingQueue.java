import java.util.NoSuchElementException;
import java.util.ArrayList;

public class WaitingQueue {

    private int front,rear;
    public Customer[] queue;

    public WaitingQueue(int initialSize){
        this.front = this.rear = -1;
        this.queue = new Customer[initialSize];
    }

    public ArrayList<Customer> getCustomerList() {
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        if (!isEmpty()) {
            int j = front;

            do {
                if (queue[j] != null){
                customerList.add(queue[j]);
                }
                j = (j+1) % queue.length;
            } while (j != front);
        }
        return customerList;
    }

    public void reset() {
        this.front = this.rear = -1;
        this.queue = new Customer[queue.length];
    }

    public int size() {
        if (isEmpty()) {
            return 0;
        }
        return (queue.length + rear - front) % queue.length + 1;
    }

    public void enqueue (Customer customer) {
        if(isFull()) {
            System.out.println("Error: Queue is full");
        } else {
            if (isEmpty()) {
                front++;
            }
            rear = (rear + 1) % queue.length;
            queue[rear] = customer;
        }
    }

    public Customer dequeue() {
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        Customer temp = queue[front];
        if(front == rear){
            front = rear = -1;
        }
        else  {
            front = (front+1) % queue.length;
        }
        return temp;
    }

    public Customer peek() {
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        return queue[front];
    }

    public boolean isEmpty() {
        return front == -1;
    }

    public boolean isFull() {

        return (rear + 1) % queue.length == front;
    }

    
}
