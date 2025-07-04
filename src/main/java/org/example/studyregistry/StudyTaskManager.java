package org.example.studyregistry;

import org.example.studymaterial.Reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudyTaskManager {
    private static StudyTaskManager instance;
    private WeekSetupData lastWeekConfig;
    private StudyMaterial studyMaterial = StudyMaterial.getStudyMaterial();
    private List<Registry> registryList;
    private List<String> weekResponsibilities;

    private StudyTaskManager(){
        this.registryList = new ArrayList<>();
        this.weekResponsibilities = new ArrayList<>();
    }
    public static StudyTaskManager getStudyTaskManager(){
        if (instance == null) {
            instance = new StudyTaskManager();
        }
        return instance;
    }

    public List<String> getWeekResponsibilities() {
        return weekResponsibilities;
    }

    public void handleSetUpWeek(List<String> stringProperties) {
        if (stringProperties.size() != 11) {
            throw new IllegalArgumentException("A lista deve conter exatamente 11 elementos.");
        }
        WeekSetupData config = new WeekSetupData.Builder()
                .withPlanName(stringProperties.get(0))
                .withObjectiveTitle(stringProperties.get(1))
                .withObjectiveDescription(stringProperties.get(2))
                .withMaterialTopic(stringProperties.get(3))
                .withMaterialFormat(stringProperties.get(4))
                .withGoal(stringProperties.get(5))
                .withReminderTitle(stringProperties.get(6))
                .withReminderDescription(stringProperties.get(7))
                .withMainTaskTitle(stringProperties.get(8))
                .withMainHabit(stringProperties.get(9))
                .withMainCardStudy(stringProperties.get(10))
                .build();
        setUpWeek(config);
    }

    public void setUpWeek(WeekSetupData config) {
        this.lastWeekConfig = config;
        this.weekResponsibilities = new ArrayList<>(Arrays.asList(
                config.getPlanName(),
                config.getObjectiveTitle(),
                config.getObjectiveDescription(),
                config.getMaterialTopic(),
                config.getMaterialFormat(),
                config.getGoal(),
                config.getReminderTitle(),
                config.getReminderDescription(),
                config.getMainTaskTitle(),
                config.getMainHabit(),
                config.getMainCardStudy()
        ));
    }


    public void addReference(Reference reference) {
        studyMaterial.addReference(reference);
    }

    public void addRegistry(Registry registry){
        registryList.add(registry);
    }
    public void removeRegistry(Registry registry){
        registryList.remove(registry);
    }
    public List<Registry> getRegistryList(){
        return registryList;
    }

    public List<String> searchInRegistries(String text){
        List<String> response = new ArrayList<>();
        for(Registry registry : registryList){
            String mix = (registry.getName() != null ? registry.getName() : "");
            if (mix.toLowerCase().contains(text.toLowerCase())){
                response.add(registry.getName());
            }
        }
        return response;
    }

}
