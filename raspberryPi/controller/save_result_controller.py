# save_result_controller.py
# control save result view

from PyQt5 import QtWidgets, QtCore
from view import ui_save_result

class Save_result_controller(QtWidgets.QWidget):
    def __init__(self, client, sig, parent=None):
        super(Save_result_controller, self).__init__(parent)
        self.client = client
        self.sig = sig # signal object

        self.timer = QtCore.QTimer()
        self.initUI()
        self.initTimer()

    def initUI(self):
        self.ui_save_result = ui_save_result.Ui_Form() # initiate view ui
        self.ui_save_result.setupUi(self) # give widget

    def initTimer(self):
        QtCore.QTimer.setSingleShot(self.timer, True)
        self.timer.timeout.connect(self.emit)

    # reset coin collector
    def emit(self):
        self.sig.reset.emit() # send signal to main

    # display specified number
    # i needs processing to digit overflow
    def display_number(self, amount):
        self.ui_save_result.insertedCoin.display(amount)

    def showEvent(self, QShowEvent):
        # connection to server
        self.client.connection.save_point(self.client.inserted_barcode, self.client.inserted_coin)
        # display saved amount
        self.display_number(self.client.inserted_coin)
        # reset after 5 sec
        self.timer.start(5000)