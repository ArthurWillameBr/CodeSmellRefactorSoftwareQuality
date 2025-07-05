package org.example.controllers;

import org.example.studymaterial.AudioReference;
import org.example.studymaterial.Reference;
import org.example.studymaterial.TextReference;
import org.example.studymaterial.VideoReference;
import org.example.studyregistry.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.example.controllers.MainController.getInput;
import static org.example.controllers.MainController.validateInput;
import org.example.studymaterial.AudioEditData;

public class StudyRegistryController {
    private StudyTaskManager studyTaskManager = StudyTaskManager.getStudyTaskManager();
    private Map<String, Runnable> actions = new HashMap<>();

    public StudyRegistryController() {
        assignActions();
    }

    void assignActions(){
        actions.put("1", this::handleAddStudyTask);
        actions.put("2", this::handleAddStudyGoal);
        actions.put("3", this::handleAddStudyMaterial);
        actions.put("4", this::handleAddStudyObjective);
        actions.put("5", this::handleAddStudyPlan);
        actions.put("6", this::handleSetUpWeek);
        actions.put("7", this::handleGetWeekResponsibilities);
    }

    private String getInput() {
        return "";
    }

    private void handleMethodHeader(String header){
        System.out.println("~~~~" + header + "~~~~\n");
    }

    private Task getStudyTaskInfo(){
        System.out.println("Type the following info: title, description, author \n");
        String title = getInput();
        String description = getInput();
        String author = getInput();
        return new Task(title, description, author, LocalDateTime.now());
    }

    private void handleAddStudyTask(){
        Task task = getStudyTaskInfo();
        studyTaskManager.addRegistry(task);
    }

    private void handleSetObjective(StudyObjective objective){
        handleMethodHeader("(Study Objective Edit)");
        System.out.println("Type the following info: Integer id, Integer priority " +
                "Integer practicedDays, int day, int month, int year, String name, String title, String description, " +
                "String topic, String objectiveInOneLine, String objectiveFullDescription, String motivation, " +
                "Double duration, boolean isActive  \n");
        objective.handleSetObjective(Integer.parseInt(getInput()), Integer.parseInt(getInput()),Integer.parseInt(getInput()),Integer.parseInt(getInput()),Integer.parseInt(getInput()),
                Integer.parseInt(getInput()), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(), getInput(),
                Double.parseDouble(getInput()), Boolean.parseBoolean(getInput()));
    }

    private StudyObjective getStudyObjectiveInfo(){
        handleMethodHeader("(Study Objective Creation)");
        System.out.println("Type the following info: title, description \n");
        String title = getInput();
        String description = getInput();
        StudyObjective studyObjective = new StudyObjective(title, description);
        handleSetObjective(studyObjective);
        studyTaskManager.addRegistry(studyObjective);
        return studyObjective;
    }

    private StudyPlan getStudyPlanInfo(){
        handleMethodHeader("(Study Plan Creation)");
        System.out.println("Type the following info: name \n");
        String name = getInput();
        StudyObjective studyObjective = getStudyObjectiveInfo();
        StudyPlan plan = new StudyPlan(name, studyObjective,  new ArrayList<>());
        studyTaskManager.addRegistry(plan);
        return plan;
    }

    private void handleSetSteps(StudyPlan studyPlan){
        handleMethodHeader("(Study Plan Edit)");
        System.out.println("Type the following info: firstStep, resetStudyMechanism, consistentStep, seasonalSteps, basicSteps, mainObjectiveTitle, mainGoalTitle, mainMaterialTopic, mainTask, numberOfSteps, isImportant, daysUntilEnd");
        LocalDateTime now = LocalDateTime.now();

        // Coleta das 9 Strings
        List<String> props = Arrays.asList(
                getInput(), getInput(), getInput(), getInput(), getInput(),
                getInput(), getInput(), getInput(), getInput()
        );
        Integer numberOfSteps = Integer.parseInt(getInput());
        Boolean isImportant   = Boolean.parseBoolean(getInput());
        Long daysUntilEnd     = Long.parseLong(getInput());

        StudyPlanStepsData data = new StudyPlanStepsData.Builder()
                .withFirstStep(props.get(0))
                .withResetStudyMechanism(props.get(1))
                .withConsistentStep(props.get(2))
                .withSeasonalSteps(props.get(3))
                .withBasicSteps(props.get(4))
                .withMainObjectiveTitle(props.get(5))
                .withMainGoalTitle(props.get(6))
                .withMainMaterialTopic(props.get(7))
                .withMainTask(props.get(8))
                .withNumberOfSteps(numberOfSteps)
                .withIsImportant(isImportant)
                .withStartDate(now)
                .withEndDate(now.plusDays(daysUntilEnd))
                .build();

        studyPlan.handleAssignSteps(data);
    }

    private StudyGoal getStudyGoalInfo(){
        handleMethodHeader("(Study Goal Creation)");
        System.out.println("Type the following info: name \n");
        String name = getInput();
        StudyPlan studyPlan = getStudyPlanInfo();
        handleSetSteps(studyPlan);
        StudyObjective studyObjective = studyPlan.getObjective();
        return new StudyGoal(name, studyObjective, studyPlan);
    }

    private void handleAddStudyGoal(){
        StudyGoal goal = getStudyGoalInfo();
        studyTaskManager.addRegistry(goal);
    }

    // ✅ MÉTODO REFATORADO - Apenas 3 linhas (sem NcssCount)
    private void editAudio(AudioReference audioReference){
        handleMethodHeader("(Audio Edit)");
        System.out.println("Type the following info:  AudioReference. AudioQuality audioQuality, boolean isDownloadable, " +
                "String title, String description, String link, String accessRights, String license, String language, int rating, " +
                "int viewCount, int shareCount \n");
        new AudioEditor(audioReference).execute();
    }

    // ✅ METHOD OBJECT - Encapsula toda a lógica de coleta e edição
    private class AudioEditor {
        private final AudioReference audioReference;

        public AudioEditor(AudioReference audioReference) {
            this.audioReference = audioReference;
        }

        public void execute() {
            AudioEditData data = collectInputsAndBuildData();
            audioReference.editAudio(data);
        }

        private AudioEditData collectInputsAndBuildData() {
            return new AudioEditData.Builder()
                    .withAudioQuality(AudioReference.audioQualityAdapter(getInput()))
                    .isDownloadable(Boolean.parseBoolean(getInput()))
                    .withTitle(getInput())
                    .withDescription(getInput())
                    .withLink(getInput())
                    .withAccessRights(getInput())
                    .withLicense(getInput())
                    .withLanguage(getInput())
                    .withRating(Integer.parseInt(getInput()))
                    .withViewCount(Integer.parseInt(getInput()))
                    .withShareCount(Integer.parseInt(getInput()))
                    .build();
        }
    }

    private AudioReference addAudioReference(){
        handleMethodHeader("(Audio Reference Creation)");
        System.out.println("Type the following info: Audio Quality ( LOW | MEDIUM | HIGH | VERY_HIGH) \n");
        AudioReference audioReference = new AudioReference(AudioReference.audioQualityAdapter(getInput()));
        editAudio(audioReference);
        return audioReference;
    }

    private VideoReference addVideoReference(){
        handleMethodHeader("(Video Reference Creation)");
        System.out.println("Type the following info: boolean isAvailable, String title, " +
                "String description, String resolution, String frameRate, String videoFormat, String accessRights \n");
        return new VideoReference(Boolean.parseBoolean(getInput()), getInput(), getInput(), getInput(), getInput(),
                getInput(), getInput());
    }

    private TextReference addTextReference(){
        handleMethodHeader("(Text Reference Creation)");
        System.out.println("Type the following info:  String title, String language, int wordCount, String format, String accessRights \n");
        return new TextReference(getInput(), getInput(), Integer.parseInt(getInput()), getInput(),
                getInput());
    }

    private Reference addStudyMaterial() {
        // método mock para simular a criação de material de estudo
        return new TextReference("Título", "Português", 1000, "PDF", "Público");
    }

    private void handleAddStudyMaterial(){
        Reference reference = addStudyMaterial();
        if(reference != null){
            studyTaskManager.addReference(reference);
        }
    }

    private void handleAddStudyObjective(){
        getStudyObjectiveInfo();
    }

    private void handleAddStudyPlan(){
        getStudyPlanInfo();
        System.out.println("Study Plan Added");
    }

    private void getWeekInfo() {
        System.out.println("(Study Task Manager Week Set Up) Digite os seguintes dados:");
        WeekSetupData config = new WeekSetupData.Builder()
                .withPlanName(getInput())
                .withObjectiveTitle(getInput())
                .withObjectiveDescription(getInput())
                .withMaterialTopic(getInput())
                .withMaterialFormat(getInput())
                .withGoal(getInput())
                .withReminderTitle(getInput())
                .withReminderDescription(getInput())
                .withMainTaskTitle(getInput())
                .withMainHabit(getInput())
                .withMainCardStudy(getInput())
                .build();
        studyTaskManager.setUpWeek(config);
    }

    private void handleSetUpWeek() {
        getWeekInfo();
    }

    private void handleGetWeekResponsibilities(){
        List<String> responsibilities = studyTaskManager.getWeekResponsibilities();
        System.out.println(String.join(", ", responsibilities));
    }

    public void handleRegistryInput(){
        try{
            while(true){
                controllerOptions();
                String response = validateInput(actions);
                if(response == null) {return;}
                actions.get(response).run();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void controllerOptions(){
        System.out.println("""
                0 - return
                1 - add study task
                2 - add study goal
                3 - add study material (audio, video, text)
                4 - add study objective
                5 - add study plan
                6 - set up week
                7 - get week responsibilities
               """);
    }
}