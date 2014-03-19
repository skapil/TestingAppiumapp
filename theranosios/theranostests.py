#!/usr/bin/python

import sys,getopt
import os
import subprocess
from time import sleep
import signal

''' This class will take two arguments as name of the device and UUID for ios or serial number for android and will start the test  '''

class RunTest:

    ios_uuid = {'iphone5':'11c89c89810feed8cddd52303cb363492bd58268','iphone4':'bec5792d45a291e16920617ac87769811e02b0ac'}
    
    device = ''
    
    def __init__(self):
        print "Started the runtest\n"

    #Getting through the options which provided in command line
    def getDeviceInfo(self, argv):
        print "This is the getDevice method" 
        try:
            opt,arg = getopt.getopt(argv,"-hd:",["deviceos="])
        except getopt.GetopyError:
            print "theranostests.py -d <android/iphone4/iphone5>s"
            sys.exit(2)
        for opt, arg in opt:
            if opt == '-h':
                print 'theranostests.py -d <android/ios>'
                sys.exit()
            elif opt in ('-d', 'devicesos='):
                device = arg
        print 'Device need to run theranos app tests on '+device
        return device

                
    # Get the device type and run the command application for that device
    def deviceToRunTest(self,device):
        if(device == 'iphone4' or device == 'iphone5'):
            print "Test will run for iOS in "+device
            self.runIOSTest(self.ios_uuid[device])
        elif device == "android":
            print 'Test will run for Android device\n'
        else:
            print 'Not able to run please run the test again\n'    

   # Running the test on ios device
    def runIOSTest(self,uuid):
        flag = 'false'
        serverfile = open('appiumserver.txt','w+')
        print 'Device '+device+' is being prepare to run'
        appium_server = subprocess.Popen(['appium','-U',uuid,'--app','com.theranos.appsuite.medassist','-a','127.0.0.1','-p','4723','-l'],stdout = serverfile,stderr = subprocess.PIPE,preexec_fn=os.setsid) 
        print 'Starting appium server'
        
        while True: 
            for appiumline in iter(serverfile.readline,''):
                print (appiumline)
                if 'interface listener started on 127.0.0.1:4723' in appiumline:
                    print "Now starting the test as appium server is ready to run the test"
                    print 'Waiting for 10 seconds before starting the test'
                    sleep(10)
                    test_run = subprocess.Popen(['mvn','test'],stdout = subprocess.PIPE,preexec_fn=os.setsid)
                    sleep(2)
                    for testline in iter(test_run.stdout.readline,''):
                        print testline
                    flag = 'true'
                
            if flag == 'true':
                print 'closing the appium server output'
                serverfile.close()
                print 'killing the appium server process'
                os.killpg(appium_server.pid, signal.SIGKILL)
                break

        print '----------------- Test has completed for IOS .....................'            

        if flag =='false':
            print 'Not able to run the test, exiting from the text'
            print 'closing the appium server output'
            serverfile.close()
            print 'killing the appium server process'
            os.killpg(appium_server.pid, signal.SIGTERM)
            sys.exit() 

        

if __name__ == "__main__":
    run = RunTest() 
    device = run.getDeviceInfo(sys.argv[1:])
    run.deviceToRunTest(device)
