package xyz.arunangshu.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {

  private Integer transactionId;
  private Integer categoryId;
  private Integer userId;
  private Double amount;
  private String note;
  private Long transactionDate;
}
