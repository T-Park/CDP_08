# get_barcode_controller.py
# control get barcode view

from PyQt5 import QtWidgets
from view import ui_get_barcode

class Get_barcode_controller(QtWidgets.QWidget):
    def __init__(self, parent=None):
        super(Get_barcode_controller, self).__init__(parent)
        self.initUI()

    def initUI(self):
        self.ui_get_barcode = ui_get_barcode.Ui_Form() # initiate view ui
        self.ui_get_barcode.setupUi(self) # give widget