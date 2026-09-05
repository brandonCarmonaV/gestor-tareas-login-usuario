package co.com.politecnico.gestorcontratos.loginusuario.application.ports.output;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rmi.dto.RmiLoginRequest;

public interface AuthRmiPort extends Remote {
    Map<String, String> auth(RmiLoginRequest user) throws RemoteException;
    String extractSubject(String token) throws RemoteException;
    String generateRefreshToken(String userId) throws RemoteException;
    boolean isAccessTokenValid(String token) throws RemoteException;
    boolean isRefreshTokenValid(String token) throws RemoteException;
}
