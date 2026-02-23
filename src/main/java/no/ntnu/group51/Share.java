package no.ntnu.group51;

import java.math.BigDecimal;

public class Share {
    private final Stock stock;
    private BigDecimal quantity;
    private BigDecimal purchasePrice;

    public Share(Stock stock, BigDecimal quantity, BigDecimal purchasePrice){
        if (stock == null)
            throw new IllegalArgumentException ("Stock cannot be null");
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Quantity must be positive.");
        if (purchasePrice == null || purchasePrice.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Purchase price cannot be negative.");
        this.stock = stock;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }

    public Stock getStock(){
        return stock;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }
}
