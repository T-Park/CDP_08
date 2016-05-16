# orglist_controller.py
# control orglist view

from PyQt5 import QtWidgets
from view import ui_orglist

class Orglist_controller(QtWidgets.QWidget):
    def __init__(self, sig, parent=None):
        super(Orglist_controller, self).__init__(parent)
        self.sig = sig # signal object
        self.initUI()

    def initUI(self):
        self.ui_get_barcode = ui_orglist.Ui_Dialog() # initiate view ui
        self.ui_get_barcode.setupUi(self) # give widget