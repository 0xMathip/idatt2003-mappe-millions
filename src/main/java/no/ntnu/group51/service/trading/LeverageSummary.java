package no.ntnu.group51.service.trading;

import java.math.BigDecimal;
import no.ntnu.group51.model.trading.Leverage;

/**
 * Represents calculated leverage data for a leveraged trade preview.
 *
 * @param leverage the selected leverage level
 * @param multiplier the leverage multiplier
 * @param marginRequired the required margin to open the position
 * @param exposure the total market exposure
 * @param liquidationPrice the calculated liquidation price
 */
public record LeverageSummary(
    Leverage leverage,
    BigDecimal multiplier,
    BigDecimal marginRequired,
    BigDecimal exposure,
    BigDecimal liquidationPrice
) {
}
