package com.thoughtworks.frankenstein.events;

import com.thoughtworks.frankenstein.common.RobotFactory;
import com.thoughtworks.frankenstein.playback.ComponentFinder;
import com.thoughtworks.frankenstein.playback.WindowContext;
import org.jmock.Mock;


/**
 * Ensures behaviour of DebugDumpEvent
 *
 * @author Korny Sietsma
 */
public class DebugDumpEventTest extends AbstractEventTestCase {
    public void testEqualsAndHashCode() {
        DebugDumpEvent one = new DebugDumpEvent("");
        DebugDumpEvent two = new DebugDumpEvent("");
        assertEquals(one, two);
        assertEquals(one.hashCode(), two.hashCode());
    }

    public void testToString() {
        assertEquals("DebugDumpEvent", new DebugDumpEvent("").toString());
    }

    public void testAction() {
        assertEquals("DebugDump", new DebugDumpEvent("").action());
    }

    public void testTarget() {
        assertEquals("", new DebugDumpEvent("").target());
    }

    public void testParameters() {
        assertEquals("", new DebugDumpEvent("").parameters());
    }

    public void testScriptLine() {
        assertEquals("debug_dump", new DebugDumpEvent("").scriptLine());
    }

    public void testScriptLineInJava() {
        assertEquals("debugDump()", new DebugDumpEvent("").scriptLine(new JavaScriptStrategy()));
    }

    public void testPlaysEvent() throws Exception {
        Mock mockComponentFinder = mock(ComponentFinder.class);
        Mock mockContext = mock(WindowContext.class);
        WindowContext context = (WindowContext) mockContext.proxy();
        DebugDumpEvent DebugDump = new DebugDumpEvent("dialog");
        mockContext.expects(once()).method("debugDump");
        DebugDump.play(context, (ComponentFinder) mockComponentFinder.proxy(), null, RobotFactory.getRobot());
    }

    protected FrankensteinEvent createEvent() {
        return new DebugDumpEvent("");
    }
}
