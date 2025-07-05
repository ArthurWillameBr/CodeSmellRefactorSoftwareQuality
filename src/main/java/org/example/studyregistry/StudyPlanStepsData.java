package org.example.studyregistry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class StudyPlanStepsData {
    private final StepsInfo stepsInfo;
    private final ObjectivesInfo objectivesInfo;
    private final ConfigInfo configInfo;

    private StudyPlanStepsData(Builder builder) {
        this.stepsInfo = new StepsInfo(builder.firstStep, builder.resetStudyMechanism,
                builder.consistentStep, builder.seasonalSteps, builder.basicSteps);
        this.objectivesInfo = new ObjectivesInfo(builder.mainObjectiveTitle, builder.mainGoalTitle,
                builder.mainMaterialTopic, builder.mainTask);
        this.configInfo = new ConfigInfo(builder.numberOfSteps, builder.isImportant,
                builder.startDate, builder.endDate);
    }

    private static class StepsInfo {
        final String firstStep;
        final String resetStudyMechanism;
        final String consistentStep;
        final String seasonalSteps;
        final String basicSteps;

        StepsInfo(String firstStep, String resetStudyMechanism, String consistentStep,
                  String seasonalSteps, String basicSteps) {
            this.firstStep = firstStep;
            this.resetStudyMechanism = resetStudyMechanism;
            this.consistentStep = consistentStep;
            this.seasonalSteps = seasonalSteps;
            this.basicSteps = basicSteps;
        }
    }

    private static class ObjectivesInfo {
        final String mainObjectiveTitle;
        final String mainGoalTitle;
        final String mainMaterialTopic;
        final String mainTask;

        ObjectivesInfo(String mainObjectiveTitle, String mainGoalTitle,
                       String mainMaterialTopic, String mainTask) {
            this.mainObjectiveTitle = mainObjectiveTitle;
            this.mainGoalTitle = mainGoalTitle;
            this.mainMaterialTopic = mainMaterialTopic;
            this.mainTask = mainTask;
        }
    }

    private static class ConfigInfo {
        final Integer numberOfSteps;
        final Boolean isImportant;
        final LocalDateTime startDate;
        final LocalDateTime endDate;

        ConfigInfo(Integer numberOfSteps, Boolean isImportant,
                   LocalDateTime startDate, LocalDateTime endDate) {
            this.numberOfSteps = numberOfSteps;
            this.isImportant = isImportant;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    public static class Builder {
        private String firstStep;
        private String resetStudyMechanism;
        private String consistentStep;
        private String seasonalSteps;
        private String basicSteps;

        private String mainObjectiveTitle;
        private String mainGoalTitle;
        private String mainMaterialTopic;
        private String mainTask;

        private Integer numberOfSteps;
        private Boolean isImportant;

        private LocalDateTime startDate;
        private LocalDateTime endDate;

        public Builder withFirstStep(String val)                { this.firstStep = val; return this; }
        public Builder withResetStudyMechanism(String val)      { this.resetStudyMechanism = val; return this; }
        public Builder withConsistentStep(String val)           { this.consistentStep = val; return this; }
        public Builder withSeasonalSteps(String val)            { this.seasonalSteps = val; return this; }
        public Builder withBasicSteps(String val)               { this.basicSteps = val; return this; }
        public Builder withMainObjectiveTitle(String val)       { this.mainObjectiveTitle = val; return this; }
        public Builder withMainGoalTitle(String val)            { this.mainGoalTitle = val; return this; }
        public Builder withMainMaterialTopic(String val)        { this.mainMaterialTopic = val; return this; }
        public Builder withMainTask(String val)                 { this.mainTask = val; return this; }

        public Builder withNumberOfSteps(Integer val)           { this.numberOfSteps = val; return this; }
        public Builder withIsImportant(Boolean val)             { this.isImportant = val; return this; }

        public Builder withStartDate(LocalDateTime val)         { this.startDate = val; return this; }
        public Builder withEndDate(LocalDateTime val)           { this.endDate = val; return this; }

        public StudyPlanStepsData build() {
            Objects.requireNonNull(firstStep, "firstStep não pode ser nulo");
            if (numberOfSteps == null || numberOfSteps < 1) {
                throw new IllegalArgumentException("Número de passos inválido");
            }
            if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
                throw new IllegalArgumentException("Datas inválidas");
            }
            return new StudyPlanStepsData(this);
        }
    }

    // Getters - delegam para objetos internos
    public String getFirstStep()            { return stepsInfo.firstStep; }
    public String getResetStudyMechanism()  { return stepsInfo.resetStudyMechanism; }
    public String getConsistentStep()       { return stepsInfo.consistentStep; }
    public String getSeasonalSteps()        { return stepsInfo.seasonalSteps; }
    public String getBasicSteps()           { return stepsInfo.basicSteps; }
    public String getMainObjectiveTitle()   { return objectivesInfo.mainObjectiveTitle; }
    public String getMainGoalTitle()        { return objectivesInfo.mainGoalTitle; }
    public String getMainMaterialTopic()    { return objectivesInfo.mainMaterialTopic; }
    public String getMainTask()             { return objectivesInfo.mainTask; }
    public Integer getNumberOfSteps()       { return configInfo.numberOfSteps; }
    public Boolean getIsImportant()         { return configInfo.isImportant; }
    public LocalDateTime getStartDate()     { return configInfo.startDate; }
    public LocalDateTime getEndDate()       { return configInfo.endDate; }

    // Métodos de negócio - atualizados para usar objetos internos
    public boolean isValidStepsData() {
        return configInfo.numberOfSteps > 0 && !configInfo.endDate.isBefore(configInfo.startDate);
    }

    public String getFormattedStartDate() {
        return format(configInfo.startDate);
    }

    public String getFormattedEndDate() {
        return format(configInfo.endDate);
    }

    private String format(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(formatter);
    }

    public int getStepsCount() {
        return configInfo.numberOfSteps;
    }

    public String getStepsSummary() {
        return String.format("Steps: %s, %s, %s, %s, %s",
                stepsInfo.firstStep, stepsInfo.resetStudyMechanism, stepsInfo.consistentStep,
                stepsInfo.seasonalSteps, stepsInfo.basicSteps);
    }

    public String getObjectivesSummary() {
        return String.format("Objectives: %s | Goal: %s | Topic: %s | Task: %s",
                objectivesInfo.mainObjectiveTitle, objectivesInfo.mainGoalTitle,
                objectivesInfo.mainMaterialTopic, objectivesInfo.mainTask);
    }
}