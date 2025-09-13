package com.coding.app.controllers;

import com.coding.app.exceptions.InvalidObjectException;
import com.coding.app.exceptions.NotFoundException;
import com.coding.app.models.User;
import com.coding.app.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private static final String PATH_LOGIN = "login";

    private static final String PATH_SIGNUP = "signup";

    private static final String PATH_SUCCESS = "success_page";

    private static final String PATH_ERROR = "error_page";

    private static final String ATTR_SUCCESS = "successMessage";

    private static final String ATTR_ERROR = "errorMessage";

    private static final String ATTR_ERRORS = "errors";

    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    public ModelAndView loginForm(final HttpServletRequest request) {
        final ModelAndView model = new ModelAndView(PATH_LOGIN);
        final String err = request.getParameter(ATTR_ERROR);
        if (err != null)
            model.addObject(ATTR_ERROR, "Your credentials are incorrect.");
        return model;
    }

    @GetMapping("/signup")
    public ModelAndView signup() {
        return new ModelAndView(PATH_SIGNUP);
    }

    @PostMapping("/signup")
    public ModelAndView submitSignupData(@RequestPayload final User user, final HttpServletRequest request) {
        ModelAndView model;
        try {
            authenticationService.registerUser(user, request);
            model = new ModelAndView(PATH_SUCCESS);
            model.addObject(ATTR_SUCCESS, "Your account has been created successfully. You will receive a confirmation email.");
        } catch (final InvalidObjectException e) {
            model = new ModelAndView(PATH_SIGNUP);
            model.addObject(ATTR_ERROR, e.getMessage());
            model.addObject(ATTR_ERRORS, e.getErrors());
            return model;
        }
        return model;
    }

    @GetMapping("/verification")
    public ModelAndView validSignupGet(
            @RequestParam("code") final String code,
            @RequestParam("username") final String username,
            final HttpServletRequest request) {
        ModelAndView modelAndView;
        try {
            authenticationService.verifyUser(username, code, request);
            modelAndView = new ModelAndView(PATH_SUCCESS);
            modelAndView.addObject(ATTR_SUCCESS, "Your account has been verified successfully. You can now log in.");
        } catch (final NotFoundException e) {
            modelAndView = new ModelAndView(PATH_ERROR);
            modelAndView.addObject(ATTR_ERROR, e.getMessage());
        }
        return modelAndView;
    }
}
