package org.example.studyregistry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudyPlan extends Registry{
    private StudyObjective objective;
    private List<String> steps;

    public StudyPlan(String planName, StudyObjective objective, List<StudyMaterial> materials) {
        this.name = planName;
        this.objective = objective;
        this.steps = new ArrayList<>();
    }

    @Override
    public String toString(){
        return "Plan: " + name + ",\nObjective: " + objective.getDescription() + ",\nSteps: " + String.join(", ", steps);
    }

    public List<String> getSteps() {
        return steps;
    }

    public StudyObjective getObjective() {
        return objective;
    }

    public void assignObjective(StudyObjective objective) {
        this.objective = objective;
    }

    public void addSingleStep(String toAdd){
        steps.add(toAdd);
    }

    public void assignSteps(StudyPlanStepsData data) {
        this.steps = new ArrayList<>(Arrays.asList(
                data.getFirstStep(),
                data.getResetStudyMechanism(),
                data.getConsistentStep(),
                data.getSeasonalSteps(),
                data.getBasicSteps(),
                "Number of steps: " + data.getStepsCount(),
                "Is it important to you? " + data.getIsImportant(),
                data.getFormattedStartDate(),
                data.getFormattedEndDate(),
                data.getMainObjectiveTitle(),
                data.getMainGoalTitle(),
                data.getMainMaterialTopic(),
                data.getMainTask()
        ));
    }

    public void handleAssignSteps(StudyPlanStepsData data){
        assignSteps(data);
    }

    public void handleAssignSteps(List<String> stringProperties, Integer numberOfSteps,
                                  boolean isImportant, LocalDateTime startDate, LocalDateTime endDate){
        StudyPlanStepsData data = new StudyPlanStepsData.Builder()
                .withFirstStep(stringProperties.get(0))
                .withResetStudyMechanism(stringProperties.get(1))
                .withConsistentStep(stringProperties.get(2))
                .withSeasonalSteps(stringProperties.get(3))
                .withBasicSteps(stringProperties.get(4))
                .withMainObjectiveTitle(stringProperties.get(5))
                .withMainGoalTitle(stringProperties.get(6))
                .withMainMaterialTopic(stringProperties.get(7))
                .withMainTask(stringProperties.get(8))
                .withNumberOfSteps(numberOfSteps)
                .withIsImportant(isImportant)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .build();

        handleAssignSteps(data);
    }
}