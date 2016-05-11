# main.py
# mainwindow controller for ui

import sys

from PyQt5 import QtWidgets
from PyQt5.QtWidgets import (QApplication)
from controller import intro_controller, get_barcode_controller, donate_result_controller, insert_coin_controller, orglist_controller, save_result_controller, signal


class MainWindow(QtWidgets.QMainWindow):

    def __init__(self):
        super().__init__()
        self.resize(800, 480) # set window size


        self.central_widget = QtWidgets.QStackedWidget() # use stacked widget
        self.setCentralWidget(self.central_widget) #
        self.initSignal()
        self.initUI()

    def test(self, num):
        print( "signal %d" %num )
        self.central_widget.setCurrentIndex(num)

    def initSignal(self):
        # signal from other widgets
        self.s = signal.Signal()
        self.s.asignal.connect(self.test)

        # run whenever widget is changed
        self.central_widget.currentChanged.connect(self.func)

    def initUI(self):
        # initiate all ui
        self.intro_ui = intro_controller.Intro_controller(self.s)
        self.get_barcode_ui = get_barcode_controller.Get_barcode_controller()
        self.insert_coin_ui = insert_coin_controller.Insert_coin_controller()
        self.orglist_ui = orglist_controller.Orglist_controller()
        self.save_result_ui = save_result_controller.Save_result_controller()
        self.donate_result_ui = donate_result_controller.Donate_result_controller()

        # insert to stack
        self.central_widget.addWidget(self.intro_ui)
        self.central_widget.addWidget(self.get_barcode_ui)
        self.central_widget.addWidget(self.insert_coin_ui)
        self.central_widget.addWidget(self.orglist_ui)
        self.central_widget.addWidget(self.save_result_ui)
        self.central_widget.addWidget(self.donate_result_ui)

        self.central_widget.setCurrentWidget(self.intro_ui)
        self.show()

    def func(self, num):
        print( "widget changed %d %s"%( num, self.central_widget.currentWidget()))

if __name__ == '__main__':

    app = QApplication(sys.argv)
    ex = MainWindow()
    sys.exit(app.exec_())