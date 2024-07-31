package com.example.demo.service;

import com.example.demo.dto.account.ChangePassRequest;
import com.example.demo.dto.account.RequestLogin;
import com.example.demo.dto.account.ResponseLogin;
import com.example.demo.dto.account.UserCreate;
import com.example.demo.exception.BaseException;
import com.example.demo.model.auth.Account;
import com.example.demo.model.auth.Customer;
import com.example.demo.model.auth.Employee;
import com.example.demo.model.auth.Role;
import com.example.demo.repository.IAccountRepository;
import com.example.demo.repository.ICustomerRepository;
import com.example.demo.repository.IEmployeeRepository;
import com.example.demo.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AccountService {
    private final IAccountRepository accountRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IEmployeeRepository employeeRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ContextHolderService contextHolderService;

    @Autowired
    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public ResponseEntity<?> createAccountCustomer(UserCreate userCreate) {
        customerRepository.existsByPhone(userCreate.getUsername())
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST,"Phone number is already registered!"));
        Account account = new Account(userCreate.getUsername(),userCreate.getPassword(), Role.CUSTOMER,true);
        try {
            account = accountRepository.save(account);
            accountRepository.flush();
            Customer customer = new Customer(userCreate.getFullName(),userCreate.getUsername(),account);
            customerRepository.save(customer);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Create account failed!");
        }
        return ResponseEntity.ok("Create account successfully!");
    }

    // TODO
    public String customerLogin(String phone, String password) {
        return "";
    }

    // TODO
    public String employeeLogin(String phone, String password) {
        return "";
    }

    public ResponseEntity<?> login(RequestLogin requestLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestLogin.getUsername(), requestLogin.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Invalid credentials.");
        }
        Account account = new Account();
        if (requestLogin.getUsername().length() < 10){
            Employee employee = employeeRepository.findByEmployeeId(Integer.parseInt(requestLogin.getUsername()));
            if (employee.getAccount() == null){
                throw new UsernameNotFoundException("Account not found with username: "+requestLogin.getUsername());
            }
            account = employee.getAccount();
            account.setUsername(requestLogin.getUsername());
        }else{
            Customer customer = customerRepository.findCustomerByPhone(requestLogin.getUsername());
            if (customer.getAccount() == null){
                throw new UsernameNotFoundException("Account not found with username: "+requestLogin.getUsername());
            }
            account = customer.getAccount();
            account.setUsername(requestLogin.getUsername());
        }

        return ResponseEntity.ok(new ResponseLogin(account.getUsername(),
                jwtService.generateJwtToken(requestLogin.getUsername(), new Date(new Date().getTime() + JwtService.jwtExpirationMs)),
                account.getRole()));
    }

    @Transactional
    public ResponseEntity<?> changePassword(ChangePassRequest changePassRequest) {
        Account account = contextHolderService.getAccountFromContext();
        if(changePassRequest.getOldPass().equals(changePassRequest.getNewPass()))
            return ResponseEntity.badRequest().body("New Password must have different old password");
        account.setPassword(changePassRequest.getNewPass());
        try {
            accountRepository.save(account);
            return ResponseEntity.ok("Change password is successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Change password is failed!");
        }
    }
}
