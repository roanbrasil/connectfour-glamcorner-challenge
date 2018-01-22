package com.glamcorner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Move {
    private int value; // Game value of this move
    private int move; // Number of pit to be emptied
}
