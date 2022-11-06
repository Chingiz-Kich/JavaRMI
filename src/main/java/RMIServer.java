import java.io.BufferedReader;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RMIServer {

    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8080;
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        List<Integer> list = new ArrayList<>();
        try {
            System.setProperty(RMI_HOSTNAME, hostName);

            // Create service for RMI
            Service service = new ServiceImpl();

            BufferedReader br = new BufferedReader(new FileReader("src/test.txt"));
            String line = br.readLine();
            while (line != null) {
                String[] numbers = line.split(" ");
                Arrays.stream(numbers).forEach(strNum -> list.add(Integer.valueOf(strNum)));
                line = br.readLine();
            }

            list.forEach(num -> {
                try {
                    service.sendMessage(num);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });

            // Init service
            String serviceName = "Service";

            System.out.println("Initializing " + serviceName);

            Registry registry = LocateRegistry.createRegistry(port);
            // Make service available for RMI
            registry.rebind(serviceName, service);

            System.out.println("Start " + serviceName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
