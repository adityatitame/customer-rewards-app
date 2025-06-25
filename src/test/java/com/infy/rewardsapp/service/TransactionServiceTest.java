package com.infy.rewardsapp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.model.Customer;
import com.infy.rewardsapp.model.CustomerDTO;
import com.infy.rewardsapp.model.RewardSummary;
import com.infy.rewardsapp.model.Transaction;
import com.infy.rewardsapp.model.TransactionDTO;
import com.infy.rewardsapp.repository.CustomerRepository;
import com.infy.rewardsapp.repository.TransactionRepository;

@SpringBootTest
public class TransactionServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;
    
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private ModelMapper modelMapper = new ModelMapper();

    
    @Test
    void testAddTransaction_AmountUnderOrEqual50() throws RewardsAppException {
        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(50.0);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(1);
        dto.setCustomerDTO(customerDTO);

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setTotalRewardPoints(0);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        String response = transactionService.addTransaction(dto);
        Assertions.assertEquals("Transaction added successfully. Reward Points Earned: 0", response);
    }

    @Test
    void testAddTransaction_AmountBetween51And100() throws RewardsAppException {
        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(80.0); 
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(1);
        dto.setCustomerDTO(customerDTO);

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setTotalRewardPoints(0);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        String response = transactionService.addTransaction(dto);
        Assertions.assertEquals("Transaction added successfully. Reward Points Earned: 30", response);
    }

    
    @Test
    void testAddTransaction_Success() throws RewardsAppException {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(1);
        customerDTO.setTotalRewardPoints(0);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(120.0);
        transactionDTO.setDate(LocalDate.of(2024, 6, 10));
        transactionDTO.setCustomerDTO(customerDTO);

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setTotalRewardPoints(0);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        String result = transactionService.addTransaction(transactionDTO);
        Assertions.assertEquals("Transaction added successfully. Reward Points Earned: 90", result);
    }
     
    @Test
    void testAddTransaction_CustomerNotFound() {
        TransactionDTO dto = new TransactionDTO();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(99);
        dto.setCustomerDTO(customerDTO);

        when(customerRepository.findById(99)).thenReturn(Optional.empty());

        RewardsAppException e = Assertions.assertThrows(RewardsAppException.class,
                () -> transactionService.addTransaction(dto));

        Assertions.assertEquals("TRANSACTIONSERVICE.CUSTOMER_NOT_FOUND", e.getMessage());
    }

    @Test
    void testCalculateRewardPtsForTimeFrame_Success() throws RewardsAppException {
        Integer customerId = 1;
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 12, 31);

        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setName("Alice");
        customer.setEmail("alice@example.com");
        customer.setContact("9999999999");
        customer.setTotalRewardPoints(150);

        Transaction tx1 = new Transaction();
        tx1.setDate(LocalDate.of(2024, 2, 10));
        tx1.setRewardPoints(30);

        Transaction tx2 = new Transaction();
        tx2.setDate(LocalDate.of(2024, 2, 15));
        tx2.setRewardPoints(20);

        List<Transaction> transactions = Arrays.asList(tx1, tx2);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(transactionRepository.findByCustomerCustomerIdAndDateBetween(customerId, from, to)).thenReturn(transactions);

        RewardSummary summary = transactionService.calculateRewardPtsForTimeFrame(customerId, from, to);

        Assertions.assertNotNull(summary);
        Assertions.assertEquals(customerId, summary.getCustomerId());
        Assertions.assertEquals(50, summary.getTotalPointsForRange());
    }

    @Test
    void testCalculateRewardPtsForTimeFrame_CustomerNotFound() {
        Integer customerId = 77;
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 12, 31);

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        RewardsAppException e = Assertions.assertThrows(RewardsAppException.class,
                () -> transactionService.calculateRewardPtsForTimeFrame(customerId, from, to));

        Assertions.assertEquals("TRANSACTIONSERVICE.CUSTOMER_NOT_FOUND77", e.getMessage());
    }

}

