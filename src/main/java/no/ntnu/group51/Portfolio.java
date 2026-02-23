package no.ntnu.group51;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Portfolio {
    private List<Share> shares;

    public Portfolio() {
        this.shares = new ArrayList<>();
    }

    public boolean addShare(Share share){
        if (share == null)
            throw new IllegalArgumentException("Share cannot be null.");
        return shares.add(share);
    }

    public boolean removeShare (Share share){
        if (share == null)
            throw new IllegalArgumentException("Share cannot be null.");
        return shares.remove(share);
    }

    public List<Share> getShares() {
        return Collections.unmodifiableList(shares);
    }

    public List<Share> getShares(String symbol) {
        if (symbol == null)
            throw new IllegalArgumentException("Symbol cannot be null.");
        List<Share> indexedList = shares.stream()
                .filter(s -> s.getSymbol().equalsIgnoreCase(symbol))
                .toList();
        return indexedList;
    }

    public boolean contains (Share share){
        if (share == null)
            throw new IllegalArgumentException("Share cannot be null.");
        return shares.contains(share);
    }
}
