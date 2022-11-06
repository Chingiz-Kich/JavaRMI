import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for a service which will be accessible remotely
 */
public interface Service extends Remote {
    Integer getMessage() throws RemoteException ;
    void sendMessage(Integer num) throws RemoteException;
    Integer pollElem() throws RemoteException;
    void addElem(Integer str) throws RemoteException;
    void storeCalculatedMessage(Integer num) throws RemoteException;
}
