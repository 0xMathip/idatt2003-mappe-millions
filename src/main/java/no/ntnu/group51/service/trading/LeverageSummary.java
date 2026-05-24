package no.ntnu.group51.service.trading;

import java.math.BigDecimal;
import no.ntnu.group51.model.trading.Leverage;

public record LeverageSummary(
    Leverage leverage,
    BigDecimal multiplier,
    BigDecimal marginRequired,
    BigDecimal exposure,
    BigDecimal liquidationPrice
) {
}
