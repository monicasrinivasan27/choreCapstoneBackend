package org.launchcode.taskcrusher.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.launchcode.taskcrusher.models.ParentUser;
import org.launchcode.taskcrusher.models.data.ParentUserRepository;
import org.launchcode.taskcrusher.models.dto.ParentLoginFormDTO;
import org.launchcode.taskcrusher.models.dto.ParentRegistrationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ParentAuthenticationController {

    @Autowired
    private ParentUserRepository parentUserRepository;

   // This is the key to store parent user IDs
    private static final String parentUserSessionKey = "parentUser";

    // This stores key/value pair with session key and parent user ID
    private static void setParentUserInSession(HttpSession session, ParentUser parentUser) {
        session.setAttribute(parentUserSessionKey, parentUser.getParentId());
        System.out.println("session: " + session.getAttribute("parentUser"));
        // The above line was in Carrie's lecture. Likely not needed?
    }

    // This looks up parent user with key
    public ParentUser getParentUserFromSession(HttpSession session) {
        // This gets the parent user ID from the DB using key
        Integer parentUserId = (Integer) session.getAttribute(parentUserSessionKey);
        if (parentUserId == null) {
            return null;
        }
        // Get optional back from DB
        Optional<ParentUser> parentUserOpt = parentUserRepository.findById(parentUserId);

        //Early return with null if parent user is not found
        if (parentUserOpt.isEmpty()) {
            return null;
        }

        // Return parent user object (unboxed from optional)
        return parentUserOpt.get();
    }

    // Handlers for registration form
    @GetMapping("/register")
    public String displayParentRegistrationForm(Model model, HttpSession session){
        model.addAttribute(new ParentRegistrationFormDTO());
        model.addAttribute("loggedIn", session.getAttribute("parentUser") != null);
        return "/register";
    }

    @PostMapping("/register")
    public String processParentRegistrationForm(@ModelAttribute @Valid ParentRegistrationFormDTO parentRegistrationFormDTO,
                                                Errors errors,
                                                HttpServletRequest request) {
        // Sends parent user back to form if errors are found
        if (errors.hasErrors()) {
            return "Please fill all fields in correctly";
        }
        //Look up parent user in the DB using the username they provided in the form
        ParentUser existingParentUser = parentUserRepository.findByUsername(parentRegistrationFormDTO.getUsername());
        // Sends parent user back to form if username already esists
        if (existingParentUser != null) {
            errors.rejectValue("username", "username.alreadyExists", "A user with that username already exists");
            return "Username already exists";
        }

        // ADDED 1/23 ---------------------------------------
        //Look up parent user email in the DB and send user back if an account already exists with that email address
        ParentUser existingParentEmail = parentUserRepository.findByEmail(parentRegistrationFormDTO.getEmail());
        if (existingParentEmail != null) {
            errors.rejectValue("email", "email.alreadyExists", "An account is already created with that email");
            return "There is already an account linked to that email address";
        }
        // ADDED 1/23 ---------------------------------------

        //Sends parent user back to form is passwords didn't match
        String password = parentRegistrationFormDTO.getPassword();
        String verifyPassword = parentRegistrationFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            return "Passwords do not match";
        }
        //If there are no errors, save new username and hashed password in DB, start a new session, and redirect to
        // parent homepage
        ParentUser newParentUser = new ParentUser(parentRegistrationFormDTO.getUsername(),
                parentRegistrationFormDTO.getPassword());
        parentUserRepository.save(newParentUser);
        setParentUserInSession(request.getSession(), newParentUser);
        return "redirect:/parentDash";
    }

    //Handlers for login form
    @GetMapping("/api/parent-login")
    public String displayParentLoginForm(Model model, HttpSession session) {
        model.addAttribute(new ParentLoginFormDTO());
        model.addAttribute("loggedIn", session.getAttribute("parentUser") != null);
        return "/api/parent-login";
    }

    @PostMapping("/api/parent-login")
    public String processParentLoginForm(@ModelAttribute @Valid ParentLoginFormDTO parentLoginFormDTO,
                                         Errors errors,
                                         HttpServletRequest request) {
        // This sends parent user back to the form if errors are found
        if (errors.hasErrors()) {
            return "/api/parent-login";
        }
        // This looks up parent user in DB using username they provided in the form
        ParentUser theParentUser = parentUserRepository.findByUsername(parentLoginFormDTO.getUsername());
        //This gets the password the parent user supplied in the form
        String parentPassword = parentLoginFormDTO.getPassword();
        // This will sent parent user back to the form if the username doesn't exist or if password hash doesn't match
        if (theParentUser == null || !theParentUser.isMatchingPassword(parentPassword)) {
            errors.rejectValue("password", "login.invalid", "Credentials invalid. Please try again with correct " +
                    "username and/or password");
            return "/api/parent-login";
        }
        // If there are no errors, create a new session for the parent user and direct them to parent dashboard
        setParentUserInSession(request.getSession(), theParentUser);
        return "redirect:/api/parent-dash";
    }

    //Handler for logging out
    @GetMapping("/api/parent-logout")
    public String parentLogout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/api/parent-login";
    }
}
