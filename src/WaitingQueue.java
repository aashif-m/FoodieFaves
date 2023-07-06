import java.util.NoSuchElementException;

public class WaitingQueue {

    private int front,rear;
    private Customer[] queue;

    public WaitingQueue(int initialSize){
        this.front = this.rear = -1;
        this.queue = new Customer[initialSize];
    }



    public void enqueue (Customer customer) {
        if(isFull()) {
            resize();
        } else if (isEmpty()) {
            front++;
        }
        rear = (rear + 1) % queue.length;
        queue[rear] = customer;
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

    private void resize() {
        Customer[] tempArr = new Customer[queue.length*2];
        int i = 0;
        int j = front;

        do {
            tempArr[i++] = queue[j];
            j = (j+1) % queue.length;
        } while (j != front);

        front = 0;
        rear = queue.length-1;
        queue = tempArr;
    }



}
