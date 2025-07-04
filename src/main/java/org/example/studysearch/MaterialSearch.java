package org.example.studysearch;

import org.example.studyregistry.StudyMaterial;

import java.util.ArrayList;
import java.util.List;

public class MaterialSearch implements Search<String> {
    private final SearchLog searchLog = new SearchLog("Material Search");

    public MaterialSearch() {}

    @Override
    public List<String> search(String text) {
        List<String> results = new ArrayList<>();
        results.addAll(StudyMaterial.getStudyMaterial().searchInMaterials(text));
        results.add(searchLog.recordSearch(text));
        return results;
    }

    /**
     * Legacy accessor for controller compatibility.
     */
    public SearchLog getSearchLog() {
        return searchLog;
    }
}
