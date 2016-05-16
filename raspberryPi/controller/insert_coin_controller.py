# insert_coin_controller.py
# control insert coin view

from PyQt5 import QtWidgets
from view import ui_insert_coin

class Insert_coin_controller(QtWidgets.QWidget):
    def __init__(self, sig, parent=None):
        super(Insert_coin_controller, self).__init__(parent)
        self.sig = sig # signal object
        self.initUI()

    def initUI(self):
        self.ui_get_barcode = ui_insert_coin.Ui_Form() # initiate view ui
        self.ui_get_barcode.setupUi(self) # give widget