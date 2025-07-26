package org.example.studycards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardManager {
    private Map<Integer, Card> cards;
    private Integer nextID = 1;

    private static CardManager instance = null;

    private CardManager() {
        this.cards = new HashMap<Integer, Card>();
    }

    public static CardManager getCardManager() {
        if (instance == null) {
            instance = new CardManager();
        }
        return instance;
    }

    public String formatCard(Integer id) {
        Card card = this.getCard(id);
        if (card == null) {
            return "[id: " + id + "] Card not found.";
        }
        return "[id: " + id + "] " + card.toString();
    }

    public Map<Integer, Card> getCardsMap(){
        return cards;
    }

    public List<Card> getCards() {
        return new ArrayList<Card>(cards.values());
    }

    public List<Card> getCards(List<Integer> ids){
        List<Card> responseCards = new ArrayList<>();
        for (Integer id : ids) {
            Card card = cards.get(id);
            if (card != null) {
                responseCards.add(card);
            }
        }
        return responseCards;
    }

    public Card getCard(Integer id) {
        return cards.get(id);
    }

    public Integer addCard(String question, String answer) {
        Card card = new Card(question, answer); // Throws IllegalArgumentException if data is invalid
        Integer response = nextID;
        cards.put(nextID, card);
        nextID++;
        return response;
    }

    public void removeCard(Integer id) {
        cards.remove(id);
    }

    public void updateCard(Integer id, String question, String answer) {
        Card card = cards.get(id);
        if (card != null) {
            card.edit(question, answer); // Throws IllegalArgumentException if data is invalid
        }
    }

    private boolean validateCard(String question, String answer) {
        return question == null || question.isEmpty() || answer == null || answer.isEmpty();
    }

    public List<String> searchInCards(String search) {
        List<String> responseCards = new ArrayList<>();
        for (Map.Entry<Integer, Card> entry : cards.entrySet()) {
            Card card = entry.getValue();
            if (card.contains(search)) {
                responseCards.add(formatCard(entry.getKey()));
            }
        }
        return responseCards;
    }
}