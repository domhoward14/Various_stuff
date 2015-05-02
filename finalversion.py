import sys
import urllib
import re
import time

oldData = []
newData =[]
diff = []
oldStrings = []

#return the list of strings from the site
def retrieveData():
        page = urllib.urlopen("http://oldschool.runescape.com/slu").read()
        urls = re.findall('(...,false,0,"oldschool..",...,".*","..","Old School ..",".*",".*")',page)
        return urls

#retrieve the initial set of strings from the site
def init ():
        global oldStrings
        global oldData
        oldStrings = retrieveData()
        oldData = getStats(oldStrings)

#rtrieves the second list of strings and finds the first difference
def init2 (threshold, trackTime):
        global newData
        global oldData
        global oldStrings
        time.sleep(int(trackTime))
        newStrings = retrieveData()
        newData = getStats(newStrings)
        for i in range(len(oldData)):
                answer =int( newData[i]) - int(oldData[i])
                print 'The difference for the world number  ', i , '  is = ', answer , '\n'
                if abs(answer) >= int(threshold):
                        if answer > 0:
                                print '\nWorld number ' , i , ' increased by ' , abs(answer) , ' players', '\n'
                        else:
                                print '\nWorld number ' , i , ' decreased by ' , abs(answer) , ' players', '\n'
                diff.append(answer)

#updates the difference in the list
def updataDiff(threshold):

        global oldData
        global newData
        global diff
        oldData = newData
        newStrings = retrieveData()
        newData = getStats(newStrings)
        for i in range(len(oldData)):
                answer = int( newData[i]) - int(oldData[i])
                print 'The difference for the world number  ', i , '  is = ', answer , '\n'
                if abs(answer) >= int(threshold):
                        if answer > 0:
                                print '\nWorld number ' , i , ' increased by ' , abs(answer) , ' players', '\n'
                        else:
                                print '\nWorld number ' , i , ' decreased by ' , abs(answer) , ' players', '\n'

        return diff
		
		
#continuously updates the differences going by the the user input
def function (threshold,trackTime):
        global diff
        while True:
                time.sleep(int(trackTime))
                diff  = updataDiff(int(threshold))
				
#extracts the number from the strings
def  getStats (list):

        statList = []
        for string in list:
                tempString = string.split(',')
                statList.append(tempString[4])
        return statList
		
		
#custom validator to make sure that the input will not cause errors
def validateInput():
        for i in range(3):
                trackTime = raw_input('Please enter the sleep time interval: ')
                if int(trackTime) <= 0:
                        if i == 2:
                                print 'Too many incorrect tries. Now exiting.'
                                sys.exit()
                        print 'Please input a time higher then zero.'
                        continue
                threshold = raw_input('Please enter the change threshold: ')
                if int(threshold) <= 0:
                        if i == 2:
                                print 'Too many incorrect tries. Now exiting.'
                                sys.exit()
                        print 'Please input a threshold greater then zero.'
                        continue
                break
        return threshold, trackTime
		

def main ():
# needs an exception thrown for wrong type of input
        options = validateInput()
        init()
        init2(options[0],options[1])
        function(options[0],options[1])

#main function call
main()


