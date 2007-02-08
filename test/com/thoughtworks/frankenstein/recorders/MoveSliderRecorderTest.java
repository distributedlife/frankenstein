package com.thoughtworks.frankenstein.recorders;

import javax.swing.*;

import com.thoughtworks.frankenstein.events.MoveSliderEvent;
import com.thoughtworks.frankenstein.naming.DefaultNamingStrategy;


/**
    Ensures SliderRecorders behavior
 */
public class MoveSliderRecorderTest extends AbstractRecorderTestCase {
    JSlider slider;
    MoveSliderRecorder recorder;

    protected void setUp() throws Exception {
        super.setUp();
        slider=new JSlider(1,100,5);
        slider.setName("slider");
        JScrollPane scrollPane = new JScrollPane(slider, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        new JFrame().getContentPane().add(scrollPane);
        recorder = new MoveSliderRecorder((Recorder) mockRecorder.proxy(), new DefaultNamingStrategy());
    }


    public void testAddsChangeListenerWhenSliderIsShown() {
        int listenerCount = listenerCount();
        recorder.componentShown(slider);
        assertTrue(listenerCount() == listenerCount + 1);
    }

     public void testRemovesChangeListenerWhenSliderIsHidden() {
        int listenerCount = listenerCount();
        recorder.componentShown(slider);
        recorder.componentHidden(slider);
        assertTrue(listenerCount() == listenerCount);
    }

     public void testRecordsMovementOnSlider() {
        recorder.componentShown(slider);
        mockRecorder.expects(once()).method("record").with(eq(new MoveSliderEvent("slider",10)));
        slider.setValue(10);
    }

    private int listenerCount() {
        return slider.getChangeListeners().length;
    }
}
