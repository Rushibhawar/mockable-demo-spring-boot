package com.mockable.app.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanAccountResponse {
    private String loanAccountNumber;
    private String dueDate;
    private String emiAmount;
}
