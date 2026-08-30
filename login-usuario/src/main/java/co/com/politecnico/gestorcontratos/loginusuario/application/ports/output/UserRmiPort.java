package co.com.politecnico.gestorcontratos.loginusuario.application.ports.output;

import java.rmi.Remote;
import java.rmi.RemoteException;

import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rmi.dto.RmiLoginRequest;

public interface UserRmiPort extends Remote {
    boolean login(RmiLoginRequest user) throws RemoteException;
}
