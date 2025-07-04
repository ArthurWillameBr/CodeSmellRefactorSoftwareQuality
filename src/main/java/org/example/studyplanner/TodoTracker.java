package org.example.studyplanner;

import java.util.ArrayList;
import java.util.List;

public class TodoTracker {
    private final List<ToDo> toDos = new ArrayList<>();
    private Integer nextId = 1;
    private static TodoTracker instance;

    private TodoTracker() {}

    public static TodoTracker getInstance() {
        if (instance == null) {
            instance = new TodoTracker();
        }
        return instance;
    }

    public Integer addToDo(String title, String description, int priority) {
        ToDo toAdd = new ToDo(nextId, title, description, priority);
        nextId++;
        toDos.add(toAdd);
        return toAdd.getId();
    }

    public void removeToDo(Integer id) {
        toDos.removeIf(t -> t.getId().equals(id));
    }

    public void addToDoExecutionTime(Integer id) {
        ToDo td = getToDoById(id);
        if (td != null) td.addExecutionTime();
    }

    public ToDo getToDoById(Integer id) {
        return toDos.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<ToDo> getToDos() {
        return new ArrayList<>(toDos);
    }

    public List<ToDo> sortTodosByPriority() {
        List<ToDo> sorted = new ArrayList<>(toDos);
        sorted.sort((a, b) -> Integer.compare(a.getPriority(), b.getPriority()));
        return sorted;
    }

    public List<String> searchInTodos(String search) {
        List<String> matched = new ArrayList<>();
        for (ToDo t : toDos) {
            if (t.matches(search)) matched.add(t.toString());
        }
        return matched;
    }

    /**
     * Retorna todas as ToDos formatadas em string, incluindo execuções.
     */
    @Override
    public String toString() {
        if (toDos.isEmpty()) {
            return "No ToDos found";
        }
        StringBuilder sb = new StringBuilder();
        for (ToDo t : toDos) {
            sb.append(t.toString());
        }
        return sb.toString();
    }
}
