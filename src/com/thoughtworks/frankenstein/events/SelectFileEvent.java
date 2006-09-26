package com.thoughtworks.frankenstein.events;

import com.thoughtworks.frankenstein.playback.WindowContext;
import com.thoughtworks.frankenstein.playback.ComponentFinder;
import com.thoughtworks.frankenstein.recorders.ScriptContext;

import java.awt.*;
import java.io.File;

/**
 * Understands selecting a file from a filechooser.
 */
public class SelectFileEvent extends AbstractFrankensteinEvent {

    private String fileName;

    public SelectFileEvent(String fileName) {
        this.fileName = fileName;
    }

    public String toString() {
        return "SelectFileEvent: " + fileName;
    }

    public String target() {
        return fileName;
    }

    public void run() {
        finder.findFileChooser(context).setSelectedFile(new File(fileName));
    }
}
