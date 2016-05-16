# donate_result_controller.py
# control donate result view

from PyQt5 import QtWidgets, QtCore
from view import ui_donate_result

class Donate_result_controller(QtWidgets.QWidget):
    def __init__(self, sig, parent=None):
        super(Donate_result_controller, self).__init__(parent)
        self.sig = sig # signal object
        self.timer = QtCore.QTimer()

        self.initUI()
        self.initTimer()

    def initUI(self):
        self.ui_donate_result = ui_donate_result.Ui_Form() # initiate view ui
        self.ui_donate_result.setupUi(self) # give widget

    def initTimer(self):
        QtCore.QTimer.setSingleShot(self.timer, True)
        self.timer.timeout.connect(self.emit)

    # reset coin collector
    def emit(self):
        self.sig.reset.emit() # send signal to main

    # display specified number
    # i needs processing to digit overflow
    def display_number(self, amount):
        self.ui_donate_result.insertedCoin.display(amount)