# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'donateresult.ui'
#
# Created by: PyQt5 UI code generator 5.5.1
#
# WARNING! All changes made in this file will be lost!

from PyQt5 import QtCore, QtGui, QtWidgets

class Ui_Form(object):
    def setupUi(self, Form):
        Form.setObjectName("Form")
        Form.resize(800, 480)
        self.donatedAmountButton = QtWidgets.QTextBrowser(Form)
        self.donatedAmountButton.setGeometry(QtCore.QRect(310, 160, 261, 70))
        self.donatedAmountButton.setObjectName("donatedAmountButton")
        self.label = QtWidgets.QLabel(Form)
        self.label.setGeometry(QtCore.QRect(50, 140, 250, 110))
        font = QtGui.QFont()
        font.setFamily("1훈새마을운동 R")
        font.setPointSize(24)
        self.label.setFont(font)
        self.label.setObjectName("label")
        self.label_2 = QtWidgets.QLabel(Form)
        self.label_2.setGeometry(QtCore.QRect(590, 140, 150, 110))
        font = QtGui.QFont()
        font.setFamily("1훈새마을운동 R")
        font.setPointSize(24)
        self.label_2.setFont(font)
        self.label_2.setObjectName("label_2")
        self.label_3 = QtWidgets.QLabel(Form)
        self.label_3.setGeometry(QtCore.QRect(210, 300, 391, 110))
        font = QtGui.QFont()
        font.setFamily("1훈새마을운동 R")
        font.setPointSize(24)
        self.label_3.setFont(font)
        self.label_3.setObjectName("label_3")

        self.retranslateUi(Form)
        QtCore.QMetaObject.connectSlotsByName(Form)

    def retranslateUi(self, Form):
        _translate = QtCore.QCoreApplication.translate
        Form.setWindowTitle(_translate("Form", "Form"))
        self.label.setText(_translate("Form", "기부하신 금액은"))
        self.label_2.setText(_translate("Form", "원 입니다."))
        self.label_3.setText(_translate("Form", "이용해 주셔서 감사합니다"))

