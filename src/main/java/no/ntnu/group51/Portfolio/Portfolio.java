package no.ntnu.group51.Portfolio;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import no.ntnu.group51.Stocks.Share;

/**
 * Portfolio class.
 */
public class Portfolio {
    private List<Share> shares;

    /**
     * Constructor for the Portfolio class.
     * Creates a list with intention to add shares.
     */
    public Portfolio() {
        this.shares = new ArrayList<>();
    }

    /**
     * Adds a share in the Portfolio-list.
     *
     * @param share the share you want to add.
     * @return true if the share was added, false if it was not.
     * @throws IllegalArgumentException if share is null.
     */
    public boolean addShare(Share share) {
        if (share == null)
            throw new IllegalArgumentException("Share cannot be null.");
        return shares.add(share);
    }

    /**
     * Removes a share in the Portfolio-list.
     *
     * @param share the share you want to remove.
     * @return true if the share was removed, false if it was not.
     * @throws IllegalArgumentException if share is null.
     */
    public boolean removeShare (Share share) {
        if (share == null)
            throw new IllegalArgumentException("Share cannot be null.");
        return shares.remove(share);
    }

    /**
     * Creates an unmodifiable list of the shares in the portfolio
     *
     * @return the unmodifiable list of the portfolio's shares.
     */
    public List<Share> getShares() {
        return Collections.unmodifiableList(shares);
    }

    /**
     * Returns the shares associated with a specific symbol.
     * Searches through the portfolio list with the entered symbol,
     * and adds it to a list.
     *
     * @param symbol the symbol for a stock, i.e "AAPL".
     * @return a list of shares with the entered symbol.
     * @throws IllegalArgumentException if symbol is null.
     */
    public List<Share> getShares(String symbol) {
        if (symbol == null)
            throw new IllegalArgumentException("Symbol cannot be null.");
        List<Share> indexedList = shares.stream()
                .filter(s -> s.getStock().getSymbol().equalsIgnoreCase(symbol))
                .toList();
        return indexedList;
    }

    /**
     * Checks if a share is in a portfolio
     *
     * @param share the share you want to check in a portfolio
     * @return true if the portfolio contains the share, false if it does not.
     * @throws IllegalArgumentException if symbol is null.
     */
    public boolean contains(Share share) {
        if (share == null)
            throw new IllegalArgumentException("Share cannot be null.");
        return shares.contains(share);
    }
}
