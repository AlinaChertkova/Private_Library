//package com.example.personalLib.API.UI;
//
//import com.example.personalLib.DB.Models.Role;
//import com.example.personalLib.DB.Models.UserModel;
//import com.example.personalLib.DB.Repository.UserRepository;
//import com.vaadin.flow.component.Key;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.html.Label;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.PasswordField;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.router.Route;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.Set;
//
//@Route("registration")
//public class RegistrationView extends VerticalLayout {
//    @Autowired
//    private UserRepository userRepositiry;
//
//    private TextField loginField;
//    private TextField usernameField;
//    private PasswordField passwordField;
//    private PasswordField repeatPasswordField;
//    private Button registrationButton;
//
//    private Label errorLabel;
//
//    @Autowired
//    private AuthenticationManager authManager;
//
//    public RegistrationView() {
//        setAlignItems(Alignment.CENTER);
//
//        errorLabel = new Label();
//        errorLabel.getElement().getStyle().set("color", "red");
//        errorLabel.setVisible(false);
//        add(errorLabel);
//
//        loginField = new TextField();
//        loginField.setLabel("Логин");
//
//        usernameField = new TextField();
//        usernameField.setLabel("Имя");
//
//        passwordField = new PasswordField();
//        passwordField.setLabel("Пароль");
//        repeatPasswordField = new PasswordField();
//        repeatPasswordField.setLabel("Повторите пароль");
//        passwordField.addKeyPressListener(Key.ENTER, listener -> registerAction());
//
//        registrationButton = new Button("Сохранить");
//        registrationButton.addClickListener(e -> registerAction());
//
//        add(loginField, usernameField, passwordField, repeatPasswordField, registrationButton);
//    }
//
//    private void registerAction() {
//        try {
//            String username = loginField.getValue();
//            String password = passwordField.getValue();
//            UserModel user1 = userRepositiry.findByLogin(username);
//            if (user1 != null)
//            {
//                passwordField.focus();
//                loginField.setValue("");
//                throw new Exception("Пользователь в таким логином уже существует!");
//            }
//
//            if (!password.equals(repeatPasswordField.getValue()))
//            {
//                repeatPasswordField.setValue("");
//                throw new Exception("Пароли на совпадают!");
//            }
//            if (password.isEmpty() || loginField.isEmpty() || usernameField.isEmpty())
//            {
//                throw new Exception("Заполнены не все поля!");
//            }
//            UserModel user = new UserModel();
//            user.setActive(true);
//            user.setPassword(password);
//            user.setLogin(username);
//            user.setName(usernameField.getValue());
//            Set<Role> r  = new HashSet<>();
//            r.add(Role.USER);
//            user.setRoles(r);
//            user.setRegistrationDate(LocalDateTime.now());
//            userRepositiry.save(user);
//
//            registrationButton.getUI().ifPresent(ui -> ui.navigate("login"));
//        } catch (Exception e) {
//            errorLabel.setVisible(true);
//            errorLabel.setText(e.getMessage());
//        }
//    }
//}
