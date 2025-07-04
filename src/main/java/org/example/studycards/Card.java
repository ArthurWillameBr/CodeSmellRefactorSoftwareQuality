package org.example.studycards;

public class Card {
    private String question;
    private String answer;

    public Card(String question, String answer) {
        // Validation logic is now inside the class, ensuring a valid state upon creation.
        validate(question, answer);
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        validate(question, this.answer);
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        validate(this.question, answer);
        this.answer = answer;
    }

    public void edit(String question, String answer) {
        // The edit method also uses the internal validation.
        validate(question, answer);
        this.question = question;
        this.answer = answer;
    }

    /**
     * MOVED METHOD
     * Checks if the question or answer contains the search string.
     * This logic was moved from CardManager.searchInCards().
     */
    public boolean contains(String search) {
        if (search == null || search.trim().isEmpty()) {
            return false;
        }
        return question.toLowerCase().contains(search.toLowerCase()) ||
                answer.toLowerCase().contains(search.toLowerCase());
    }

    /**
     * MOVED METHOD (Logic)
     * Overrides toString() to provide a standard representation of a Card's content.
     * This logic was previously in CardManager.formatCard().
     */
    @Override
    public String toString() {
        return "Question: " + this.question + " Answer: " + this.answer;
    }

    /**
     * MOVED METHOD
     * Validates the card's data. Throws an exception for invalid state.
     * This logic was moved from CardManager.validateCard().
     */
    private void validate(String question, String answer) {
        if (question == null || question.trim().isEmpty() || answer == null || answer.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid question or answer");
        }
    }
}