# signal.py
# define signals, indicate mainwindow's action by other widgets

from PyQt5 import QtCore

class Signal(QtCore.QObject):

    select_service = QtCore.pyqtSignal(int)
    barcode_cognized = QtCore.pyqtSignal(str)
    coin_insert_completed = QtCore.pyqtSignal(int)
    error = QtCore.pyqtSignal(str)
    reset = QtCore.pyqtSignal()

    def __init__(self):
        QtCore.QObject.__init__(self)


