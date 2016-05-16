# save_result_controller.py
# control save result view

from PyQt5 import QtWidgets
from view import ui_save_result

class Save_result_controller(QtWidgets.QWidget):
    def __init__(self, sig, parent=None):
        super(Save_result_controller, self).__init__(parent)
        self.sig = sig # signal object
        self.initUI()

    def initUI(self):
        self.ui_get_barcode = ui_save_result.Ui_Form() # initiate view ui
        self.ui_get_barcode.setupUi(self) # give widget