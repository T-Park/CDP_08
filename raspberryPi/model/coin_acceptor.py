# coin_acceptor.py
# it sets GPIO and control coin acceptor and relay
# it accepts coin and retain inserted amount
# it provide interface power_on, power_off

# import RPi.GPIO as GPIO
import RPi as GPIO

class Coin_acceptor():
    def __init__(self):
        self.ammount = 0
        self.count = 0
        self.switch = True

    # reset coin acceptor
    def reset(self):
        self.switch = True
        self.ammount = 0
        self.count = 0

    # configurate GPIO
    def set_GPIO(self):
        GPIO.setmode(GPIO.BCM)

        # GPIO 17 to output port
        GPIO.setup(17, GPIO.OUT)
        # set pull down resistance and GPIO 23 to input port
        GPIO.setup(23, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
        GPIO.input(23)

        # set callback for GPIO 23
        GPIO.add_event_detect(23, GPIO.RISING, callback=self.callback_function)

    # callback function for accept coin
    # it will accept pwm signal
    def callback_function(self, channel):
        self.count = self.count + 1
        self.ammount = self.count * 50
        print("Edge on %s"%(50 * self.count))

    # to stop the coin acceptor
    def set_off(self):
        self.switch = False

    # start accepting coin
    def power_on(self):
        # initial setting
        self.set_GPIO()
        # power on the relay
        GPIO.output(17, True)
        self.reset()

        # get coin until switch is on
        while self.switch:
            pass

    # stop accepting coin and reset
    def power_off(self):
        GPIO.output(17, False)
        self.set_off()
        GPIO.cleanup()




# GPIO.output(17, True)

# try:
#     print("start")
#     while True:
#         continue
# except KeyboardInterrupt:
#     print("end")
#     GPIO.output(17, False)
#     GPIO.cleanup()
#     exit(0)
# #GPIO.setup(25, GPIO.IN, pull_up_down=GPIO.PUD.UP)
#
#
#
# try:
#     print("start")
#     while True:
#         #GPIO.wait_for_edge(23,GPIO.RISING)
#         #print("in %s"%(count * 100))
#         #count = count+1
#         #print(GPIO.input(23))
#         continue
# except KeyboardInterrupt:
#     print("exit")
#     GPIO.cleanup()
