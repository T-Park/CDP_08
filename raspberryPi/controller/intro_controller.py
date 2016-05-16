# intro_controller.py
# control intro view

from PyQt5 import QtWidgets
from view import ui_intro
from util import service_type


class Intro_controller(QtWidgets.QWidget):
    def __init__(self, sig, parent=None):
        super(Intro_controller, self).__init__(parent)
        self.sig = sig  # signal object
        self.initUI()
        self.initSignal()

    def initUI(self):
        self.ui_intro = ui_intro.Ui_Form()  # initiate view ui
        self.ui_intro.setupUi(self)  # give widget

    def initSignal(self):
        self.ui_intro.donateButton.clicked.connect(self.select_donate)
        self.ui_intro.saveButton.clicked.connect(self.select_save)

    def select_donate(self):
        print("donateButton")
        self.sig.select_service.emit(service_type.Service_Type.Donate.value)

    def select_save(self):
        print("saveButton")
        self.sig.select_service.emit(service_type.Service_Type.Save.value)
