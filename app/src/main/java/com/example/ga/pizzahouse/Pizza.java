package com.example.ga.pizzahouse;

public class Pizza {
    private boolean isSelected;
    private String pizzaName;
    private double pizzaPrice;
    private String pizzaDescription;

/*    public Pizza(String pizzaName, double pizzaPrice, String pizzaDescription) {
        this.pizzaName = pizzaName;
        this.pizzaPrice = pizzaPrice;
        this.pizzaDescription = pizzaDescription;
    }*/

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public double getPizzaPrice() {
        return pizzaPrice;
    }

    public String getPizzaDescription() {
        return pizzaDescription;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public void setPizzaPrice(double pizzaPrice) {
        this.pizzaPrice = pizzaPrice;
    }

    public void setPizzaDescription(String pizzaDescription) {
        this.pizzaDescription = pizzaDescription;
    }
}
