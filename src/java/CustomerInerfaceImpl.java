
import java.beans.Statement;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Asus
 */
public class CustomerInerfaceImpl extends UnicastRemoteObject implements CustomerInterface{
    
    CustomerInerfaceImpl() throws RemoteException{
        
        super();
    }
    
    @Override
    public List<Customer> getCustomer() throws RemoteException{
        List<Customer> list;
        DatabaseHandler d=new DatabaseHandler();
        if(d.setConnection("shopmanagement","root",""))
             System.out.println("Successfully Connected..");
      
       list = d.testQuery("Customer");
        for (Customer s: list){ 
            System.out.println("Fname: " + s.getName()); 
            System.out.println("Femail: " + s.getEmail()); 
            System.out.println("Fpassword: " + s.getPassword()); 
            }  
           
       
       d.closeDatabase();
       return list;     
    }
    @Override
    public String sayHello(String name) throws RemoteException{
        
        return "hello "  + name;
        
    }
    @Override
    public boolean loginCheck(String email,String password) throws RemoteException{

        boolean result = false;
        DatabaseHandler d = new DatabaseHandler();
        if(d.setConnection("shopmanagement","root",""))
            System.out.println("Successfully Connected..");  
        try {
            result = d.loginMethod(email, password);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerInerfaceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
        @Override
    public boolean registerCustomer(String name,String email,String password) throws RemoteException{

        boolean result = false;
        DatabaseHandler d = new DatabaseHandler();
        if(d.setConnection("shopmanagement","root",""))
            System.out.println("Successfully Connected..");  
        result = d.registrationMethod(name,email, password);
        return result;
    }
        @Override
    public ArrayList<Product> getProduct() throws RemoteException{
        ArrayList<Product> list = new ArrayList<Product>();
        DatabaseHandler d=new DatabaseHandler();
        ResultSet result;
        if(d.setConnection("shopmanagement","root",""))
             System.out.println("Successfully Connected..");
      
        result = d.getProduct("product");
        try {
            
            ArrayList<String> colName = new ArrayList<>();
            
            
            for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                colName.add(result.getMetaData().getColumnName(i));
            }
            int cnt = 0;
            while (result.next()) {
                String ss = "",res;
                Product product = new Product();
                for (String col : colName) {
                    
                    res = result.getString(col);
                    ss += " " + res;
                    switch (cnt) {
                        case 0:
                            product.setId(Integer.parseInt(res));
                            System.out.println("Hello : " + res);
                            break;
                        case 1:
                            product.setName(res);
                            break;
                        case 2:
                            product.setCategory(res);
                            break;
                        case 3:
                            product.setQuantity(Integer.parseInt(res));
                            break;
                        case 4:
                            product.setAvailability(res);
                            break;
                        case 5:
                            product.setDetails(res);
                            break;
                        case 6:
                            product.setPicture(res);
                            break;
                        default:
                            product.setPrice(res);
                            
                            break;
                    }
                    cnt++;
                }
                cnt = 0;
            //    System.out.println("Helo " + ss);
                list.add(product);
            }
            list.stream().forEach((s) -> {
                System.out.println(s.getId());
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        d.closeDatabase();
        return list;    
    }
  
    
}
