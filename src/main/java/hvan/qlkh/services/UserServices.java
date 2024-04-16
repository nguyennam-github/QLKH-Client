/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hvan.qlkh.services;

import hvan.qlkh.dao.UserDAO;
import hvan.qlkh.models.User;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author PC
 */
public class UserServices {

    private static UserServices instance;
    private static User current;
    private static User selectedUser;

    private UserServices() {
    }

    public static synchronized UserServices getInstance(){
        if(instance == null){
            instance = new UserServices();
        }
        return instance;
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        UserServices.current = current;
    }

    public List<User> getUsers(){
        return UserDAO.getInstance().readUsers();
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        UserServices.selectedUser = selectedUser;
    }

    public User findByName(String username){
        return getUsers().stream()
            .filter(temp -> username.equals(temp.getUsername()))
            .findAny()
            .orElse(null);
    }
    
    public List<User> filter(String username, Date dateMin, Date dateMax, boolean read, boolean write){
        List<User> temp = getUsers();
        if (!username.equals("")){
            temp = temp.stream()
                .filter(user -> user.getUsername().toLowerCase().contains(username.toLowerCase().trim()))
                .collect(Collectors.toList());
        }
        if (dateMin != null && dateMax != null){
            dateMax.setHours(23);
            dateMax.setMinutes(59);
            dateMax.setSeconds(59);
            temp = temp.stream()
                    .filter(user -> user.getRegister().compareTo(dateMin) >= 0)
                    .filter(user -> user.getRegister().compareTo(dateMax) <= 0)
                    .collect(Collectors.toList());
        }
        return temp.stream()
                .filter(user -> user.isRead() == read)
                .filter(user -> user.isWrite() == write)
                .collect(Collectors.toList());
    }
}
