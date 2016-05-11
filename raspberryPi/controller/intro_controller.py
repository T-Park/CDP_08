# intro_controller.py
# control intro view

from PyQt5 import QtWidgets
from view import ui_intro

class Intro_controller(QtWidgets.QWidget):
    def __init__(self, sig, parent=None ):
        super(Intro_controller, self).__init__(parent)
        self.s = sig
        self.initUI()


    def initUI(self):
        self.ui_intro = ui_intro.Ui_Form() # initiate view ui
        self.ui_intro.setupUi(self) # give widget

        self.ui_intro.donateButton.clicked.connect(self.test)
                # self.btn.clicked.connect(self.doAction)
        self.ui_intro.saveButton.clicked.connect(self.test2)
                # self.btn.clicked.connect(self.doAction)

    def test(self):
        print("donateButton")
        self.s.asignal.emit(1)

    def test2(self):
        print("saveButton")
        self.s.asignal.emit(2)
