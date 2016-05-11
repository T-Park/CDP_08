# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'insertcoin.ui'
#
# Created by: PyQt5 UI code generator 5.5.1
#
# WARNING! All changes made in this file will be lost!

from PyQt5 import QtCore, QtGui, QtWidgets

class Ui_Form(object):
    def setupUi(self, Form):
        Form.setObjectName("Form")
        Form.resize(800, 480)
        self.inputAmount = QtWidgets.QTextBrowser(Form)
        self.inputAmount.setGeometry(QtCore.QRect(260, 30, 311, 81))
        self.inputAmount.setObjectName("inputAmount")
        self.label = QtWidgets.QLabel(Form)
        self.label.setGeometry(QtCore.QRect(190, 40, 64, 71))
        font = QtGui.QFont()
        font.setFamily("210 동화책 R")
        font.setPointSize(12)
        font.setBold(False)
        font.setWeight(50)
        self.label.setFont(font)
        self.label.setObjectName("label")
        self.completeButton = QtWidgets.QPushButton(Form)
        self.completeButton.setGeometry(QtCore.QRect(600, 340, 81, 81))
        font = QtGui.QFont()
        font.setFamily("1훈새마을운동 R")
        font.setPointSize(10)
        self.completeButton.setFont(font)
        self.completeButton.setObjectName("completeButton")
        self.noticeText = QtWidgets.QTextEdit(Form)
        self.noticeText.setGeometry(QtCore.QRect(160, 140, 501, 181))
        self.noticeText.setObjectName("noticeText")

        self.retranslateUi(Form)
        QtCore.QMetaObject.connectSlotsByName(Form)

    def retranslateUi(self, Form):
        _translate = QtCore.QCoreApplication.translate
        Form.setWindowTitle(_translate("Form", "Form"))
        self.label.setText(_translate("Form", "넣은 돈"))
        self.completeButton.setText(_translate("Form", "완료"))
        self.noticeText.setHtml(_translate("Form", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\" \"http://www.w3.org/TR/REC-html40/strict.dtd\">\n"
"<html><head><meta name=\"qrichtext\" content=\"1\" /><style type=\"text/css\">\n"
"p, li { white-space: pre-wrap; }\n"
"</style></head><body style=\" font-family:\'Gulim\'; font-size:9pt; font-weight:400; font-style:normal;\">\n"
"<p style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\"><span style=\" font-size:12pt;\">적립할 돈을 모두 투입하셨으면 완료를 눌러주세요.</span></p>\n"
"<p style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\"><span style=\" font-size:12pt;\">넣으신 돈이 맞지않는다면 관리자에게 문의해 주세요.</span></p></body></html>"))

