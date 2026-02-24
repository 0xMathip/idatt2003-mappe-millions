package no.ntnu.group51.Calculator;

import java.math.BigDecimal;

public interface TransactionCalculator {

  BigDecimal calculateGross();

  BigDecimal calculateCommission();

  BigDecimal calculateTax();

  BigDecimal calculateTotal();
}
