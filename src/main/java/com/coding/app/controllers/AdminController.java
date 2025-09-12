package com.coding.app.controllers;

import com.coding.app.exceptions.InvalidObjectException;
import com.coding.app.exceptions.NotFoundException;
import com.coding.app.models.User;
import com.coding.app.models.enums.ServerRole;
import com.coding.app.services.HistoryService;
import com.coding.app.services.UserService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import java.util.List;

import static com.coding.app.controllers.ClientViewAttributes.*;
import static com.coding.app.controllers.DashboardViewAttributes.CURRENT_USER;
import static com.coding.app.controllers.HistoryViewAttributes.*;
import static com.coding.app.controllers.ManagerViewAttributes.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ClientViewAttributes {

    public static final String ADMIN_CLIENT_URI = "/admin/client";
    public static final String JSP_ADMIN_CLIENT = "dashboard-portal/page_client";
    public static final String MODEL_AND_VIEW_CLIENTS = "clients";
    public static final String REDIRECT_ADMIN_CLIENT = "redirect:" + ADMIN_CLIENT_URI;
}

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ManagerViewAttributes {

    public static final String ADMIN_MANAGER_URI = "/admin/manager";
    public static final String JSP_ADMIN_MANAGER = "dashboard-portal/page_manager";
    public static final String MODEL_AND_VIEW_MANAGERS_ATTRIBUTE = "managers";
    public static final String MODEL_AND_VIEW_MANAGER_ATTRIBUTE = "manager";
    public static final String REDIRECT_MANAGER = "redirect:" + ADMIN_MANAGER_URI;
}

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class HistoryViewAttributes {

    public static final String ADMIN_HISTORY_URI = "/admin/history";
    public static final String JSP_ADMIN_HISTORY = "dashboard-portal/page_history";
    public static final String MODEL_AND_VIEW_HISTORIES_ATTRIBUTE = "historyData";
    public static final String REDIRECT_HISTORY = "redirect:" + ADMIN_MANAGER_URI;
}

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final HistoryService historyService;

    @GetMapping(ADMIN_HISTORY_URI)
    public ModelAndView getHistoryPage() {
        final ModelAndView model = new ModelAndView(JSP_ADMIN_HISTORY);
        configureCurrentUser(model);
        DashboardUtils.activateMenu(DashboardUtils.NavbarMenu.HISTORY, model);
        model.addObject(MODEL_AND_VIEW_HISTORIES_ATTRIBUTE, historyService.getAllHistories());
        return model;
    }

    @GetMapping(ADMIN_HISTORY_URI + "/clear")
    public ModelAndView clearHistory() {
        historyService.clearHistory();
        return new ModelAndView(REDIRECT_HISTORY);
    }

    @GetMapping(ADMIN_MANAGER_URI)
    public ModelAndView getManagersPage() {
        final ModelAndView model = new ModelAndView(JSP_ADMIN_MANAGER);
        configureCurrentUser(model);
        DashboardUtils.activateMenu(DashboardUtils.NavbarMenu.MANAGERS, model);
        final List<User> users = userService.findUsersByFilter(user -> user.getRoles().contains(ServerRole.MANAGER.getRole()));
        model.addObject(MODEL_AND_VIEW_MANAGERS_ATTRIBUTE, users);
        return model;
    }

    @PostMapping(ADMIN_MANAGER_URI)
    public ModelAndView addManager(@RequestPayload final User user) {
        ModelAndView model;
        try {
            userService.createManager(user);
            model = new ModelAndView(REDIRECT_MANAGER);
        } catch (final InvalidObjectException e) {
            model = new ModelAndView(JSP_ADMIN_MANAGER);
            configureCurrentUser(model);
            DashboardUtils.activateMenu(DashboardUtils.NavbarMenu.MANAGERS, model);
            final List<User> users = userService.findUsersByFilter(u -> u.getRoles().contains(ServerRole.MANAGER.getRole()));
            model.addObject(MODEL_AND_VIEW_MANAGERS_ATTRIBUTE, users);
            model.addObject(MODEL_AND_VIEW_MANAGER_ATTRIBUTE, user);
            model.addObject("errors", e.getErrors());
        }
        return model;
    }

    @GetMapping(ADMIN_CLIENT_URI)
    public ModelAndView getClientsPage() {
        final ModelAndView model = new ModelAndView(JSP_ADMIN_CLIENT);
        configureCurrentUser(model);
        DashboardUtils.activateMenu(DashboardUtils.NavbarMenu.CLIENTS, model);
        final List<User> users = userService.findUsersByFilter(user -> user.getRoles().contains(ServerRole.CLIENT.getRole()));
        model.addObject(MODEL_AND_VIEW_CLIENTS, users);
        return model;
    }

    @GetMapping("/admin/ban")
    public ModelAndView banUser(@RequestParam("userType") final String userType, @RequestParam("username") final String username) throws NotFoundException {
        userService.banUser(username);
        if("manager".equals(userType)) {
            return new ModelAndView(REDIRECT_MANAGER);
        } else {
            return new ModelAndView(REDIRECT_ADMIN_CLIENT);
        }
    }

    private void configureCurrentUser(final ModelAndView model) {
        try {
            final User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addObject(CURRENT_USER, user);
        } catch (final NotFoundException e) {
            model.setViewName("redirect:/");
        }
    }
}
