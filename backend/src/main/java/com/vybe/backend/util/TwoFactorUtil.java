package com.vybe.backend.util;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwoFactorUtil {
    @Value("${two-factor-auth.account-sid}")
    private String ACCOUNT_SID = "";
    @Value("${two-factor-auth.auth-token}")
    public String AUTH_TOKEN;
    @Value("${two-factor-auth.service-id}")
    public String SERVICE_ID;
    @Value("${two-factor-auth.channel}")
    public String CHANNEL;

    public void sendVerificationToken(String number) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Verification verification = Verification.creator(
                        SERVICE_ID,
                        number,
                        CHANNEL)
                .create();
    }

    public boolean verifyToken(String number, String token) {
        System.out.println("Account SID: " + ACCOUNT_SID);
        System.out.println("Auth Token: " + AUTH_TOKEN);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        VerificationCheck verificationCheck = VerificationCheck.creator(
                        SERVICE_ID)
                .setTo(number)
                .setCode(token)
                .create();
        System.out.println(verificationCheck.getStatus());
        return verificationCheck.getStatus().equals("approved");

    }
}
