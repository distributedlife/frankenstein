package com.thoughtworks.frankenstein.events;

import javax.swing.*;

import org.jmock.Mock;

import com.thoughtworks.frankenstein.common.WaitForIdle;
import com.thoughtworks.frankenstein.playback.ComponentFinder;
import com.thoughtworks.frankenstein.playback.WindowContext;
import com.thoughtworks.frankenstein.recorders.TestTableModel;

/**
 * Ensures behaviour of ButtonEvent
 */
public class EditTableCellEventTest extends AbstractEventTestCase {

    public void testEqualsAndHashCode() {
        EditTableCellEvent eventOne = new EditTableCellEvent("parent.tableName", 1, 2);
        EditTableCellEvent eventTwo = new EditTableCellEvent("parent.tableName", 1, 2);
        assertEquals(eventOne, eventTwo);
        assertEquals(eventOne.hashCode(), eventTwo.hashCode());
        assertFalse(eventOne.equals(null));
    }

    public void testToString() {
        assertEquals("EditTableCellEvent: Table: parent.tableName cell: (1,2)", new EditTableCellEvent("parent.tableName", 1, 2).toString());
    }

    public void testAction() {
        assertEquals("EditTableCell", new EditTableCellEvent("testTableName", 1, 1).action());
    }

    public void testTarget() {
        assertEquals("testTableName", new EditTableCellEvent("testTableName", 1, 1).target());
    }

    public void testParameters() {
        assertEquals("1,2", new EditTableCellEvent("testTableName", 1, 2).parameters());
    }

    public void testScriptLine() {
        assertEquals("edit_table_cell \"testTableName\" , \"1,2\"", new EditTableCellEvent("testTableName", 1, 2).scriptLine());
    }

    public void testPlaysEvent() {
        EditTableCellEvent event = new EditTableCellEvent("parent.tableName", 1, 1);
        Mock mockComponentFinder = mock(ComponentFinder.class);
        Mock mockContext = mock(WindowContext.class);
        WindowContext context = (WindowContext) mockContext.proxy();
        final JTable table = new JTable(new TestTableModel());
        assertFalse(table.isEditing());
        mockComponentFinder.expects(once()).method("findComponent").with(same(context), eq("parent.tableName")).will(returnValue(table));
        mockComponentFinder.expects(once()).method("setTableCellEditor").with(ANYTHING);
        event.play(context, (ComponentFinder) mockComponentFinder.proxy(), null, null);
        new WaitForIdle().waitForIdle();
        assertTrue(table.isEditing());
        assertEquals(1, table.getEditingRow());
        assertEquals(1, table.getEditingColumn());
    }

    protected FrankensteinEvent createEvent() {
        return new EditTableCellEvent("parent.tableName", 1, 2);
    }
}
