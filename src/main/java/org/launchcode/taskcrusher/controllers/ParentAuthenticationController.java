package org.launchcode.taskcrusher.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.taskcrusher.models.ParentUser;
import org.launchcode.taskcrusher.models.data.ParentUserRepository;
import org.launchcode.taskcrusher.models.dto.ParentLoginFormDTO;
import org.launchcode.taskcrusher.models.dto.ParentRegistrationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ParentAuthenticationController {

    @Autowired
    private ParentUserRepository parentUserRepository;

   // This is the key to store parent user IDs
    private static final String parentUserSessionKey = "parentUser";

    // This stores key/value pair with session key and parent user ID
    private static void setParentUserInSession(HttpSession session, ParentUser parentUser) {
        session.setAttribute(parentUserSessionKey, parentUser.getId());
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
    @GetMapping("/parent-register")
    public String displayParentRegistrationForm(Model model, HttpSession session){
        model.addAttribute(new ParentRegistrationFormDTO());
        model.addAttribute("loggedIn", session.getAttribute("parentUser") != null);
        return "parent-register";
    }

    @PostMapping("/parent-register")
    public String processParentRegistrationForm(@ModelAttribute @Valid ParentRegistrationFormDTO parentRegistrationFormDTO,
                                                Errors errors,
                                                HttpServletRequest request) {
        // Sends parent user back to form if errors are found
        if (errors.hasErrors()) {
            return "parent-register";
        }
        //Look up parent user in the DB using the username they provided in the form
        ParentUser existingParentUser = parentUserRepository.findByUsername(parentRegistrationFormDTO.getUsername());
        // Sends parent user back to form if username already esists
        if (existingParentUser != null) {
            errors.rejectValue("username", "username.alreadyExists", "A user with that username already exists");
            return "parent-register";
        }
        //Sends parent user back to form is passwords didn't match
        String password = parentRegistrationFormDTO.getPassword();
        String verifyPassword = parentRegistrationFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            return "parent-register";
        }
        //If there are no errors, save new username and hashed password in DB, start a new session, and redirect to
        // parent homepage
        ParentUser newParentUser = new ParentUser(parentRegistrationFormDTO.getUsername(),
                parentRegistrationFormDTO.getPassword());
        parentUserRepository.save(newParentUser);
        setParentUserInSession(request.getSession(), newParentUser);
        return "redirect:/parent-dash";
    }

    //Handlers for login form
    @GetMapping("/parent-login")
    public String displayParentLoginForm(Model model, HttpSession session) {
        model.addAttribute(new ParentLoginFormDTO());
        model.addAttribute("loggedIn", session.getAttribute("parentUser") != null);
        return "parent-login";
    }

    @PostMapping("/parent-login")
    public String processParentLoginForm(@ModelAttribute @Valid ParentLoginFormDTO parentLoginFormDTO,
                                         Errors errors,
                                         HttpServletRequest request) {
        // This sends parent user back to the form if errors are found
        if (errors.hasErrors()) {
            return "parent-login";
        }
        // This looks up parent user in DB using username they provided in the form
        ParentUser theParentUser = parentUserRepository.findByUsername(parentLoginFormDTO.getUsername());
        //This gets the password the parent user supplied in the form
        String parentPassword = parentLoginFormDTO.getPassword();
        // This will sent parent user back to the form if the username doesn't exist or if password hash doesn't match
        if (theParentUser == null || !theParentUser.isMatchingPassword(parentPassword)) {
            errors.rejectValue("password", "login.invalid", "Credentials invalid. Please try again with correct " +
                    "username and/or password");
            return "parent-login";
        }
        // If there are no errors, create a new session for the parent user and direct them to parent dashboard
        setParentUserInSession(request.getSession(), theParentUser);
        return "redirect:/parent-dash";
    }

    //Handler for logging out
    @GetMapping("/parent-logout")
    public String parentLogout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/parent-login";
    }
}
