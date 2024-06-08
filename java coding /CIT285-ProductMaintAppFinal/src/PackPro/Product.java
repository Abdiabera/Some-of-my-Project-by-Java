package PackPro;

import java.text.NumberFormat;

public class Product {
    private String code;
    private String id;
    private String description;
    private int amount;
    private double price;
    private char discount;

    public Product() {
        this("", "", "", 0, 0, ' ');
    }

    public Product(String code, String id, String description,
            int amount, double price, char discount) {
        setCode(code);
        setId(id);
        setDescription(description);
        setAmount(amount);
        setPrice(price);
        setDiscount(discount);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getAmountString() {
        String amountString = Integer.toString(amount);
        return amountString;

    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getFormattedPrice() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(price);
    }

    public void setDiscount(char discount) {
        this.discount = discount;
    }

    public char getDiscount() {
        return discount;
    }

    public boolean equals(Object object) {
        if (object instanceof Product) {
            Product product2 = (Product) object;

            if (
                code.equals(product2.getCode()) &&
                id.equals(product2.getId()) &&
                description.equals(product2.getDescription()) &&
                amount == product2.getAmount() &&
                price == product2.getPrice() &&
                discount == product2.getDiscount()) {
                return true;
            }
        }
        return false;
    }

    // This is kind of an ugly way of circumventing the implicit call to this
    // class's toString() method by the DefaultListModel's addElement() method.
    public String toStringAllFields() {
        StringBuilder sb = new StringBuilder(100);
        sb.append("code: ").append(code).append(" \n")
            .append("description: ").append(description).append(" \n")
            .append("amount: ").append(amount).append(" \n")
            .append("price: ").append(getFormattedPrice()).append(" \n")
            .append("discount: ").append(discount).append("\n");

        return sb.toString();
    }

    @Override
    public String toString() {
        return description;
    }
}