package xyz.arunangshu.expensetracker.services;

import java.util.List;
import xyz.arunangshu.expensetracker.domain.Category;
import xyz.arunangshu.expensetracker.exceptions.EtBadRequestException;
import xyz.arunangshu.expensetracker.exceptions.EtResourceNotFoundException;

public interface CategoryService {

  List<Category> fetchAllCategories(Integer userId);

  Category fetchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException;

  Category addCategory(Integer userId, String title, String description) throws EtBadRequestException;

  void updateCategory(Integer userId, Integer categoryId, Category category) throws EtBadRequestException;

  void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
}
