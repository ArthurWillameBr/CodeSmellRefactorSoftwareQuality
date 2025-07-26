// SearchLog.java
package org.example.studysearch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Encapsulates search logging behavior,
 * providing history, usage counts and summaries.
 */
public class SearchLog {
    private final String logName;
    private final LinkedList<String> history = new LinkedList<>();
    private final Map<String, Integer> counts = new HashMap<>();
    private boolean locked;
    private int totalUsages;

    private static final DateTimeFormatter TIMESTAMP_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SearchLog(String logName) {
        this.logName = logName;
    }

    /**
     * Records a search term, increments counters and returns the log entry message.
     */
    public String recordSearch(String term) {
        if (locked) {
            return "[LOG LOCKED]";
        }
        history.add(term);
        counts.put(term, counts.getOrDefault(term, 0) + 1);
        totalUsages++;
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FMT);
        return String.format("[%s] Logged in: %s (usage #%d)", timestamp, logName, totalUsages);
    }

    /**
     * Adds a search term to history without recording timestamp or counts.
     * Kept for legacy tests.
     */
    public void addSearchHistory(String term) {
        history.add(term);
    }

    /**
     * @deprecated Use getHistory() instead.
     */
    @Deprecated
    public LinkedList<String> getSearchHistory() {
        return history;
    }

    /**
     * Returns an unmodifiable copy of the search history.
     */
    public List<String> getHistory() {
        return history.stream().collect(Collectors.toUnmodifiableList());
    }

    /**
     * @deprecated Use getTotalUsages() instead.
     */
    @Deprecated
    public int getNumUsages() {
        return totalUsages;
    }

    /**
     * Returns the total number of searches performed.
     */
    public int getTotalUsages() {
        return totalUsages;
    }

    /**
     * Returns the count for a specific search term.
     */
    public int getCountFor(String term) {
        return counts.getOrDefault(term, 0);
    }

    /**
     * Provides a formatted summary of top searched terms.
     */
    public String getSummary(int topN) {
        StringBuilder sb = new StringBuilder("Top " + topN + " search terms:\n");
        counts.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                .limit(topN)
                .forEach(e -> sb.append(e.getKey()).append(" (").append(e.getValue()).append(")\n"));
        return sb.toString();
    }

    /**
     * Locks further logging.
     */
    public void lock() {
        this.locked = true;
    }

    /**
     * Unlocks logging.
     */
    public void unlock() {
        this.locked = false;
    }

    /**
     * Checks if logging is locked.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Returns the log's name.
     */
    public String getLogName() {
        return logName;
    }
}