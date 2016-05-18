# insert_coin_controller.py
# control insert coin view

from PyQt5 import QtCore, QtWidgets
from view import ui_insert_coin
from model import coin_acceptor
import threading

class Coin_thread(QtCore.QThread):
    def __init__(self, coin_sig, thread_finished):
        QtCore.QThread.__init__(self)
        self.running = True
        self.coin_sig = coin_sig
        self.thread_finished = thread_finished

    def __del__(self):
        self.wait()

    # stop accepting coin
    def stop(self):
        self.coin_acceptor.power_off()
        self.running = False
        print("power off")
        self.terminate()
        print("terminate")
        self.thread.join() # wait for termination of thread
        print("join complete")
        self.thread_finished.emit()
        print("signal emit")

    # running code
    def run(self):
        self.coin_acceptor = coin_acceptor.Coin_acceptor()
        amount = 0
        self.running = True
        self.thread = threading.Thread(target=self.coin_acceptor.power_on)
        self.thread.start() # start tread

        while self.running:
            if amount != self.coin_acceptor.ammount:
                amount = self.coin_acceptor.ammount
                self.coin_sig.emit(amount)

class Insert_coin_controller(QtWidgets.QWidget):
    coin_sig = QtCore.pyqtSignal(int)
    thread_finished = QtCore.pyqtSignal()
    
    def __init__(self, sig, parent=None):
        super(Insert_coin_controller, self).__init__(parent)
        self.amount = 0# inserted amount
        
        self.sig = sig # signal object
        self.coin_thread = Coin_thread(self.coin_sig, self.thread_finished)


        self.initUI()
        self.initSignal()

        # thread for coin acceptore
        

    def initUI(self):
        self.ui_insert_coin = ui_insert_coin.Ui_Form() # initiate view ui
        self.ui_insert_coin.setupUi(self) # give widget

    def initSignal(self):
        self.ui_insert_coin.completeButton.clicked.connect(self.coin_thread.stop) # set complete button signal
        self.coin_sig.connect(self.display_number)  # set update signal
        self.thread_finished.connect(self.complete_insert) # end process

    def complete_insert(self):
        print("signal processed")
        self.sig.coin_insert_completed.emit(self.amount)

    # display specified number
    # i needs processing to digit overflow
    def display_number(self, amount):
        self.amount = amount
        self.ui_insert_coin.insertedCoin.display(amount)

    def showEvent(self, QShowEvent):
        self.amount = 0 # initiate variable
        # start thread
        self.coin_thread.start()
