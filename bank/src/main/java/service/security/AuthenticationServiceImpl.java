package service.security;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.right_role.RightsRolesRepository;
import repository.user.AuthenticationException;
import repository.user.UserRepository;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthenticationServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> register(String username, String password, String role) {
        Notification<Boolean> userRegisterNotification = new Notification<>();
        Notification<User> existingUserNotification = userRepository.findByUsername(username);
        Role userRole = rightsRolesRepository.findRoleByTitle(role);

        if (!existingUserNotification.hasErrors()) {
            User user = existingUserNotification.getResult();
            if (user != null) {
                List<Role> existingRoles = rightsRolesRepository.findRolesForUser(existingUserNotification.getResult().getId());
                for (Role r : existingRoles) {
                    if (role.equals(r.getRole())) {
                        userRegisterNotification.addError("Role already existing!");
                        return userRegisterNotification;
                    }
                }

                rightsRolesRepository.addRolesToUser(user, Collections.singletonList(userRole));
                userRegisterNotification.setResult(Boolean.TRUE);
                return userRegisterNotification;
            }
        }

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(userRole))
                .build();
        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(encodePassword(password));
            userRegisterNotification.setResult(userRepository.save(user));
        }
        return userRegisterNotification;
    }

    @Override
    public Notification<User> login(String username, String password) throws AuthenticationException {
        return userRepository.findByUsernameAndPassword(username, encodePassword(password));
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
