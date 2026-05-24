package no.ntnu.group51.service.portfolio;

import java.math.BigDecimal;

public record PortfolioSummary(
    BigDecimal portfolioValue,
    BigDecimal availableCash,
    BigDecimal totalInvested,
    BigDecimal totalReturn,
    BigDecimal totalReturnPercent
) {

}
