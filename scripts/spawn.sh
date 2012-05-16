#!/bin/bash

nohup java -cp lib/*:dist/* com.thoughtworks.frankenstein.application.PipingMain org.robotframework.swing.testapp.examplesut.TodoListApplication >/dev/null 2>1 &
