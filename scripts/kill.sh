PSID=`ps -ef | grep "[o]rg.robotframework.swing.testapp.examplesut.TodoListApplication" -m 01 | awk '{print $2}'`

if [ "$PSID" != "" ] 
then 
  `kill -9 $PSID`
fi
