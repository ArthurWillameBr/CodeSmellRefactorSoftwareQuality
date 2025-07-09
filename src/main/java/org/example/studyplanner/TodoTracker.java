package org.example.studyplanner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TodoTracker {
    private List<ToDo> toDos = new ArrayList<>();
    private Map<Integer, List<LocalDateTime>> tracker;
    private Integer nextId;
    private static TodoTracker instance;

    private TodoTracker() {
        this.tracker = new HashMap<>();
        this.toDos = new ArrayList<>();
        this.nextId = 1;
    }

    public static TodoTracker getInstance() {
        if (instance == null) {
            instance = new TodoTracker();
        }
        return instance;
    }

    @Override
    public String toString() {
        if (toDos.isEmpty()) {
            return "No ToDos found";
        }

        StringBuilder str = new StringBuilder();
        for (ToDo toDo : toDos) {
            appendToDoInfo(str, toDo);
        }
        return str.toString();
    }

    private void appendToDoInfo(StringBuilder str, ToDo toDo) {
        str.append(toDo.toString()).append("\n");
        List<LocalDateTime> executionTimes = tracker.get(toDo.getId());

        if (executionTimes == null || executionTimes.isEmpty()) {
            str.append("No tracks found\n");
        } else {
            appendFormattedExecutionTimes(str, executionTimes);
        }
    }

    private void appendFormattedExecutionTimes(StringBuilder str, List<LocalDateTime> executionTimes) {
        for (LocalDateTime ldt : executionTimes) {
            str.append(formatDateTime(ldt)).append("\n");
        }
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(dateTime);
    }

    public void addToDoExecutionTime(Integer id) {
        List<LocalDateTime> et = tracker.computeIfAbsent(id, k -> new ArrayList<>());
        et.add(LocalDateTime.now());
    }

    public List<ToDo> getToDos() {
        return toDos;
    }

    public ToDo getToDoById(Integer id) {
        for (ToDo toDo : toDos) {
            if (toDo.getId() == id) {
                return toDo;
            }
        }
        return null;
    }

    public Integer addToDo(String title, String description, Integer priority) {
        ToDo toAdd = new ToDo(nextId, title, description, priority);
        nextId++;
        toDos.add(toAdd);
        return toAdd.getId();
    }

    public void removeToDo(Integer id) {
        toDos.removeIf(toDo -> toDo.getId() == id);
    }

    public List<ToDo> sortTodosByPriority() {
        List<ToDo> sortedToDos = new ArrayList<>(toDos);
        sortedToDos.sort(Comparator.comparingInt(ToDo::getPriority));
        return sortedToDos;
    }

    public List<String> searchInTodos(String search) {
        List<String> todos = new ArrayList<>();
        for (ToDo toDo : toDos) {
            if (matchesSearch(toDo, search)) {
                todos.add(toDo.toString());
            }
        }
        return todos;
    }

    private boolean matchesSearch(ToDo toDo, String search) {
        String lowerSearch = search.toLowerCase();
        return toDo.getTitle().toLowerCase().contains(lowerSearch)
                || toDo.getDescription().toLowerCase().contains(lowerSearch);
    }
}
