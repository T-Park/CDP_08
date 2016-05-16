import RPi.GPIO as GPIO
import time

count = 0

def callback_function(channel):
    global count
    count = count + 1
    print("Edge on %s"%(50 * count))

GPIO.setmode(GPIO.BCM)

GPIO.setup(23, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
GPIO.input(23)

#GPIO.setup(25, GPIO.IN, pull_up_down=GPIO.PUD.UP)

GPIO.add_event_detect(23, GPIO.RISING, callback=callback_function)

try:
    print("start")
    while True:
        #GPIO.wait_for_edge(23,GPIO.RISING)
        #print("in %s"%(count * 100))
        #count = count+1
        #print(GPIO.input(23))
        continue
except KeyboardInterrupt:
    print("exit")
    GPIO.cleanup()    



