# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'intro.ui'
#
# Created by: PyQt5 UI code generator 5.5.1
#
# WARNING! All changes made in this file will be lost!

from PyQt5 import QtCore, QtGui, QtWidgets

class Ui_Form(object):
    def setupUi(self, Form):
        Form.setObjectName("Form")
        Form.resize(800, 480)
        Form.setToolTipDuration(-1)
        Form.setAutoFillBackground(False)
        self.saveButton = QtWidgets.QPushButton(Form)
        self.saveButton.setGeometry(QtCore.QRect(0, 240, 800, 240))
        font = QtGui.QFont()
        font.setFamily("1훈새마을운동 R")
        font.setPointSize(60)
        self.saveButton.setFont(font)
        self.saveButton.setAutoFillBackground(False)
        self.saveButton.setStyleSheet("QPushButton#pushButton { background-color: rgb(255, 255, 87) }")
        icon = QtGui.QIcon()
        icon.addPixmap(QtGui.QPixmap("C:/Users/woorizip/Desktop/Qt Designer 관련/png/piggy-bank.png"), QtGui.QIcon.Normal, QtGui.QIcon.Off)
        self.saveButton.setIcon(icon)
        self.saveButton.setIconSize(QtCore.QSize(150, 200))
        self.saveButton.setCheckable(True)
        self.saveButton.setAutoExclusive(True)
        self.saveButton.setObjectName("saveButton")
        self.donateButton = QtWidgets.QPushButton(Form)
        self.donateButton.setGeometry(QtCore.QRect(0, 0, 800, 240))
        font = QtGui.QFont()
        font.setFamily("1훈새마을운동 R")
        font.setPointSize(60)
        self.donateButton.setFont(font)
        self.donateButton.setFocusPolicy(QtCore.Qt.WheelFocus)
        self.donateButton.setContextMenuPolicy(QtCore.Qt.DefaultContextMenu)
        self.donateButton.setAutoFillBackground(False)
        self.donateButton.setStyleSheet("QPushButton#pushButton2 { background-color: rgb(28, 252, 255) }")
        icon1 = QtGui.QIcon()
        icon1.addPixmap(QtGui.QPixmap("C:/Users/woorizip/Desktop/Qt Designer 관련/png/donation.png"), QtGui.QIcon.Normal, QtGui.QIcon.Off)
        self.donateButton.setIcon(icon1)
        self.donateButton.setIconSize(QtCore.QSize(150, 200))
        self.donateButton.setObjectName("donateButton")

        self.retranslateUi(Form)
        QtCore.QMetaObject.connectSlotsByName(Form)

    def retranslateUi(self, Form):
        _translate = QtCore.QCoreApplication.translate
        Form.setWindowTitle(_translate("Form", "Form"))
        self.saveButton.setText(_translate("Form", "     적립"))
        self.donateButton.setText(_translate("Form", "     기부"))

