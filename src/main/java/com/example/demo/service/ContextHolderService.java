package com.example.demo.service;

import com.example.demo.exception.BaseException;
import com.example.demo.model.auth.Account;
import com.example.demo.model.auth.Role;
import com.example.demo.repository.IAccountRepository;
import com.example.demo.repository.ICustomerRepository;
import com.example.demo.repository.IEmployeeRepository;
import com.example.demo.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContextHolderService {
    private final IAccountRepository userRepository;
    private final IEmployeeRepository employeeRepository;
    private final ICustomerRepository customerRepository;

    public Object getObjectFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null && !authentication.isAuthenticated() && (authentication instanceof AnonymousAuthenticationToken)){
            throw new BaseException(HttpStatus.UNAUTHORIZED,"You have not authenticated or the authentication process failed");
        }
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()).get(0);
        if(role.equals(Role.CUSTOMER.toString())){
            return customerRepository.findCustomerByPhone(authentication.getName());
        }else return employeeRepository.findByEmployeeId(Integer.parseInt(authentication.getName()));
    }

    public Account getAccountFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null && !authentication.isAuthenticated() && (authentication instanceof AnonymousAuthenticationToken)){
            throw new BaseException(HttpStatus.UNAUTHORIZED,"You have not authenticated or the authentication process failed");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        int accountId = userDetails.getAccountId();
        return userRepository.findById(accountId)
                .orElseThrow(() -> new BaseException(HttpStatus.UNAUTHORIZED,"You have not authenticated or the authentication process failed"));
    }
}
