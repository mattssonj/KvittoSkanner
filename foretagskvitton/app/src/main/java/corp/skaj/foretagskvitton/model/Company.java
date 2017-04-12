package corp.skaj.foretagskvitton.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import corp.skaj.foretagskvitton.exceptions.IllegalInputException;
import corp.skaj.foretagskvitton.exceptions.NoSuchEmployeeException;

/**
 * Created by annekeller on 2017-04-05.
 */

public class Company {
    private String companyName;
    private List<Employee> listOfEmployees;
    private List<Card> listOfCards;
    private List<Comment> listOfComments;
    private List<Supplier> listOfSuppliers;

    public Company (String companyName, List<Employee> listOfEmployees, List<Card> listOfCards, List<Comment> listOfComments, List<Supplier> listOfSuppliers) {
        this.companyName = companyName;
        this.listOfEmployees = listOfEmployees;
        this.listOfCards = listOfCards;
        this.listOfComments = listOfComments;
        this.listOfSuppliers = listOfSuppliers;
    }

    public Company (String companyName) {
        this.companyName = companyName;
        listOfEmployees = new ArrayList<>();
        listOfCards = new ArrayList<>();
        listOfComments = new ArrayList<>();
        listOfSuppliers = new ArrayList<>();
    }

    public void addNewEmployee (String name) throws IllegalInputException{
        if (containsEmployee(name)) {
            listOfEmployees.add(new Employee(name));
        } else {
            throw new IllegalInputException(this);
        }
    }
    
    public void addNewEmployee (Employee employee) throws IllegalInputException {
        if(containsEmployee(employee)) {
            listOfEmployees.add(employee);
        } else {
            throw new IllegalInputException(this);
        }
    }

    public void removeEmployee (String name) throws NoSuchEmployeeException{
        for (int i = 0; i < listOfEmployees.size(); i++) {
            Employee temp = listOfEmployees.get(i);
            if (temp.getName().equals(name)) {
                listOfEmployees.remove(i);
            } else {
                throw new NoSuchEmployeeException();
            }
        }
    }
    public void removeEmployee (Employee employee) throws NoSuchEmployeeException{
        for (int i = 0; i < listOfEmployees.size(); i++) {
            Employee temp = listOfEmployees.get(i);
            if (temp.equals(employee)) {
                listOfEmployees.remove(i);
            } else {
                throw new NoSuchEmployeeException();
            }
        }
    }

    public boolean containsEmployee(Employee employee) {
        for (int i = 0; i < listOfEmployees.size(); i++) {
            if (listOfEmployees.get(i).equals(employee)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsEmployee(String name) {
        for (int i = 0; i < listOfEmployees.size(); i++) {
            if(listOfEmployees.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    //Kolla så att vi inte lägger till dubletter
    public void addNewCard (int cardNumber) {

    }

    //Kolla så att vi inte tar bort något som inte finns i listan
    public void removeCard (Card card) {

    }

    //Kolla så att vi inte lägger till dubletter
    public void addSupplier (String supplierName) {

    }

    //Kolla så att vi inte tar bort något som inte finns i listan
    public void removeSupplier (String supplierName) {

    }

    public String getName () {
        return companyName;
    }

    public List<Employee> getListOfEmployees() {
        return listOfEmployees;
    }

    public List<Card> getListOfCards() {
        return listOfCards;
    }

    public List<Comment> getCompanyComment () {
        return listOfComments;
    }

    public List<Supplier> getListOfSuppliers () {
        return listOfSuppliers;
    }
}