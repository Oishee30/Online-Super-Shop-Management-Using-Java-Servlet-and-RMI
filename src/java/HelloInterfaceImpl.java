/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.*;
import java.rmi.server.*;

/**
 *
 * @author Asus
 */
public class HelloInterfaceImpl extends UnicastRemoteObject implements HelloInterFace{
    
    
    HelloInterfaceImpl() throws RemoteException{
        super();
    }
    
    @Override
    public String sayHello(String name) throws RemoteException{
        
        return "Say Hello to " + name;
    }
}
