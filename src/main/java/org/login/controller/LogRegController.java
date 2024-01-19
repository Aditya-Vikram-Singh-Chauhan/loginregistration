package org.login.controller;

import org.login.entity.LogReg;
import org.login.entity.OTPRequest;
import org.login.service.LogRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LogRegController {

    @Autowired
    private LogRegService lrs;

    @RequestMapping("/home")
    public ModelAndView showPage() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    //load Login page
    @RequestMapping("/loginpage")
    public ModelAndView showLoginPage() {
        ModelAndView mv = new ModelAndView("loginpage");
        return mv;
    }

    //load Registeration page
    @RequestMapping("/registerationpage")
    public ModelAndView showRegPage() {
        ModelAndView mv = new ModelAndView("registerationpage");
        return mv;
    }

    //load Landing page
    @RequestMapping("/landingpage")
    public ModelAndView showLandingPage() {
        ModelAndView mv = new ModelAndView("landingpage");
        return mv;
    }

    //Add new user
    @PostMapping("/registerNewUser")
    public String saveUser(
            @RequestParam Long contact_num,
            @RequestParam String emailID,
            @RequestParam String name,
            @RequestParam String username,
            @RequestParam String password) {

        Long id = (long) 0;
        if(lrs.checkUser(username,emailID,contact_num).isEmpty()) {
            System.out.println(contact_num+", "+emailID+", "+name+", "+username+", "+password);
            LogReg lr = new LogReg(id, contact_num, emailID, name, username, password);
            lrs.saveUser(lr);
            return "User Successfully Added!";
        }
        return null;
    }

    //Login
    @PostMapping("/Login")
    public String checkUser(
            @RequestParam String username,
            @RequestParam String password) {
        System.out.println(username+" AND "+password);
        if(!lrs.getUserByUsernamePassword(username, password).isEmpty()) {
            System.out.println(lrs.getUserByUsernamePassword(username, password));
            System.out.println("User Logged In!!");
            try {
                // Create /tmp folder if it doesn't exist
                File tmpFolder = new File("/tmp");
                if (!tmpFolder.exists()) {
                    tmpFolder.mkdirs();
                }

                // Define the path to the file you want to create in /tmp
                String filePath = "/tmp/"+username+"Login.txt"; // Replace with the desired file name and extension

                // Create the file and write some content to it
                Path path = Paths.get(filePath);
                Files.write(path, "Hello, World!".getBytes(), StandardOpenOption.CREATE);

                System.out.println("Login Successful. File created.");
            } catch (IOException e) {
                // Handle IO exceptions
                e.printStackTrace();
                System.out.println("An error occurred while creating the file.");
            }
            return "Login Successful";
        }
        return null;
    }
    @GetMapping("/getUserInfo")
    public LogReg getUserInfo(@RequestParam String username) {
        LogReg user = lrs.getVerificationDetails(username);

        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    @PostMapping("/deleteFile")
    public String deleteFile(@RequestParam String username) {
        try {
            // Define the path to the file you want to delete in the /tmp directory
            String filePath = "/tmp/" + username + "Login.txt"; // Replace with the actual file name and extension

            // Create a File object
            File file = new File(filePath);

            // Check if the file exists
            if (file.exists()) {
                // Attempt to delete the file
                if (file.delete()) {
                    // File deleted successfully
                    return "File deleted successfully.";
                } else {
                    // Failed to delete the file
                    return "Failed to delete the file.";
                }
            } else {
                // File does not exist
                return "File not found.";
            }
        } catch (Exception e) {
            // Handle exceptions here
            e.printStackTrace();
            return "An error occurred: " + e.getMessage();
        }
    }
    @GetMapping("/listFiles")
    public List<String> listFiles() {
        List<String> fileNames = new ArrayList<>();

        // Directory path for the /tmp directory
        String directoryPath = "/tmp/";

        // Create a File object for the directory
        File directory = new File(directoryPath);

        // Check if the directory exists and is a directory
        if (directory.exists() && directory.isDirectory()) {
            // List all files in the directory
            File[] files = directory.listFiles();

            // Iterate through the files and add their names to the list
            for (File file : files) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        }

        return fileNames;
    }

    @PostMapping("/sendOTPByEmail")
    public void sendOTPByEmail(@RequestBody OTPRequest otpRequest) {
        // Call your JavaMail service to send OTP by email
        lrs.sendOTPByEmail(otpRequest.getEmail(), otpRequest.getOtp());
    }

    @PostMapping("/sendOTPBySMS")
    public void sendOTPBySMS(@RequestBody OTPRequest otpRequest) {
        // Call your Twilio service to send OTP by SMS
        lrs.sendOTPBySMS(otpRequest.getPhoneNumber(), otpRequest.getOtp());
    }

}
