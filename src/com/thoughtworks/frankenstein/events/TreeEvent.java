package com.thoughtworks.frankenstein.events;

import java.awt.*;
import javax.swing.*;

import com.thoughtworks.frankenstein.events.actions.Action;
import com.thoughtworks.frankenstein.recorders.EventList;

/**
 * Understands actions on trees
 *
 * @author Vivek Prahlad
 */
public class TreeEvent extends AbstractCompoundEvent {
    private String treeName;
    private String[] path;

    public TreeEvent(String treeName, String[] path, Action action) {
        super(action);
        this.treeName = treeName;
        this.path = path;
    }

    public TreeEvent(String scriptLine, Action action) {
        this(TreeUtil.name(scriptLine), TreeUtil.path(scriptLine), action);
    }

    public void record(EventList list, FrankensteinEvent lastEvent) {
        if (lastEvent instanceof SelectTreeEvent) {
            list.replaceLastEvent(this);
        } else {
            super.record(list, lastEvent);
        }
    }

    public String toString() {
        return action.name() + "TreeEvent: Tree: " + treeName + ", Path: " + parameters();
    }

    public String target() {
        return treeName;
    }

    public String scriptLine() {
        return (underscore(action()) + " \"" + target() + "\"," + TreeUtil.pathString(path, ",", "\""));
    }

    public String parameters() {
        return TreeUtil.pathString(path, ">");
    }

    public void run() {
        new SelectTreeEvent(treeName, path).play(context, finder, scriptContext, robot);
        JTree tree = (JTree) finder.findComponent(context, treeName);
        action.execute(location(tree), tree, finder, context);
    }

    private Point location(JTree tree) {
        Point treeLocation = tree.getLocation();
        Rectangle pathBounds = tree.getPathBounds(tree.getSelectionPath());
        Point nodeLocation = new Point(pathBounds.x + pathBounds.width / 2, pathBounds.y + pathBounds.height / 2);
        treeLocation.translate(nodeLocation.x, nodeLocation.y);
        return treeLocation;
    }

    public String scriptLine(ScriptStrategy scriptStrategy) {
        return scriptStrategy.toMethod(action()) + scriptStrategy.enclose(quote(target()) + ", " + scriptStrategy.array(path));
    }
}
