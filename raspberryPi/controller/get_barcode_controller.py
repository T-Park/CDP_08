# get_barcode_controller.py
# control get barcode view

from PyQt5 import QtWidgets, QtCore
from view import ui_get_barcode
from model import barcode_reader

class Get_barcode_controller(QtWidgets.QWidget):
    def __init__(self, sig, parent=None):
        super(Get_barcode_controller, self).__init__(parent)
        self.barcode_reader = barcode_reader.BarcodeReader()
        self.timer = QtCore.QTimer()
        self.sig = sig # signal object
        self.initUI()
        self.initTimer()

    def initUI(self):
        self.ui_get_barcode = ui_get_barcode.Ui_Form() # initiate view ui
        self.ui_get_barcode.setupUi(self) # give widget

    def initTimer(self):
        QtCore.QTimer.setSingleShot(self.timer, True)
        self.timer.timeout.connect(self.read_barcode)

    def read_barcode(self):
        # get barcode from barcode reader
        state, data = self.barcode_reader.run()

        # success to get barcode
        if state:
            self.sig.barcode_cognized.emit(data)
            print("success get barcode")
        else :
            self.sig.error.emit("Failed to get barcode\n")
            print("Failed to get barcode")

    def showEvent(self, QShowEvent):
        self.timer.start(5000)


