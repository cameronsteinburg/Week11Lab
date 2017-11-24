/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic;

import dataaccess.NotesDBException;
import dataaccess.UserDB;
import domainmodel.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author awarsyle
 */
public class AccountService {

    public int resetPassword(String email, String path, String url) throws NotesDBException {

        String uuid = UUID.randomUUID().toString();

        UserDB db = new UserDB();

       // List<User> users = db.getAll();
        
        UserService us = new UserService();
        User user = us.getByEmail(email);
        
        if (user != null) {
            //insert password change request
            //ResetPasswordService rps = new ResetPasswordService();
            //rps.insert(uuid, user.getUsername());
            
            user.setUUID(uuid);
            db.update(user);
            
            String link = url + "?uuid=" + uuid;
            try {
                HashMap<String, String> contents = new HashMap<>();
                contents.put("firstname", user.getFirstname());
                contents.put("lastname", user.getLastname());
                contents.put("username", user.getUsername()); 
                contents.put("link", link); 

                try {
                    WebMailService.sendMail(email, "Password Reset Request", path , contents);
                } catch (IOException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (MessagingException ex) {
                Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NamingException ex) {
                Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //OK, email sent
            return 1;
        }
        else {
            //email not found in Database
            return 2;
        }

        /*for (int i = 0; i < users.size(); i++) { //find the user with the matching email
            
            if (users.get(i).getEmail() == email) {
                user = users.get(i);
                user.setUUID(uuid);
                db.update(user);

                String link = url + "?uuid=" + uuid;

                HashMap<String, String> contents = new HashMap<>();
                contents.put("username", user.getUsername());
                contents.put("password", user.getPassword());
                contents.put("firstname", user.getFirstname());
                contents.put("lastname", user.getLastname());
                contents.put("link", link);
                
                try {
                    WebMailService.sendMail(email, "Noteskeepr Forgot Password", path, contents);
                } catch (Exception e) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, e);
                } */
            //}          
       // }
    }

    public User checkLogin(String username, String password, String path) {
        User user;

        UserDB userDB = new UserDB();
        try {
            user = userDB.getUser(username);

            if (user.getPassword().equals(password)) {
                // successful login
                Logger.getLogger(AccountService.class.getName())
                        .log(Level.INFO,
                                "A user logged in: {0}", username);
                String email = user.getEmail();
                try {
                    // WebMailService.sendMail(email, "NotesKeepr Login", "Big brother is watching you!  Hi " + user.getFirstname(), false);

                    HashMap<String, String> contents = new HashMap<>();
                    contents.put("firstname", user.getFirstname());
                    contents.put("date", ((new java.util.Date()).toString()));

                    try {
                        WebMailService.sendMail(email, "NotesKeepr Login", path + "/emailtemplates/login.html", contents);
                    } catch (IOException ex) {
                        Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (MessagingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NamingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                }

                return user;
            }

        } catch (NotesDBException ex) {
        }

        return null;
    }
    
    public boolean changePassword(String uuid, String password) {
        UserDB db = new UserDB();
        try {
            User user = db.getByUUID(uuid);
            user.setPassword(password);
            user.setUUID(null);
            UserDB ur = new UserDB ();
            ur.update(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


}
