package ru.neoflex.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.service.DealService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DealController.class)
@ExtendWith(MockitoExtension.class)
class DealControllerTest {
    @MockBean
    private DealService dealService;
    @InjectMocks
    private DealController dealController;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void ConveyorController_CalculationCreditParameters_ReturnLoanOfferDTO() throws Exception {
        LoanApplicationRequestDTO loanApplicationRequestDTO = new LoanApplicationRequestDTO(
                BigDecimal.valueOf(150000),
                24,
                "Ivan",
                "Petrov",
                null,
                "ivan@example.com",
                LocalDate.of(1990, 8, 10),
                "5678",
                "123456"
        );

        List<LoanOfferDTO> expectedLoanOffers = new ArrayList<>();

        LoanOfferDTO loanOffer1 = new LoanOfferDTO(12L, BigDecimal.valueOf(150000), BigDecimal.valueOf(151500.00),
                24, BigDecimal.valueOf(6547.17), BigDecimal.valueOf(4.5), true, true);

        LoanOfferDTO loanOffer2 = new LoanOfferDTO(12L, BigDecimal.valueOf(150000), BigDecimal.valueOf(151500.00),
                24, BigDecimal.valueOf(6614.35), BigDecimal.valueOf(5.5), true, false);

        LoanOfferDTO loanOffer3 = new LoanOfferDTO(12L, BigDecimal.valueOf(150000), BigDecimal.valueOf(150000),
                24, BigDecimal.valueOf(6749.94), BigDecimal.valueOf(7.5), false, true);

        LoanOfferDTO loanOffer4 = new LoanOfferDTO(12L, BigDecimal.valueOf(150000), BigDecimal.valueOf(150000),
                24, BigDecimal.valueOf(6818.35), BigDecimal.valueOf(8.5), false, false);

        expectedLoanOffers.add(0, loanOffer1);
        expectedLoanOffers.add(1, loanOffer2);
        expectedLoanOffers.add(2, loanOffer3);
        expectedLoanOffers.add(3, loanOffer4);

        when(dealService.calculationPossibleCreditConditions(loanApplicationRequestDTO)).thenReturn(expectedLoanOffers);

        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].applicationId").value(12L))
                .andExpect(jsonPath("$[0].requestedAmount").value(150000))
                .andExpect(jsonPath("$[0].totalAmount").value(151500.00))
                .andExpect(jsonPath("$[0].term").value(24))
                .andExpect(jsonPath("$[0].monthlyPayment").value(6547.17))
                .andExpect(jsonPath("$[0].rate").value(4.5))
                .andExpect(jsonPath("$[0].isInsuranceEnabled").value(true))
                .andExpect(jsonPath("$[0].isSalaryClient").value(true))
                .andExpect(jsonPath("$[1].applicationId").value(12L))
                .andExpect(jsonPath("$[1].requestedAmount").value(150000))
                .andExpect(jsonPath("$[1].totalAmount").value(151500.00))
                .andExpect(jsonPath("$[1].term").value(24))
                .andExpect(jsonPath("$[1].monthlyPayment").value(6614.35))
                .andExpect(jsonPath("$[1].rate").value(5.5))
                .andExpect(jsonPath("$[1].isInsuranceEnabled").value(true))
                .andExpect(jsonPath("$[1].isSalaryClient").value(false))
                .andExpect(jsonPath("$[2].applicationId").value(12L))
                .andExpect(jsonPath("$[2].requestedAmount").value(150000))
                .andExpect(jsonPath("$[2].totalAmount").value(150000.00))
                .andExpect(jsonPath("$[2].term").value(24))
                .andExpect(jsonPath("$[2].monthlyPayment").value(6749.94))
                .andExpect(jsonPath("$[2].rate").value(7.5))
                .andExpect(jsonPath("$[2].isInsuranceEnabled").value(false))
                .andExpect(jsonPath("$[2].isSalaryClient").value(true))
                .andExpect(jsonPath("$[3].applicationId").value(12L))
                .andExpect(jsonPath("$[3].requestedAmount").value(150000))
                .andExpect(jsonPath("$[3].totalAmount").value(150000.00))
                .andExpect(jsonPath("$[3].term").value(24))
                .andExpect(jsonPath("$[3].monthlyPayment").value(6818.35))
                .andExpect(jsonPath("$[3].rate").value(8.5))
                .andExpect(jsonPath("$[3].isInsuranceEnabled").value(false))
                .andExpect(jsonPath("$[3].isSalaryClient").value(false));
    }
}

