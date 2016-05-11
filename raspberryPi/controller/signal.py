# signal.py
# define signals, indicate mainwindow's action by other widgets

from PyQt5 import QtCore

class Signal(QtCore.QObject):
    asignal = QtCore.pyqtSignal(int)

    def __init__(self):
        super(Signal, self).__init__()
