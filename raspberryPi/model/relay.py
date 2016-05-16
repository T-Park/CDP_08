import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)

GPIO.setup(17, GPIO.OUT)
GPIO.output(17, True)

try:
    print("start")
    while True:
        continue
except KeyboardInterrupt:
    print("end")
    GPIO.output(17, False)
    GPIO.cleanup()
    exit(0)
