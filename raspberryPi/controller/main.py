# main.py
# mainwindow controller for ui

import sys
import os.path

sys.path.append(os.path.join(os.path.dirname(__file__), '..')) # add current dir to python path

from PyQt5 import QtWidgets, QtCore
from PyQt5.QtWidgets import (QApplication)
from controller import intro_controller, get_barcode_controller, donate_result_controller, insert_coin_controller, \
    orglist_controller, save_result_controller, error_message_controller, signal
from model import coin_collector_client, organization

from util import widget_type, service_type

class MainWindow(QtWidgets.QMainWindow):
    def __init__(self):
        super().__init__()
        self.resize(640, 450)  # set window size

        self.central_widget = QtWidgets.QStackedWidget()  # use stacked widget
        self.setCentralWidget(self.central_widget)  # set widget

        self.client = coin_collector_client.Coin_collector_clinet('localhost',5555) # client model
        # it will be modifyed
        self.orglist = list() # organization list
        self.widget_type = widget_type.Widget_type # enum for widget type
        self.service_type = service_type.Service_Type # enum for service type

        self.setWindowFlags(self.windowFlags() | QtCore.Qt.FramelessWindowHint) # remove frame
        self.setGeometry(0,0,self.width(),self.height()) # 0,0으로 윈도우 위치 옮기기
        self.initClient()
        self.initSignal()
        self.initUI()



    # set signals and processing functions
    def initSignal(self):
        # signal from other widgets
        self.sig = signal.Signal()
        self.sig.select_service.connect(self.select_service) # select service callback

        self.sig.barcode_cognized.connect(self.process_barcode) # barcode recognization completed
        self.sig.coin_insert_completed.connect(self.proccess_inserted_point) # insert coin completed

        self.sig.barcode_cognized.connect(self.process_barcode) # process recognized barcode
        self.sig.org_selected.connect(self.process_org_selected)

        self.sig.error.connect(self.process_error) # error control callback
        self.sig.reset.connect(self.process_reset) # reset flow

        # run whenever widget is changed
        self.central_widget.currentChanged.connect(self.func)
        #

    # set widgets
    def initUI(self):
        # initiate all ui
        self.intro_ui = intro_controller.Intro_controller(self.sig)  # index 0
        self.get_barcode_ui = get_barcode_controller.Get_barcode_controller(self.sig)  # index 1
        self.insert_coin_ui = insert_coin_controller.Insert_coin_controller(self.sig)  # index 2
        self.orglist_ui = orglist_controller.Orglist_controller(self.orglist, self.sig)  # index 3
        self.save_result_ui = save_result_controller.Save_result_controller(self.client, self.sig)  # index 4
        self.donate_result_ui = donate_result_controller.Donate_result_controller(self.client, self.sig)  # index 5
        self.error_message_ui = error_message_controller.Error_message_controller(self.sig)  # index 6

        # insert to stack
        self.central_widget.insertWidget(widget_type.Widget_type.intro.value, self.intro_ui)
        self.central_widget.insertWidget(widget_type.Widget_type.get_barcode.value, self.get_barcode_ui)
        self.central_widget.insertWidget(widget_type.Widget_type.insert_coin.value, self.insert_coin_ui)
        self.central_widget.insertWidget(widget_type.Widget_type.orglist.value, self.orglist_ui)
        self.central_widget.insertWidget(widget_type.Widget_type.save_result.value, self.save_result_ui)
        self.central_widget.insertWidget(widget_type.Widget_type.donate_result.value, self.donate_result_ui)
        self.central_widget.insertWidget(widget_type.Widget_type.error_message.value, self.error_message_ui)

        self.central_widget.setCurrentWidget(self.intro_ui)
        self.show()

    # set client
    def initClient(self, cid = 1):
        self.client.connection.connect(); # connect to server
        amount = self.client.connection.login(cid); # login

    # change widget by index
    def change_widget(self, num):
        print("index %d" % num)
        self.central_widget.setCurrentIndex(num)

    # set which service is selected( save, donation )
    def select_service(self, type):
        if type == service_type.Service_Type.Donate.value:
            self.client.current_state = self.service_type.Donate
        elif type == service_type.Service_Type.Save.value:
            self.client.current_state = self.service_type.Save
        self.change_widget(widget_type.Widget_type.get_barcode.value) # change widget to get_barcode

    # it might delete later......
    def func(self, num, opt=0):
        print("widget changed %d %s" % (num, self.central_widget.currentWidget()))
        if opt != 0:
            print("opt %d" % opt)

    # set barcode and get user info
    def process_barcode(self, barcode):
        print("barcode: %s"% barcode)
        self.client.input_barcode = barcode
        # code for get access server's user info

        if self.client.current_state == self.service_type.Save:
            self.change_widget(self.widget_type.insert_coin.value) # change widget to insert_coin
        elif self.client.current_state == self.service_type.Donate:
            self.change_widget(self.widget_type.orglist.value) # change widget to insert_coin



    # process inserted point according to service type
    def proccess_inserted_point(self, amount):
        self.client.coin_collector.add_money(amount) # accumulate inserted coin to coin collector
        self.client.inserted_coin = amount

        if self.client.current_state == self.service_type.Save:
            # code for save point to user and communicate with server
            self.change_widget(self.widget_type.save_result.value)
        elif self.client.current_state == self.service_type.Donate:
            # code for show organization list and save point to selected list
            self.change_widget(self.widget_type.donate_result.value)


    # process selected organization
    # param - index: index of organization
    def process_org_selected(self, index):
        self.client.target_org_index = index
        self.change_widget(self.widget_type.insert_coin.value)


    # reset variables and back to the intro widget
    def process_reset(self):
        # initiate client
        self.client.current_state = self.service_type.Beginning
        self.client.input_barcode = None
        self.client.inserted_coin = 0
        self.client.user = None

        self.change_widget(self.widget_type.intro.value)

    # process error message
    def process_error(self, error_message):
        self.error_message_ui.set_error_message(error_message)
        self.change_widget(self.widget_type.error_message.value)

if __name__ == '__main__':
    app = QApplication(sys.argv)
    ex = MainWindow()
    sys.exit(app.exec_())
