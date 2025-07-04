package org.example.studymaterial;

public abstract class Reference {
    private String title;
    private String description;
    private String link;
    private String accessRights;
    private String license;
    private boolean isDownloadable;
    private int rating;
    private String language;
    private int viewCount;
    private int downloadCount;
    private int shareCount;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(String accessRights) {
        this.accessRights = accessRights;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public boolean getIsDownloadable() {
        return isDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        isDownloadable = downloadable;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    // Basic and update methods
    public void editBasic(String title, String description, String link) {
        this.setTitle(title);
        this.setDescription(description);
        this.setLink(link);
    }

    public void updateBasicInfo(String title, String description, String link) {
        editBasic(title, description, link);
    }

    // Simple counters
    public void incrementViewCount() {
        this.viewCount++;
    }

    public void incrementDownloadCount() {
        this.downloadCount++;
    }

    public void incrementShareCount() {
        this.shareCount++;
    }

    // Rating validation
    public boolean isValidRating(int rating) {
        return rating >= 0 && rating <= 5;
    }

    // Summary
    public String getSummary() {
        return String.format(
                "Title: %s%nDescription: %s%nRating: %d%nViews: %d, Downloads: %d, Shares: %d",
                title, description, rating, viewCount, downloadCount, shareCount
        );
    }

    // Accessibility
    public boolean isPubliclyAccessible() {
        return "Public".equalsIgnoreCase(accessRights);
    }

    // --- New Complex Business Methods ---

    /**
     * Calculates a popularity score based on weighted metrics.
     * Formula: (views * 0.1) + (downloads * 0.3) + (shares * 0.2) + (rating * 10)
     */
    public double calculatePopularityScore() {
        double score = viewCount * 0.1
                + downloadCount * 0.3
                + shareCount * 0.2
                + rating * 10;
        return Math.round(score * 100.0) / 100.0; // two decimals
    }

    /**
     * Validates completeness: title, description, link, accessRights, license, language must be non-null/empty.
     */
    public boolean validateReferenceCompleteness() {
        return isNonEmpty(title)
                && isNonEmpty(description)
                && isNonEmpty(link)
                && isNonEmpty(accessRights)
                && isNonEmpty(license)
                && isNonEmpty(language);
    }

    private boolean isNonEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    /**
     * Bulk update engagement metrics with validation: values must be >= current counts.
     */
    public boolean updateEngagementMetrics(int newViews, int newDownloads, int newShares) {
        if (newViews < viewCount || newDownloads < downloadCount || newShares < shareCount) {
            return false; // cannot decrease metrics
        }
        this.viewCount = newViews;
        this.downloadCount = newDownloads;
        this.shareCount = newShares;
        return true;
    }

    /**
     * Compares popularity score against another Reference.
     * Returns positive if this > other, zero if equal, negative if less.
     */
    public double comparePopularity(Reference other) {
        return this.calculatePopularityScore() - other.calculatePopularityScore();
    }

    /**
     * Determines if this reference is high quality: rating>=4 and popularity score above threshold.
     */
    public boolean isHighQuality() {
        return rating >= 4 && calculatePopularityScore() >= 50.0;
    }

    /**
     * Provides a detailed engagement summary including percentage breakdowns.
     */
    public String getEngagementSummary() {
        int total = viewCount + downloadCount + shareCount;
        if (total == 0) {
            return "No engagement yet.";
        }
        double pv = (viewCount * 100.0) / total;
        double pd = (downloadCount * 100.0) / total;
        double ps = (shareCount * 100.0) / total;
        return String.format(
                "Engagement - Views: %d (%.1f%%), Downloads: %d (%.1f%%), Shares: %d (%.1f%%)",
                viewCount, pv, downloadCount, pd, shareCount, ps
        );
    }
}
