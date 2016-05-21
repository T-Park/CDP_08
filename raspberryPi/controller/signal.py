# signal.py
# define signals, indicate mainwindow's action by other widgets

from PyQt5 import QtCore

class Signal(QtCore.QObject):

    select_service = QtCore.pyqtSignal(int) # service type : save, donation
    barcode_cognized = QtCore.pyqtSignal(str) # recognized barcode
    coin_insert_completed = QtCore.pyqtSignal(int) # int: ammount of inserted coin
    org_selected = QtCore.pyqtSignal(int) # int: index for list
    error = QtCore.pyqtSignal(str) # error message
    reset = QtCore.pyqtSignal() # back to the main widget

    def __init__(self):
        QtCore.QObject.__init__(self)


