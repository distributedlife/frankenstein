package com.thoughtworks.frankenstein.script;

import java.io.IOException;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.thoughtworks.frankenstein.events.FrankensteinEvent;

/**
 * Ensures behaviour of HtmlTestReporter
 */
public class HtmlTestReporterTest extends MockObjectTestCase {
    private HtmlTestReporter reporter;

    protected void setUp() throws Exception {
        super.setUp();
        reporter = new HtmlTestReporter();
    }

    public void testReportsTestSuccess() throws IOException {
        Mock frankensteinEvent = mock(FrankensteinEvent.class);
        frankensteinEvent.expects(once()).method("action").will(returnValue("Action"));
        frankensteinEvent.expects(once()).method("target").will(returnValue("Target"));
        frankensteinEvent.expects(once()).method("parameters").will(returnValue("Parameters"));
        reporter.startTest("testName");
        reporter.reportSuccess((FrankensteinEvent) frankensteinEvent.proxy());
        reporter.finishTest();
        assertEquals("<html>\n<head><title>testName</title></head>\n<body>\n<h3>testName</h3>\n" +
                "<h4>Test Status</h4>\n" +
                "<table BORDER CELLSPACING=0 CELLPADDING=4>\n" +
                "<tr><td bgcolor=#CFFFCF><font size=2 color=black>Action</font></td>" +
                "<td bgcolor=#CFFFCF><font size=2 color=black>Target</font></td>" +
                "<td bgcolor=#CFFFCF><font size=2 color=black>Parameters</font></td></tr>\n" +
                "</table>\n" +
                "</body>", reporter.report());
    }

    public void testReportsTestFailure() throws IOException {
        Mock frankensteinEvent = mock(FrankensteinEvent.class);
        frankensteinEvent.expects(once()).method("action").will(returnValue("Action"));
        frankensteinEvent.expects(once()).method("target").will(returnValue("Target"));
        frankensteinEvent.expects(once()).method("parameters").will(returnValue("Parameters"));
        reporter.startTest("testName");
        reporter.reportFailure((FrankensteinEvent) frankensteinEvent.proxy(), null);
        reporter.finishTest();
        assertEquals("<html>\n<head><title>testName</title></head>\n<body>\n<h3>testName</h3>\n" +
                "<h4>Test Status</h4>\n" +
                "<table BORDER CELLSPACING=0 CELLPADDING=4>\n" +
                "<tr><td bgcolor=#FFCFCF><font size=2 color=black>Action</font></td>" +
                "<td bgcolor=#FFCFCF><font size=2 color=black>Target</font></td>" +
                "<td bgcolor=#FFCFCF><font size=2 color=black>Parameters</font></td></tr>\n" +
                "</table>\n" +
                "</body>", reporter.report());
    }

    public void testSubstitutesNbspForEmptyStrings() throws IOException {
        Mock frankensteinEvent = mock(FrankensteinEvent.class);
        frankensteinEvent.expects(once()).method("action").will(returnValue("Action"));
        frankensteinEvent.expects(once()).method("target").will(returnValue("Target"));
        frankensteinEvent.expects(once()).method("parameters").will(returnValue(""));
        reporter.startTest("testName");
        reporter.reportFailure((FrankensteinEvent) frankensteinEvent.proxy(), null);
        reporter.finishTest();
        assertEquals("<html>\n<head><title>testName</title></head>\n<body>\n<h3>testName</h3>\n" +
                "<h4>Test Status</h4>\n" +
                "<table BORDER CELLSPACING=0 CELLPADDING=4>\n" +
                "<tr><td bgcolor=#FFCFCF><font size=2 color=black>Action</font></td>" +
                "<td bgcolor=#FFCFCF><font size=2 color=black>Target</font></td>" +
                "<td bgcolor=#FFCFCF><font size=2 color=black>&nbsp;</font></td></tr>\n" +
                "</table>\n" +
                "</body>", reporter.report());
    }
}
