package com.thoughtworks.frankenstein.events;

import org.jmock.MockObjectTestCase;
import org.jmock.Mock;
import com.thoughtworks.frankenstein.playback.WindowContext;

/**
 * Ensures behaviour of DialogShownEvent
 */
public class DialogClosedEventTest extends MockObjectTestCase {

    public void testEqualsAndHashCode() {
        DialogClosedEvent eventOne = new DialogClosedEvent("title");
        DialogClosedEvent eventTwo = new DialogClosedEvent("title");
        assertEquals(eventOne, eventTwo);
        assertEquals(eventOne.hashCode(), eventTwo.hashCode());
    }

    public void testToString() {
        assertEquals("DialogClosedEvent: title", new DialogClosedEvent("title").toString());
    }

    public void testAction() {
        assertEquals("DialogClosed", new DialogClosedEvent("testDialog").action());
    }

    public void testTarget() {
        assertEquals("testDialog", new DialogClosedEvent("testDialog").target());
    }

    public void testParameters() {
        assertEquals("", new DialogClosedEvent("testDialog").parameters());
    }

    public void testScriptLine() {
        assertEquals("dialog_closed \"testDialog\"", new DialogClosedEvent("testDialog").scriptLine());
    }

    public void testNoErrorIfTitleMatches() {
        DialogClosedEvent event = new DialogClosedEvent("title");
        Mock mockWindownContext = mock(WindowContext.class);
        mockWindownContext.expects(once()).method("waitForDialogClosing").with(eq("title"), eq(10));
        event.play((WindowContext) mockWindownContext.proxy(), null, null, null);
    }
}
