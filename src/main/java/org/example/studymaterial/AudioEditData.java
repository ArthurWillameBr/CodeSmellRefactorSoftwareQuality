package org.example.studymaterial;

public class AudioEditData {
    private final BasicInfo basicInfo;
    private final AccessInfo accessInfo;
    private final MetricsInfo metricsInfo;

    // ✅ CONSTRUTOR REFATORADO - Apenas 3 linhas (sem NcssCount)
    private AudioEditData(Builder builder) {
        this.basicInfo = new BasicInfo(builder.audioQuality, builder.isDownloadable, builder.title, builder.description);
        this.accessInfo = new AccessInfo(builder.link, builder.accessRights, builder.license, builder.language);
        this.metricsInfo = new MetricsInfo(builder.rating, builder.viewCount, builder.shareCount);
    }

    // ✅ CLASSES AUXILIARES - Agrupam campos relacionados
    private static class BasicInfo {
        final AudioReference.AudioQuality audioQuality;
        final boolean isDownloadable;
        final String title;
        final String description;

        BasicInfo(AudioReference.AudioQuality audioQuality, boolean isDownloadable, String title, String description) {
            this.audioQuality = audioQuality;
            this.isDownloadable = isDownloadable;
            this.title = title;
            this.description = description;
        }
    }

    private static class AccessInfo {
        final String link;
        final String accessRights;
        final String license;
        final String language;

        AccessInfo(String link, String accessRights, String license, String language) {
            this.link = link;
            this.accessRights = accessRights;
            this.license = license;
            this.language = language;
        }
    }

    private static class MetricsInfo {
        final int rating;
        final int viewCount;
        final int shareCount;

        MetricsInfo(int rating, int viewCount, int shareCount) {
            this.rating = rating;
            this.viewCount = viewCount;
            this.shareCount = shareCount;
        }
    }

    // Getters - delegam para objetos internos
    public AudioReference.AudioQuality getAudioQuality() { return basicInfo.audioQuality; }
    public boolean isDownloadable() { return basicInfo.isDownloadable; }
    public String getTitle() { return basicInfo.title; }
    public String getDescription() { return basicInfo.description; }
    public String getLink() { return accessInfo.link; }
    public String getAccessRights() { return accessInfo.accessRights; }
    public String getLicense() { return accessInfo.license; }
    public String getLanguage() { return accessInfo.language; }
    public int getRating() { return metricsInfo.rating; }
    public int getViewCount() { return metricsInfo.viewCount; }
    public int getShareCount() { return metricsInfo.shareCount; }

    // Métodos de negócio (Move Method)
    public boolean isValidAudioData() {
        return basicInfo.title != null && !basicInfo.title.isBlank()
                && accessInfo.link != null && !accessInfo.link.isBlank()
                && basicInfo.audioQuality != null
                && metricsInfo.rating >= 0 && metricsInfo.rating <= 5;
    }

    public boolean hasHighQuality() {
        return basicInfo.audioQuality == AudioReference.AudioQuality.HIGH || basicInfo.audioQuality == AudioReference.AudioQuality.VERY_HIGH;
    }

    public String getAudioSummary() {
        return String.format("'%s' [%s] (%s) - %s", basicInfo.title, accessInfo.language, accessInfo.license, basicInfo.isDownloadable ? "Downloadable" : "Streaming only");
    }

    public boolean isPublicAccess() {
        return "public".equalsIgnoreCase(accessInfo.accessRights);
    }

    public int calculateEngagementScore() {
        return (metricsInfo.viewCount * 2) + (metricsInfo.shareCount * 3) + (metricsInfo.rating * 10);
    }

    public String toMetadataString() {
        return String.format("Title: %s | Quality: %s | Language: %s | License: %s | Views: %d | Shares: %d",
                basicInfo.title, basicInfo.audioQuality, accessInfo.language, accessInfo.license, metricsInfo.viewCount, metricsInfo.shareCount);
    }

    public boolean validateRating() {
        return metricsInfo.rating >= 0 && metricsInfo.rating <= 5;
    }

    public static class Builder {
        private AudioReference.AudioQuality audioQuality;
        private boolean isDownloadable;
        private String title;
        private String description;
        private String link;
        private String accessRights;
        private String license;
        private String language;
        private int rating;
        private int viewCount;
        private int shareCount;

        public Builder withAudioQuality(AudioReference.AudioQuality audioQuality) {
            this.audioQuality = audioQuality;
            return this;
        }

        public Builder isDownloadable(boolean isDownloadable) {
            this.isDownloadable = isDownloadable;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withLink(String link) {
            this.link = link;
            return this;
        }

        public Builder withAccessRights(String accessRights) {
            this.accessRights = accessRights;
            return this;
        }

        public Builder withLicense(String license) {
            this.license = license;
            return this;
        }

        public Builder withLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder withRating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder withViewCount(int viewCount) {
            this.viewCount = viewCount;
            return this;
        }

        public Builder withShareCount(int shareCount) {
            this.shareCount = shareCount;
            return this;
        }

        public AudioEditData build() {
            return new AudioEditData(this);
        }
    }
}