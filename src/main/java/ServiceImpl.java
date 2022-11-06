import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implementation of the remote service.
 */
public class ServiceImpl extends UnicastRemoteObject implements Service {
    private final BlockingQueue<Integer> queue;
    private final List<Integer> numbers;
    private long startTime = 0;
    boolean isProcessStarted = false;

    public ServiceImpl() throws RemoteException {
        super();
        this.queue = new LinkedBlockingQueue<>();
        this.numbers = new ArrayList<>();
    }

    @Override
    public Integer getMessage() throws RemoteException {
        if(!this.isProcessStarted) {
            startTime = System.nanoTime();
        }
        isProcessStarted = true;
        return this.queue.poll();
    }

    @Override
    public void sendMessage(Integer num) throws RemoteException {
        this.queue.add(num);
    }

    @Override
    public Integer pollElem() throws RemoteException {
        return this.queue.poll();
    }

    @Override
    public void addElem(Integer str) throws RemoteException {
        this.queue.add(str);
        System.out.println("Added: " + str);
    }

    @Override
    public void storeCalculatedMessage(Integer num) throws RemoteException {
        System.out.println("Queue: " + queue);
        if (this.isPrime(num)) numbers.add(num);
        this.process();
    }

    public void process() {
        int sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println("Sum: " + sum);
        long endTime = System.nanoTime();
        System.out.println("Time: " + (endTime - startTime));
    }

    private boolean isPrime(int num) {
        // Corner case
        if (num <= 1)
            return false;

        // Check from 2 to n-1
        for (int i = 2; i < num; i++)
            if (num % i == 0)
                return false;

        return true;
    }
}