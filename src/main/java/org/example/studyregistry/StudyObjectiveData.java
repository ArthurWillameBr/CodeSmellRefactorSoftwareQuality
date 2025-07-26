package org.example.studyregistry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class StudyObjectiveData {
    private final RegistryInfo registryInfo;
    private final ContentInfo contentInfo;
    private final TimeInfo timeInfo;

    private StudyObjectiveData(Builder b) {
        this.registryInfo = new RegistryInfo(b.id, b.priority, b.name, b.isActive);
        this.contentInfo = new ContentInfo(b.title, b.description, b.topic, b.objectiveInOneLine,
                b.objectiveFullDescription, b.motivation);
        this.timeInfo = new TimeInfo(b.practicedDays, b.day, b.month, b.year, b.duration);
    }

    private static class RegistryInfo {
        final Integer id;
        final Integer priority;
        final String name;
        final boolean isActive;

        RegistryInfo(Integer id, Integer priority, String name, boolean isActive) {
            this.id = id;
            this.priority = priority;
            this.name = name;
            this.isActive = isActive;
        }
    }

    private static class ContentInfo {
        final String title;
        final String description;
        final String topic;
        final String objectiveInOneLine;
        final String objectiveFullDescription;
        final String motivation;

        ContentInfo(String title, String description, String topic, String objectiveInOneLine,
                    String objectiveFullDescription, String motivation) {
            this.title = title;
            this.description = description;
            this.topic = topic;
            this.objectiveInOneLine = objectiveInOneLine;
            this.objectiveFullDescription = objectiveFullDescription;
            this.motivation = motivation;
        }
    }

    private static class TimeInfo {
        final Integer practicedDays;
        final int day;
        final int month;
        final int year;
        final Double duration;

        TimeInfo(Integer practicedDays, int day, int month, int year, Double duration) {
            this.practicedDays = practicedDays;
            this.day = day;
            this.month = month;
            this.year = year;
            this.duration = duration;
        }
    }

    public Integer getId() { return registryInfo.id; }
    public Integer getPriority() { return registryInfo.priority; }
    public String getName() { return registryInfo.name; }
    public boolean isActive() { return registryInfo.isActive; }

    public String getTitle() { return contentInfo.title; }
    public String getDescription() { return contentInfo.description; }
    public String getTopic() { return contentInfo.topic; }
    public String getObjectiveInOneLine() { return contentInfo.objectiveInOneLine; }
    public String getObjectiveFullDescription() { return contentInfo.objectiveFullDescription; }
    public String getMotivation() { return contentInfo.motivation; }

    public Integer getPracticedDays() { return timeInfo.practicedDays; }
    public Double getDuration() { return timeInfo.duration; }

    public LocalDateTime createLocalDateTime() {
        return LocalDateTime.of(timeInfo.year, timeInfo.month, timeInfo.day, 0, 0);
    }

    public String getFormattedDate() {
        return createLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public boolean isValidObjectiveData() {
        return registryInfo.id != null
                && registryInfo.priority != null
                && timeInfo.practicedDays != null
                && registryInfo.name != null && !registryInfo.name.isEmpty()
                && contentInfo.title != null && !contentInfo.title.isEmpty()
                && timeInfo.duration != null
                && Objects.nonNull(createLocalDateTime());
    }

    public String getObjectiveSummary() {
        return String.format("Objetivo: %s | Tópico: %s | Duração: %.1f | Prioridade: %d",
                contentInfo.title, contentInfo.topic, timeInfo.duration, registryInfo.priority);
    }

    public static class Builder {
        private Integer id;
        private Integer priority;
        private Integer practicedDays;
        private int day;
        private int month;
        private int year;
        private String name;
        private String title;
        private String description;
        private String topic;
        private String objectiveInOneLine;
        private String objectiveFullDescription;
        private String motivation;
        private Double duration;
        private boolean isActive;

        public Builder withId(Integer id) { this.id = id; return this; }
        public Builder withPriority(Integer priority) { this.priority = priority; return this; }
        public Builder withPracticedDays(Integer practicedDays) { this.practicedDays = practicedDays; return this; }
        public Builder withDate(int day, int month, int year) {
            this.day = day; this.month = month; this.year = year; return this;
        }
        public Builder withName(String name) { this.name = name; return this; }
        public Builder withTitle(String title) { this.title = title; return this; }
        public Builder withDescription(String description) { this.description = description; return this; }
        public Builder withTopic(String topic) { this.topic = topic; return this; }
        public Builder withObjectiveInOneLine(String obj) { this.objectiveInOneLine = obj; return this; }
        public Builder withObjectiveFullDescription(String full) { this.objectiveFullDescription = full; return this; }
        public Builder withMotivation(String motivation) { this.motivation = motivation; return this; }
        public Builder withDuration(Double duration) { this.duration = duration; return this; }
        public Builder isActive(boolean isActive) { this.isActive = isActive; return this; }

        public StudyObjectiveData build() {
            StudyObjectiveData data = new StudyObjectiveData(this);
            if (!data.isValidObjectiveData()) {
                throw new IllegalArgumentException("Dados de objetivo inválidos");
            }
            return data;
        }
    }
}