import java.rmi.Naming;

public class RMIClient {
    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8080;
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        String SERVICE_PATH = "//" + hostName + ":" + port + "/Service";

        try {
            System.setProperty(RMI_HOSTNAME, hostName);
            Service service = (Service) Naming.lookup(SERVICE_PATH);

            while (true){
                Integer num = service.getMessage();
                if(num == null) {
                    System.out.print("Not received");
                    break;
                }
                else {
                    System.out.print("Received: " + num);
                    service.storeCalculatedMessage(num);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
