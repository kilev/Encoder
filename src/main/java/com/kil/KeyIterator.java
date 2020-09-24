package com.kil;

import lombok.Data;

import javax.annotation.concurrent.NotThreadSafe;

@Data
@NotThreadSafe
public class KeyIterator {
    private final String key;
    private int currentKeyIndex = 0;

    public char getCharacter() {
        if (key.length() == currentKeyIndex) {
            currentKeyIndex = 0;
        }

        return key.charAt(currentKeyIndex++);
    }
}
