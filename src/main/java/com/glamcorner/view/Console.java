package com.glamcorner.view;

public interface Console {

    void reportMove(int chosenMove, String name);

    int getIntAnswer(String question);

    void reportToUser(String message);

    String getAnswer(String question);
}
