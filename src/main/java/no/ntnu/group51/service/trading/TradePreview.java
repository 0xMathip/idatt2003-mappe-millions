package no.ntnu.group51.service.trading;

import java.math.BigDecimal;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.TradeMode;
import no.ntnu.group51.model.trading.TradeType;

public record TradePreview(
    Stock stock,
    TradeType tradeType,
    TradeMode tradeMode,
    Leverage leverage,
    BigDecimal quantity,
    BigDecimal gross,
    BigDecimal tax,
    BigDecimal total
) {

}
