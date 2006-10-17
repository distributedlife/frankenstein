package com.thoughtworks.frankenstein.application;

import javax.swing.*;

import com.thoughtworks.frankenstein.playback.DefaultComponentFinder;
import com.thoughtworks.frankenstein.playback.DefaultWindowContext;
import com.thoughtworks.frankenstein.playback.WindowContext;
import com.thoughtworks.frankenstein.playback.ComponentFinder;
import com.thoughtworks.frankenstein.recorders.DefaultRecorder;
import com.thoughtworks.frankenstein.recorders.Recorder;
import com.thoughtworks.frankenstein.recorders.DefaultComponentVisibility;
import com.thoughtworks.frankenstein.recorders.DefaultScriptContext;
import com.thoughtworks.frankenstein.ui.DefaultFileDialogLauncher;
import com.thoughtworks.frankenstein.ui.RecorderPane;
import com.thoughtworks.frankenstein.ui.RecorderTableModel;
import com.thoughtworks.frankenstein.common.DefaultComponentDecoder;
import com.thoughtworks.frankenstein.naming.NamingStrategy;
import com.thoughtworks.frankenstein.naming.DefaultNamingStrategy;
import com.thoughtworks.frankenstein.script.HtmlTestReporter;
import spin.over.CheckingRepaintManager;

/**
 * Facade for the framework.
 * @author Vivek Prahlad
 */
public class FrankensteinIntegration {
    private Recorder eventRecorder;
    private DefaultFrankensteinRecorder recorder;
    private Class mainClass;
    private JFrame frame;
    private WindowContext context;
    private SocketListener socketListener;

    public FrankensteinIntegration(Class mainClass,
                                   JFrame frame,
                                   WorkerThreadMonitor monitor,
                                   WindowContext context,
                                   NamingStrategy namingStrategy) {
        this(mainClass, frame, monitor, context, namingStrategy, new DefaultComponentFinder(namingStrategy));
    }

    public FrankensteinIntegration(Class mainClass,
                                   JFrame frame,
                                   WorkerThreadMonitor monitor,
                                   WindowContext context,
                                   NamingStrategy namingStrategy,
                                   ComponentFinder finder) {
        this.mainClass = mainClass;
        this.frame = frame;
        this.context = context;
        eventRecorder = new DefaultRecorder(new DefaultScriptContext(new HtmlTestReporter(), monitor, context, finder));
        recorder = new DefaultFrankensteinRecorder(eventRecorder, new DefaultComponentDecoder(), new DefaultComponentVisibility(), namingStrategy);
        socketListener = new SocketListener(recorder);
        createRecorderUI(recorder);
        RepaintManager.setCurrentManager(new CheckingRepaintManager());
    }

    public FrankensteinIntegration(Class mainClass, WorkerThreadMonitor monitor) {
        this(mainClass, new JFrame("Recorder"), monitor, new DefaultWindowContext(), new DefaultNamingStrategy());
    }

    public FrankensteinIntegration(Class mainClass) {
        this(mainClass, new JFrame("Recorder"), new NullWorkerThreadMonitor(), new DefaultWindowContext(), new DefaultNamingStrategy());
    }

    public void registerEvent(Class eventClass) {
        recorder.registerEvent(eventClass);
    }

    public void registerRecorder(Class recorderClass) {
        recorder.registerRecorder(recorderClass);
    }

    public void start(String[] args) {
        recorder.startRecording();
        socketListener.start();
        try {
            mainClass.getDeclaredMethod("main", new Class[] {String[].class}).invoke(null, new Object[] {args});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createRecorderUI(FrankensteinRecorder compositeRecorder) {
        frame.getContentPane().add(new RecorderPane(compositeRecorder, new DefaultFileDialogLauncher(), new RecorderTableModel(eventRecorder)));
        frame.pack();
        frame.setVisible(true);
    }

    public void stop() {
        context.close();
        recorder.stopRecording();
        socketListener.stop();
        frame.dispose();
    }
}
