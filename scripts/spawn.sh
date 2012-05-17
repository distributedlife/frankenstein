#!/bin/bash

nohup java -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n -cp lib/*:dist/* com.thoughtworks.frankenstein.application.PipingMain org.robotframework.swing.testapp.examplesut.TodoListApplication >/dev/null 2>1 &
