package com.thoughtworks.frankenstein.common;

import java.awt.*;

/**
 * Understands decoding strings from renderer components
 * @author Vivek Prahlad
 */
public interface ComponentDecoder {
    String decode (Component renderer);
}
