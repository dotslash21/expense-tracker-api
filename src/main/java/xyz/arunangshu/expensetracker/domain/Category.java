package xyz.arunangshu.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

  private Integer categoryId;
  private Integer userId;
  private String title;
  private String description;
  private Double totalExpense;
}
