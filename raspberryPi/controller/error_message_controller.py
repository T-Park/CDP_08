# error_message_controller.py
# control error meesage view

from PyQt5 import QtWidgets, QtCore
from view import ui_error_message

class Error_message_controller(QtWidgets.QWidget):
    def __init__(self, sig, parent=None):
        super(Error_message_controller, self).__init__(parent)
        self.timer = QtCore.QTimer()
        self.sig = sig # signal object
        self.initUI()
        self.initSignal()
        self.initTimer()

    def initUI(self):
        self.ui_error_message = ui_error_message.Ui_Form() # initiate view ui
        self.ui_error_message.setupUi(self) # give widget

    def initSignal(self):
        self.ui_error_message.confirmButton.clicked.connect(self.emit)

    def initTimer(self):
        QtCore.QTimer.setSingleShot(self.timer, True)
        self.timer.timeout.connect(self.emit)

    def set_error_message(self, error_message):
        self.ui_error_message.textBrowser.setText(error_message)

    def emit(self):
        # stop timer if entered by button click
        if self.timer.isActive():
            self.timer.stop()

        self.sig.reset.emit() # send signal to main

    # start timer when widget changed to self
    def showEvent(self, QShowEvent):
        # change widget after 5 sec
        self.timer.start(5000)
