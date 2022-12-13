package com.vybe.backend.util;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Value;

public class TwoFactorUtil {
    @Value("${two-factor-auth.account-sid}")
    private static String ACCOUNT_SID;
    @Value("${two-factor-auth.auth-token}")
    public static String AUTH_TOKEN;
    @Value("${two-factor-auth.service-id}")
    public static String SERVICE_ID;
    @Value("${two-factor-auth.channel}")
    public static String CHANNEL;

    public static void sendVerificationToken(String number) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Verification verification = Verification.creator(
                        SERVICE_ID,
                        number,
                        CHANNEL)
                .create();
    }

    public static boolean verifyToken(String number, String token) {
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
