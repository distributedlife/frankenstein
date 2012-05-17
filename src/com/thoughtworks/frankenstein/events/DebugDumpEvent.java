package com.thoughtworks.frankenstein.events;

/**
 * Closes all open dialogs recursively.
 *
 * @author Korny Sietsma
 */
public class DebugDumpEvent extends AbstractFrankensteinEvent {

    public DebugDumpEvent(String scriptLine) {
        executeInPlayerThread();
    }

    public String toString() {
        return "DebugDumpEvent";
    }

    public String target() {
        return "";
    }

    public void run() {
        context.debugDump();
    }
}
