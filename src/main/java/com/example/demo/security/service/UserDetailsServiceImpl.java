package com.example.demo.security.service;

import com.example.demo.model.auth.Account;
import com.example.demo.model.auth.Customer;
import com.example.demo.model.auth.Employee;
import com.example.demo.repository.IAccountRepository;
import com.example.demo.repository.ICustomerRepository;
import com.example.demo.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IAccountRepository accountRepository;
    private final IEmployeeRepository employeeRepository;
    private final ICustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.length() < 10){
            Employee employee = employeeRepository.findByEmployeeId(Integer.parseInt(username));
            if (employee.getAccount() == null){
                throw new UsernameNotFoundException("Account not found with username: "+username);
            }
            Account account = employee.getAccount();
            account.setUsername(username);
            return UserDetailsImpl.build(account);
        }else{
            Customer customer = customerRepository.findCustomerByPhone(username);
            if (customer.getAccount() == null){
                throw new UsernameNotFoundException("Account not found with username: "+username);
            }
            Account account = customer.getAccount();
            account.setUsername(username);
            return UserDetailsImpl.build(account);
        }
    }
}
