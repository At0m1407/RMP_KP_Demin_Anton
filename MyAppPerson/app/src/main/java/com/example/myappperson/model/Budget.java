package com.example.myappperson.model;

public class Budget {
    private int budgetAmount;
    private String budgetCategory;


    public Budget(int budgetAmount, String budgetCategory) {
        this.budgetAmount = budgetAmount;
        this.budgetCategory = budgetCategory;

    }


    public String getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(String budgetCategory) {
        this.budgetCategory = budgetCategory;
    }


    public void setBudgetAmount(int budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public int getBudgetAmount() {
        return budgetAmount;
    }
}