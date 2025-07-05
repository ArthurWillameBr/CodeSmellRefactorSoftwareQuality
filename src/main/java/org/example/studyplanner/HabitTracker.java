package org.example.studyplanner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HabitTracker {
    private List<Habit> habits = new ArrayList<>();
    private Map<Integer, List<LocalDateTime>> tracker = new HashMap<>();
    private int nextId = 1;
    private static HabitTracker instance;

    public static HabitTracker getHabitTracker() {
        if (instance == null) {
            instance = new HabitTracker();
        }
        return instance;
    }

    private HabitTracker(){
        this.habits = new ArrayList<>();
        this.tracker = new HashMap<>();
        this.nextId = 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Habits: ");
        habits.forEach(h -> sb.append(h).append(", "));
        return sb.toString();
    }

    public Habit getHabitById(int id) {
        return habits.stream()
                .filter(h -> h.getId() == id)
                .findFirst().orElse(null);
    }

    public List<Habit> getHabits() {
        return Collections.unmodifiableList(habits);
    }

    public String formatHabitDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public List<Integer> getTrackerKeys(){
        return this.tracker.keySet().stream().toList();
    }

    /**
     * Refatorado: recebe HabitCreationData ao invés de 11 parâmetros.
     */
    public int addHabit(HabitCreationData data) {
        Habit habit = new Habit(
                data.getName(),
                data.getMotivation(),
                data.getDailyDedicationTime(),
                nextId,
                data.getStartDate(),
                data.isConcluded()
        );
        habits.add(habit);
        tracker.put(nextId, new ArrayList<>());
        return nextId++;
    }

    public int addHabit(String name, String motivation) {
        Habit habit = new Habit(name, motivation, nextId);
        habits.add(habit);
        tracker.put(nextId, new ArrayList<>());
        return nextId++;
    }

    public int handleAddHabitAdapter(List<String> stringProps, List<Integer> intProps, boolean isConcluded) {
        HabitCreationData data = new HabitCreationData.Builder()
                .withName(stringProps.get(0))
                .withMotivation(stringProps.get(1))
                .withDailyDedication(intProps.get(1), intProps.get(0))
                .withStartDate(LocalDateTime.of(
                        intProps.get(2), intProps.get(3), intProps.get(4),
                        intProps.get(5), intProps.get(6), intProps.get(7)
                ))
                .concluded(isConcluded)
                .build();
        return addHabit(data);
    }

    public void removeHabit(int id) {
        habits.removeIf(h -> h.getId() == id);
        tracker.remove(id);
    }

    public void addHabitRecord(Integer id){
        tracker.get(id).add(LocalDateTime.now());
    }

    public void toggleConcludeHabit(Integer id) {
        for (Habit habit : this.habits) {
            if (habit.getId().equals(id)) {
                habit.setIsConcluded(!habit.getIsConcluded());
            }
        }
    }

    public void removeHabit(Integer id) {
        this.habits.removeIf(habit -> habit.getId().equals(id));
        this.tracker.remove(id);
    }

    public List<LocalDateTime> getHabitRecords(Integer id) {
        return this.tracker.get(id);
    }

    public List<String> searchInHabits(String search){
        List<String> habits = new ArrayList<>();
        for (Habit habit : this.habits) {
            if (habit.getName().toLowerCase().contains(search.toLowerCase()) ||
                    habit.getMotivation().toLowerCase().contains(search.toLowerCase())) {
                habits.add(habit.toString());
            }
        }
        return habits;
    }
}
