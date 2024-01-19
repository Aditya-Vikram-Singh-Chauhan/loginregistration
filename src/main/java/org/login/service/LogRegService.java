package org.login.service;

import org.login.entity.LogReg;
import java.util.List;

public interface LogRegService {

    LogReg saveUser(LogReg lr);
    List<LogReg> checkUser(String username, String emailID, Long number);
    List<LogReg> getUserByUsernamePassword(String user, String pass);

    LogReg getVerificationDetails(String username);

    void sendOTPByEmail(String email, Long otp);

    void sendOTPBySMS(String phoneNumber, Long otp);
}
