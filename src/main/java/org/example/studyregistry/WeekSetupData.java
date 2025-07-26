package org.example.studyregistry;

import java.util.Arrays;

public class WeekSetupData {
    private String planName;
    private String objectiveTitle;
    private String objectiveDescription;
    private String materialTopic;
    private String materialFormat;
    private String goal;
    private String reminderTitle;
    private String reminderDescription;
    private String mainTaskTitle;
    private String mainHabit;
    private String mainCardStudy;

    // Construtor delega para método auxiliar (reduz NcssCount)
    public WeekSetupData(Builder builder) {
        assignFromBuilder(builder);
    }

    private void assignFromBuilder(Builder builder) {
        String[] values = {
                builder.planName, builder.objectiveTitle, builder.objectiveDescription,
                builder.materialTopic, builder.materialFormat, builder.goal,
                builder.reminderTitle, builder.reminderDescription,
                builder.mainTaskTitle, builder.mainHabit, builder.mainCardStudy
        };

        assignBasicFields(values, 0);
        assignMaterialFields(values, 3);
        assignReminderFields(values, 6);
        assignTaskFields(values, 8);
    }

    private void assignBasicFields(String[] values, int startIndex) {
        this.planName = values[startIndex];
        this.objectiveTitle = values[startIndex + 1];
        this.objectiveDescription = values[startIndex + 2];
    }

    private void assignMaterialFields(String[] values, int startIndex) {
        this.materialTopic = values[startIndex];
        this.materialFormat = values[startIndex + 1];
        this.goal = values[startIndex + 2];
    }

    private void assignReminderFields(String[] values, int startIndex) {
        this.reminderTitle = values[startIndex];
        this.reminderDescription = values[startIndex + 1];
    }

    private void assignTaskFields(String[] values, int startIndex) {
        this.mainTaskTitle = values[startIndex];
        this.mainHabit = values[startIndex + 1];
        this.mainCardStudy = values[startIndex + 2];
    }

    // Métodos de negócio (eliminam Data Class)
    public boolean isValid() {
        return notEmpty(planName) && notEmpty(objectiveTitle) && notEmpty(mainTaskTitle);
    }

    public boolean hasValidMaterial() {
        return notEmpty(materialTopic) && notEmpty(materialFormat);
    }

    public boolean hasValidReminder() {
        return notEmpty(reminderTitle) && notEmpty(reminderDescription);
    }

    public String getTasksSummary() {
        return String.join(" | ",
                notEmpty(mainTaskTitle) ? mainTaskTitle : "Sem tarefa",
                notEmpty(mainHabit) ? mainHabit : "Sem hábito",
                notEmpty(mainCardStudy) ? mainCardStudy : "Sem flashcard"
        );
    }

    public String toConfigString() {
        return String.format(
                "Plano: %s | Objetivo: %s - %s | Material: %s (%s) | Meta: %s | Lembrete: %s - %s | Tarefa: %s | Hábito: %s | Flashcard: %s",
                planName, objectiveTitle, objectiveDescription,
                materialTopic, materialFormat, goal,
                reminderTitle, reminderDescription,
                mainTaskTitle, mainHabit, mainCardStudy
        );
    }

    public int countFilledFields() {
        return (int) Arrays.stream(new String[]{
                planName, objectiveTitle, objectiveDescription, materialTopic,
                materialFormat, goal, reminderTitle, reminderDescription,
                mainTaskTitle, mainHabit, mainCardStudy
        }).filter(this::notEmpty).count();
    }

    public double getCompletionPercentage() {
        return (countFilledFields() / 11.0) * 100;
    }

    private boolean notEmpty(String value) {
        return value != null && !value.isBlank();
    }

    // Getters
    public String getPlanName() { return planName; }
    public String getObjectiveTitle() { return objectiveTitle; }
    public String getObjectiveDescription() { return objectiveDescription; }
    public String getMaterialTopic() { return materialTopic; }
    public String getMaterialFormat() { return materialFormat; }
    public String getGoal() { return goal; }
    public String getReminderTitle() { return reminderTitle; }
    public String getReminderDescription() { return reminderDescription; }
    public String getMainTaskTitle() { return mainTaskTitle; }
    public String getMainHabit() { return mainHabit; }
    public String getMainCardStudy() { return mainCardStudy; }

    // Builder Pattern
    public static class Builder {
        private String planName;
        private String objectiveTitle;
        private String objectiveDescription;
        private String materialTopic;
        private String materialFormat;
        private String goal;
        private String reminderTitle;
        private String reminderDescription;
        private String mainTaskTitle;
        private String mainHabit;
        private String mainCardStudy;

        public Builder withPlanName(String planName) {
            this.planName = planName;
            return this;
        }

        public Builder withObjectiveTitle(String objectiveTitle) {
            this.objectiveTitle = objectiveTitle;
            return this;
        }

        public Builder withObjectiveDescription(String objectiveDescription) {
            this.objectiveDescription = objectiveDescription;
            return this;
        }

        public Builder withMaterialTopic(String materialTopic) {
            this.materialTopic = materialTopic;
            return this;
        }

        public Builder withMaterialFormat(String materialFormat) {
            this.materialFormat = materialFormat;
            return this;
        }

        public Builder withGoal(String goal) {
            this.goal = goal;
            return this;
        }

        public Builder withReminderTitle(String reminderTitle) {
            this.reminderTitle = reminderTitle;
            return this;
        }

        public Builder withReminderDescription(String reminderDescription) {
            this.reminderDescription = reminderDescription;
            return this;
        }

        public Builder withMainTaskTitle(String mainTaskTitle) {
            this.mainTaskTitle = mainTaskTitle;
            return this;
        }

        public Builder withMainHabit(String mainHabit) {
            this.mainHabit = mainHabit;
            return this;
        }

        public Builder withMainCardStudy(String mainCardStudy) {
            this.mainCardStudy = mainCardStudy;
            return this;
        }

        public WeekSetupData build() {
            return new WeekSetupData(this);
        }
    }
}
