# orglist_controller.py
# control orglist view

from PyQt5 import QtWidgets, QtCore
from view import ui_orglist
from model import organization

class Orglist_controller(QtWidgets.QWidget):
    def __init__(self, sig, parent=None):
        super(Orglist_controller, self).__init__(parent)
        self.sig = sig # signal object
        self.initUI()
        self.initSignal()

    def initUI(self):
        self.ui_orglist = ui_orglist.Ui_Dialog() # initiate view ui
        self.ui_orglist.setupUi(self) # give widget
        self.ui_orglist.tableWidget.setEditTriggers(QtWidgets.QAbstractItemView.NoEditTriggers) # 수정 못하게함
        self.ui_orglist.tableWidget.setSelectionBehavior(QtWidgets.QAbstractItemView.SelectRows) # 셀 선택시 row전체 선택하게

    def initSignal(self):
        self.ui_orglist.selectButton.clicked()

    def org_selected(self):
        self.sig.org_selected.emit() # emit signal to main controller


    def setItem(self):
        olist = list()
        olist.append(organization.Organization(name="abcde",point=10000000,tel="123-456-777"))
        olist.append(organization.Organization(name="abcde2",point=1000,tel="123-456-777"))
        olist.append(organization.Organization(name="abcde3",point=1000,tel="123-456-777"))
        olist.append(organization.Organization(name="abcde4",point=100,tel="123-456-777"))
        olist.append(organization.Organization(name="abcde5",point=100,tel="123-456-777"))
        olist.append(organization.Organization(name="abcde6",point=100,tel="123-456-777"))
        olist.append(organization.Organization(name="abcdaaaaaaaaaaaaaaae7",point=100,tel="123-456-777"))
        olist.append(organization.Organization(name="abcde8",point=100,tel="123-456-777"))
        olist.append(organization.Organization(name="abcde9",point=100,tel="123-456-777"))
        olist.append(organization.Organization(name="abcde10",point=100,tel="123-456-777"))
        olist.append(organization.Organization(name="abcde11",point=100,tel="123-456-777"))
        olist.append(organization.Organization(name="abcde12",point=100,tel="123-456-777"))
        olist.append(organization.Organization(name="abcde13",point=100,tel="123-456-777"))

        for i in range(len(olist)):
            self.ui_orglist.tableWidget.insertRow(i)


        # Add 3 cols
        for i in range(len(olist[0].header)):
            self.ui_orglist.tableWidget.insertColumn(i)
        self.ui_orglist.tableWidget.setHorizontalHeaderLabels(olist[0].header ) # 헤더이름 설정
        # item 생성은 임의로 해야할듯함
        # Add some cell data
        for i in range(self.ui_orglist.tableWidget.rowCount()):
            for j in range(self.ui_orglist.tableWidget.columnCount()):
                item = QtWidgets.QTableWidgetItem(str(olist[i].var[j])) #first class object
                item.setTextAlignment(QtCore.Qt.AlignCenter) # 3: right, 4: center http://doc.qt.io/qt-5/qt.html#AlignmentFlag-enum
                self.ui_orglist.tableWidget.closePersistentEditor(item)
                # item.setText(str(i + j))
                self.ui_orglist.tableWidget.setItem(i, j, item)





