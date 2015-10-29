package ru.innopolis.dmd.project.web.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.innopolis.dmd.project.model.User;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
public class UserRegistrationValidator implements Validator {
    private Predicate<String> loginPredicate =
            Pattern.compile("[A-Za-z0-9]+").asPredicate();
    private Predicate<String> passwordPredicate =
            Pattern.compile("[A-Za-z0-9]+").asPredicate();
    private Predicate<String> emailPredicate =
            Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9])*(\\.[A-Za-z]{2,})")
                    .asPredicate();

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (user.getLogin().trim().length() == 0)
            errors.rejectValue("login", "login.empty");
        else if (!loginPredicate.test(user.getLogin().trim()))
            errors.rejectValue("login", "login.invalid");
        String email = user.getEmail().trim();
        if (email.equals(""))
            errors.rejectValue("email", "email.empty");
        else if (!emailPredicate.test(email))
            errors.rejectValue("email", "email.invalid");
        String password = user.getPassword().trim();
        if (password.equals(""))
            errors.rejectValue("password", "password.empty");
        else if (!passwordPredicate.test(password))
            errors.rejectValue("password", "password.invalid");
    }
}
