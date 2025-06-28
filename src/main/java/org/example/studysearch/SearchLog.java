package org.example.studysearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchLog {
    private List<String> searchHistory;
    private Map<String, Integer> searchCount;
    private boolean isLocked;
    private Integer numUsages;
    private String logName;

    public SearchLog(String logName) {
        searchHistory = new ArrayList<>();
        searchCount = new HashMap<>();
        this.logName = logName;
        numUsages = 0;
        isLocked = false;
    }
    public void addSearchHistory(String searchHistory) {
        this.searchHistory.add(searchHistory);
    }
    public void recordSearch(String query) {
        addSearchHistory(query);
        this.numUsages = this.numUsages + 1;
    }
    public List<String> getSearchHistory() {
        return searchHistory;
    }
    public void setSearchHistory(List<String> searchHistory) {
        this.searchHistory = searchHistory;
    }
    public Map<String, Integer> getSearchCount() {
        return searchCount;
    }
    public void setSearchCount(Map<String, Integer> searchCount) {
        this.searchCount = searchCount;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Integer getNumUsages() {
        return numUsages;
    }

    public void setNumUsages(Integer numUsages) {
        this.numUsages = numUsages;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }
    public String getLogEntry() {
        return "\nLogged in: " + this.logName;
    }
}
