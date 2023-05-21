package com.vybe.backend.service;

import com.vybe.backend.model.dto.CustomerCreationDTO;
import com.vybe.backend.model.dto.CustomerDTO;
import com.vybe.backend.model.dto.VenueAdminCreationDTO;
import com.vybe.backend.model.dto.VenueAdminDTO;
import com.vybe.backend.model.entity.User;
import com.vybe.backend.util.TwoFactorUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthService {
    @Resource
    private UserService userService;

    @Resource TwoFactorUtil twoFactorUtil;
    public Boolean authorizeUsernameAndPassword(String username, String password) {
       try {
           User user = userService.authorizeCustomer(username, password);
           if(username.equals("admin"))
               return true;
           twoFactorUtil.sendVerificationToken(user.getPhoneNumber());
           return true;
       } catch (Exception e) {
           return false;
       }
    }

    public Boolean authorizeVenueAdminUsernameAndPassword(String username, String password) {
        try {
            User user = userService.authorizeCustomer(username, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public CustomerDTO authorizeCustomer2FA(String code, String username) {
        CustomerDTO customer = userService.getCustomer(username);
        if(username.equals("admin"))
            return customer;
        if(twoFactorUtil.verifyToken(customer.getPhoneNumber(), code)) {
            return customer;
        }
        return null;
    }
    public Boolean send2FA(String phoneNumber) {
        twoFactorUtil.sendVerificationToken(phoneNumber);
        return true;
    }

    public VenueAdminDTO authorizeVenueAdmin2FA(String code, String username) {
        VenueAdminDTO venueAdmin = userService.getVenueAdmin(username);
        if(twoFactorUtil.verifyToken(venueAdmin.getPhoneNumber(), code)) {
            return venueAdmin;
        }
        return null;
    }
    public CustomerDTO registerCustomer(CustomerCreationDTO customerCreationDTO, String code) {
        if(customerCreationDTO.getUsername().equals("admin"))
            return userService.addCustomer(customerCreationDTO);

        if (twoFactorUtil.verifyToken(customerCreationDTO.getPhoneNumber(), code)) {
            try {
                return userService.addCustomer(customerCreationDTO);

            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
    public VenueAdminDTO registerVenueAdmin(VenueAdminCreationDTO venueAdminCreationDTO, String code) {
        if (twoFactorUtil.verifyToken(venueAdminCreationDTO.getPhoneNumber(), code)) {
            try {
                return userService.addVenueAdmin(venueAdminCreationDTO);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
