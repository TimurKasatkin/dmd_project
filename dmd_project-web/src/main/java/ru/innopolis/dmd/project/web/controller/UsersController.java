package ru.innopolis.dmd.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.dmd.project.model.User;
import ru.innopolis.dmd.project.service.UserService;
import ru.innopolis.dmd.project.web.util.AuthenticationUtil;
import ru.innopolis.dmd.project.web.validation.UserRegistrationValidator;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
@Controller
public class UsersController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;

//  "password" == "$2a$10$a8.KLt0hE4rwxBsV0L.z0.65qebt7uzPcnY9hTr11QyOybs8oSS6C"

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(new UserRegistrationValidator());
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(required = false) String error, ModelMap modelMap) {
        if (AuthenticationUtil.isAuthenticated())
            return "redirect:/";
        modelMap.addAttribute("user", new User());
        if (error != null) modelMap.addAttribute("error", "Incorrect login or password");
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Validated User user, BindingResult result) {
        if (userService.findByLoginIgnoreCase(user.getLogin()) != null)
            result.rejectValue("login", "login.exist");
        if (result.hasErrors())
            return "login";
        user.setPassword(encoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/";
    }
}
