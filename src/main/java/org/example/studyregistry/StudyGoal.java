package org.example.studyregistry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudyGoal extends Registry {
    private String goal;
    private List<String> goalRequirements;
    private Boolean isCompleted;
    private LocalDateTime createdDate;
    private Double goalCompletion;
    private StudyObjective studyObjective;
    private StudyPlan studyPlan;
    private String summary;

    public StudyGoal(String name, StudyObjective objective, StudyPlan plan) {
        this.name = name;
        this.studyObjective = objective;
        this.studyPlan = plan;
        goalRequirements = new ArrayList<>();
    }

    public void editActiveCompleted(boolean active, boolean completed) {
        this.isActive = active;
        this.isCompleted = completed;
    }

    public String setGoalSummary() {
        StringBuilder summaryBuilder = new StringBuilder();
        summaryBuilder.append("Goal Summary:\n\n");
        appendActiveGoal(summaryBuilder);
        appendCompletedGoal(summaryBuilder);
        appendRequirements(summaryBuilder);
        appendPlan(summaryBuilder);
        appendObjective(summaryBuilder);

        this.summary = summaryBuilder.toString();
        return this.summary;
    }

    private void appendActiveGoal(StringBuilder sb) {
        if (this.isActive) {
            sb.append("Active Goal:\n").append(goal).append("\n\n");
        }
    }

    private void appendCompletedGoal(StringBuilder sb) {
        if (this.isCompleted) {
            sb.append("Completed Goal:\n").append(goal).append("\n\n");
        }
    }

    private void appendRequirements(StringBuilder sb) {
        if (this.goalRequirements != null && !goalRequirements.isEmpty()) {
            sb.append("Requirements:\n");
            for (String requirement : this.goalRequirements) {
                sb.append(requirement).append(", ");
            }
            sb.append("\n\n");
        }
    }

    private void appendPlan(StringBuilder sb) {
        if (this.studyPlan != null) {
            sb.append("Plan:\n").append(this.studyPlan.toString()).append("\n\n");
        }
    }

    private void appendObjective(StringBuilder sb) {
        if (this.studyObjective != null) {
            sb.append("Objective:\n").append(this.studyObjective.toString()).append("\n");
        }
    }

    public void addRequirement(String requirement) {
        this.goalRequirements.add(requirement);
    }

    public void resetRequirements() {
        this.goalRequirements.clear();
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void toggleIsCompleted() {
        this.isCompleted = !this.isCompleted;
    }

    public LocalDateTime getLimitDate() {
        return createdDate;
    }

    public void setLimitDate(LocalDateTime limitDate) {
        this.createdDate = limitDate;
    }

    public void addDaysLimitDate(int days) {
        this.createdDate = this.createdDate.plusDays(days);
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}
