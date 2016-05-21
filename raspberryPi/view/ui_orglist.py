# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'orglist2.ui'
#
# Created by: PyQt5 UI code generator 5.5.1
#
# WARNING! All changes made in this file will be lost!

from PyQt5 import QtCore, QtGui, QtWidgets

class Ui_Dialog(object):
    def setupUi(self, Dialog):
        Dialog.setObjectName("Dialog")
        Dialog.resize(800, 480)
        self.label = QtWidgets.QLabel(Dialog)
        self.label.setGeometry(QtCore.QRect(50, 10, 231, 91))
        font = QtGui.QFont()
        font.setFamily("문체부 쓰기 흘림체")
        font.setPointSize(24)
        self.label.setFont(font)
        self.label.setObjectName("label")
        self.prevButton = QtWidgets.QPushButton(Dialog)
        self.prevButton.setGeometry(QtCore.QRect(90, 400, 181, 61))
        font = QtGui.QFont()
        font.setFamily("서울남산체 M")
        font.setPointSize(14)
        self.prevButton.setFont(font)
        self.prevButton.setObjectName("prevButton")
        self.nextButton = QtWidgets.QPushButton(Dialog)
        self.nextButton.setGeometry(QtCore.QRect(530, 400, 181, 61))
        font = QtGui.QFont()
        font.setFamily("서울남산체 M")
        font.setPointSize(14)
        self.nextButton.setFont(font)
        self.nextButton.setObjectName("nextButton")
        self.selectButton = QtWidgets.QPushButton(Dialog)
        self.selectButton.setGeometry(QtCore.QRect(310, 400, 181, 61))
        font = QtGui.QFont()
        font.setFamily("서울남산체 M")
        font.setPointSize(14)
        self.selectButton.setFont(font)
        self.selectButton.setObjectName("selectButton")
        self.tableWidget = QtWidgets.QTableWidget(Dialog)
        self.tableWidget.setGeometry(QtCore.QRect(50, 100, 691, 281))
        self.tableWidget.setObjectName("tableWidget")
        self.tableWidget.setColumnCount(0)
        self.tableWidget.setRowCount(0)

        self.retranslateUi(Dialog)
        QtCore.QMetaObject.connectSlotsByName(Dialog)

    def retranslateUi(self, Dialog):
        _translate = QtCore.QCoreApplication.translate
        Dialog.setWindowTitle(_translate("Dialog", "Dialog"))
        self.label.setText(_translate("Dialog", "기부단체 목록"))
        self.prevButton.setText(_translate("Dialog", "← 이전페이지"))
        self.nextButton.setText(_translate("Dialog", "다음페이지 →"))
        self.selectButton.setText(_translate("Dialog", "선택"))

