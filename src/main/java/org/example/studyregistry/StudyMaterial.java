package org.example.studyregistry;

import org.example.studymaterial.AudioReference;
import org.example.studymaterial.Reference;
import org.example.studymaterial.TextReference;
import org.example.studymaterial.VideoReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyMaterial {
    List<Reference> references;
    private static StudyMaterial studyMaterial;
    private Map<String, Integer> referenceCount;

    private StudyMaterial() {
        references = new ArrayList<>();
    }

    public static StudyMaterial getStudyMaterial() {
        if (studyMaterial == null) {
            studyMaterial = new StudyMaterial();
        }
        return studyMaterial;
    }

    public void addReference(Reference ref) {
        references.add(ref);
    }

    List<Reference> getReferences() {
        return references;
    }

    public List<Reference> getTypeReference(Reference type) {
        List<Reference> response = new ArrayList<>();
        for (Reference reference : references) {
            if (reference.getClass() == type.getClass()) {
                response.add(reference);
            }
        }
        return response;
    }

    public void setReferenceCount(Map<String, Integer> referenceCount) {
        this.referenceCount = referenceCount;
    }

    public List<String> searchInMaterials(String text) {
        List<String> response = new ArrayList<>();
        for (Reference reference : references) {
            String mix = (reference.getTitle() != null ? reference.getTitle() : "") +
                    (reference.getDescription() != null ? reference.getDescription() : "");
            if (mix.toLowerCase().contains(text.toLowerCase())) {
                response.add(reference.getTitle());
            }
        }
        return response;
    }

    public Map<String, Integer> getReferenceCountMap() {
        Map<String, Integer> response = initializeCountMap();
        countAudioReferences(response);
        countVideoReferences(response);
        countTextReferences(response);
        setReferenceCount(response);
        return response;
    }

    private Map<String, Integer> initializeCountMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Audio References", 0);
        map.put("Video References", 0);
        map.put("Text References", 0);
        return map;
    }

    private void countAudioReferences(Map<String, Integer> map) {
        for (Reference reference : references) {
            if (reference instanceof AudioReference) {
                map.put("Audio References", map.get("Audio References") + 1);
            }
        }
    }

    private void countVideoReferences(Map<String, Integer> map) {
        for (Reference reference : references) {
            if (reference instanceof VideoReference videoRef && videoRef.handleStreamAvailability()) {
                map.put("Video References", map.get("Video References") + 1);
            }
        }
    }

    private void countTextReferences(Map<String, Integer> map) {
        for (Reference reference : references) {
            if (reference instanceof TextReference textRef && textRef.handleTextAccess()) {
                map.put("Text References", map.get("Text References") + 1);
            }
        }
    }
}
