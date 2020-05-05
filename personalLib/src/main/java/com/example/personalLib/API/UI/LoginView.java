//package com.example.personalLib.API.UI;
//
//import com.example.personalLib.DB.Models.Role;
//import com.vaadin.flow.component.Key;
//import com.vaadin.flow.component.UI;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.html.Label;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.PasswordField;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.router.BeforeEvent;
//import com.vaadin.flow.router.HasUrlParameter;
//import com.vaadin.flow.router.OptionalParameter;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.server.VaadinSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//@Route("login")
//public class LoginView extends VerticalLayout implements HasUrlParameter<String> {
//
//    private TextField usernameField;
//    private PasswordField passwordField;
//    private Button loginButton;
//
//    private Button registrationButton;
//
//    private Label logoutLabel;
//
//    @Autowired
//    private AuthenticationManager authManager;
//
//    public LoginView() {
//        setAlignItems(Alignment.CENTER);
//        registrationButton = new Button("Зарегистрироваться");
//        registrationButton.addClickListener(e ->
//            registrationButton.getUI().ifPresent(ui -> ui.navigate("registration")));
//
//        logoutLabel = new Label();
//        logoutLabel.getElement().getStyle().set("color", "red");
//        logoutLabel.setVisible(false);
//        add(logoutLabel);
//
//        usernameField = new TextField();
//        usernameField.setLabel("Логин");
//
//        passwordField = new PasswordField();
//        passwordField.setLabel("Пароль");
//        passwordField.addKeyPressListener(Key.ENTER, listener -> loginAction());
//
//        loginButton = new Button("Войти");
//        loginButton.addClickListener(e ->
//            loginAction()
//        );
//
//        add(usernameField, passwordField, loginButton, registrationButton);
//    }
//
//    private void loginAction() {
//        String username = usernameField.getValue();
//        String password = passwordField.getValue();
//        loginButton.setEnabled(false);
//        if (login(username, password)) {
//            loginButton.getUI().ifPresent(ui -> ui.navigate(""));
//        } else {
//            logoutLabel.setVisible(true);
//            loginButton.setEnabled(true);
//        }
//    }
//
//    private boolean login(String username, String password) {
//        try {
//
//            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, password);
//
//            Authentication auth = authManager.authenticate(authReq);
//            SecurityContext sc = SecurityContextHolder.getContext();
//            sc.setAuthentication(auth);
//            return true;
//
//        } catch (BadCredentialsException ex) {
//            logoutLabel.setText("Неверный логин или пароль");
//            usernameField.focus();
//            passwordField.setValue("");
//        } catch (Exception ex) {
//            Notification.show("Ошибка! Пожалуйста, повторите позже", 3000, Notification.Position.MIDDLE);
//
//        }
//        return false;
//    }
//
//    @Override
//    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
//        if (parameter != null && parameter.contentEquals("loggedout")) {
//            logoutLabel.setVisible(true);
//            logoutLabel.setText("Вы успешно вышли");
//        }
//    }
//}
//
//
