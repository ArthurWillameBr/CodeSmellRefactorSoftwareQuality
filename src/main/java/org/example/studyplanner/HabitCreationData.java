package org.example.studyplanner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Objeto de parâmetro para criação de Habit, encapsulando dados de negócio e usando Builder.
 */
public class HabitCreationData {
    private final String name;
    private final String motivation;
    private final int dailyMinutesDedication;
    private final int dailyHoursDedication;
    private final LocalDateTime startDate;
    private final boolean concluded;

    private HabitCreationData(Builder builder) {
        this.name = builder.name;
        this.motivation = builder.motivation;
        this.dailyMinutesDedication = builder.dailyMinutesDedication;
        this.dailyHoursDedication = builder.dailyHoursDedication;
        this.startDate = builder.startDate;
        this.concluded = builder.concluded;
    }

    /**
     * Valida se os dados mínimos para criar um hábito estão presentes.
     */
    public boolean isValidHabitData() {
        return name != null && !name.isBlank()
                && motivation != null && !motivation.isBlank()
                && startDate != null;
    }

    /**
     * Verifica se o tempo de dedicação diário está dentro de limites aceitáveis.
     */
    public boolean hasValidDedicationTime() {
        return dailyHoursDedication >= 0 && dailyHoursDedication <= 23
                && dailyMinutesDedication >= 0 && dailyMinutesDedication <= 59;
    }

    public String getName() { return name; }
    public String getMotivation() { return motivation; }
    public LocalTime getDailyDedicationTime() {
        return LocalTime.of(dailyHoursDedication, dailyMinutesDedication);
    }
    public LocalDateTime getStartDate() { return startDate; }
    public boolean isConcluded() { return concluded; }

    /**
     * Builder para HabitCreationData.
     */
    public static class Builder {
        private String name;
        private String motivation;
        private int dailyMinutesDedication;
        private int dailyHoursDedication;
        private LocalDateTime startDate;
        private boolean concluded = false;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }
        public Builder withMotivation(String motivation) {
            this.motivation = motivation;
            return this;
        }
        public Builder withDailyDedication(int hours, int minutes) {
            this.dailyHoursDedication = hours;
            this.dailyMinutesDedication = minutes;
            return this;
        }
        public Builder withStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }
        public Builder concluded(boolean concluded) {
            this.concluded = concluded;
            return this;
        }
        public HabitCreationData build() {
            HabitCreationData data = new HabitCreationData(this);
            if (!data.isValidHabitData()) {
                throw new IllegalStateException("Dados inválidos para criação de hábito");
            }
            if (!data.hasValidDedicationTime()) {
                throw new IllegalStateException("Tempo de dedicação diário inválido");
            }
            return data;
        }
    }
}