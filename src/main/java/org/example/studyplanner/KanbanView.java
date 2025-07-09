package org.example.studyplanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KanbanView {
    public enum State {
        TODO, DOING, DONE;
    }

    private HabitTracker habitTracker;
    private TodoTracker todoTracker;
    private Map<State, List<PlannerMaterial>> kanban;

    public KanbanView(HabitTracker habitTracker, TodoTracker todoTracker) {
        this.habitTracker = habitTracker;
        this.todoTracker = todoTracker;
        this.kanban = initializeKanban();
    }

    public List<PlannerMaterial> getKanbanByState(State state) {
        return kanban.get(state);
    }

    public void addHabitToKanban(State state, Integer id) throws Exception {
        PlannerMaterial habit = getHabitOrThrow(id);
        addToKanban(state, habit);
    }

    public void addToDoToKanban(State state, Integer id) throws Exception {
        PlannerMaterial todo = getToDoOrThrow(id);
        addToKanban(state, todo);
    }

    public void removeHabitFromKanban(State state, Integer id) throws Exception {
        PlannerMaterial habit = getHabitOrThrow(id);
        removeFromKanban(state, habit);
    }

    public void removeToDoFromKanban(State state, Integer id) throws Exception {
        PlannerMaterial todo = getToDoOrThrow(id);
        removeFromKanban(state, todo);
    }

    public String kanbanView() throws Exception {
        if (kanban.isEmpty()) {
            throw new Exception("No material found");
        }
        return new KanbanRenderer(kanban).render();
    }

    // ==== Métodos auxiliares extraídos ====

    private Map<State, List<PlannerMaterial>> initializeKanban() {
        Map<State, List<PlannerMaterial>> board = new HashMap<>();
        for (State state : State.values()) {
            board.put(state, new ArrayList<>());
        }
        return board;
    }

    private Habit getHabitOrThrow(Integer id) throws Exception {
        Habit habit = habitTracker.getHabitById(id);
        if (habit == null) {
            throw new Exception("Habit not found with id: " + id);
        }
        return habit;
    }

    private ToDo getToDoOrThrow(Integer id) throws Exception {
        ToDo todo = todoTracker.getToDoById(id);
        if (todo == null) {
            throw new Exception("ToDo not found with id: " + id);
        }
        return todo;
    }

    private void addToKanban(State state, PlannerMaterial item) {
        kanban.get(state).add(item);
    }

    private void removeFromKanban(State state, PlannerMaterial item) {
        kanban.get(state).remove(item);
    }
}
