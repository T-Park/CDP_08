# get_barcode_controller.py
# control get barcode view

from PyQt5 import QtWidgets
from view import ui_get_barcode
from model import barcode_reader
import time

class Get_barcode_controller(QtWidgets.QWidget):
    def __init__(self, sig, parent=None):
        super(Get_barcode_controller, self).__init__(parent)
        self.barcode_reader = barcode_reader.BarcodeReader()
        self.sig = sig # signal object
        self.initUI()

    def initUI(self):
        self.ui_get_barcode = ui_get_barcode.Ui_Form() # initiate view ui
        self.ui_get_barcode.setupUi(self) # give widget

    def showEvent(self, QShowEvent):
        self.show()
        time.sleep(5)
        # get barcode from barcode reader
        state, data = self.barcode_reader.run()

        # success to get barcode
        if state:
            self.sig.barcode_cognized.emit(data)
            print("success get barcode")
        else :
            self.sig.error.emit("Failed to get barcode\n")
            print("Failed to get barcode")

