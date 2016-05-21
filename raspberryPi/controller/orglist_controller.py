# orglist_controller.py
# control orglist view

from PyQt5 import QtWidgets, QtCore
from view import ui_orglist
from model import organization
import math


class Orglist_controller(QtWidgets.QWidget):
    def __init__(self, orglist, sig, parent=None):
        super(Orglist_controller, self).__init__(parent)
        self.orglist = orglist  # orgnization list
        self.sig = sig  # signal object

        self.initUI()
        self.initSignal()
        # floor(실제 적용 시 위젯길이 - 헤더 길이(수평) / 아이템 하나의 높이)임
        # number of rows displayed at one scroll
        self.scroll_per_page = 0

    def initUI(self):
        self.ui_orglist = ui_orglist.Ui_Dialog()  # initiate view ui
        self.ui_orglist.setupUi(self)  # give widget
        self.ui_orglist.tableWidget.setEditTriggers(QtWidgets.QAbstractItemView.NoEditTriggers)  # 수정 못하게함
        self.ui_orglist.tableWidget.setSelectionBehavior(QtWidgets.QAbstractItemView.SelectRows)  # 셀 선택시 row전체 선택하게

        self.ui_orglist.tableWidget.setColumnWidth(1, 80)
        self.ui_orglist.tableWidget.horizontalHeader().setSectionResizeMode(
            QtWidgets.QHeaderView.Fixed)  # 화면에 꽉차게 이러면 전부 수정안됨
        # ui.tableWidget.horizontalHeader().setSectionResizeMode(0, QtWidgets.QHeaderView.Stretch) #이러면 0번만 수정안됨
        # ui.tableWidget.horizontalHeader().setSectionResizeMode(QtWidgets.QHeaderView.ResizeToContents) # 화면에 꽉차게 이러면 전부 수정안됨

        self.ui_orglist.tableWidget.verticalHeader().hide()  # 헤더 숨기기
        self.ui_orglist.tableWidget.verticalScrollBar().hide()  # 스크롤바 숨기기

        print(self.ui_orglist.tableWidget.verticalScrollBar().value())  # 현재 스크롤 위치 알아내기

    def initSignal(self):
        self.ui_orglist.selectButton.clicked.connect(self.org_selected)
        self.ui_orglist.prevButton.clicked.connect(self.previous_page)
        self.ui_orglist.nextButton.clicked.connect(self.next_page)

    # move to next page
    def next_page(self):
        # get current top index
        current_index = self.ui_orglist.tableWidget.verticalScrollBar().value()  # 현재 스크롤 위치
        self.ui_orglist.tableWidget.scrollToItem(
            self.ui_orglist.tableWidget.item(current_index + self.scroll_per_page, 0),
            QtWidgets.QAbstractItemView.PositionAtTop)

    # move to previous page
    def previous_page(self):
        # get current top index
        current_index = self.ui_orglist.tableWidget.verticalScrollBar().value()  # 현재 스크롤 위치
        self.ui_orglist.tableWidget.scrollToItem(self.ui_orglist.tableWidget.item(
            current_index - self.scroll_per_page if current_index - self.scroll_per_page > 0 else 0, 0),
                                                 QtWidgets.QAbstractItemView.PositionAtTop)

    def org_selected(self):
        selected_index = 0;

        model = self.ui_orglist.tableWidget.selectionModel()
        if model.hasSelection():
            selected_index = model.selectedRows()[0].row()
            print(selected_index)
        else:
            print("No selection")
        self.sig.org_selected.emit(selected_index) # emit signal to main controller

    def setItem(self):
        olist = list()
        olist.append(organization.Organization(name="abcde", point=10000000, tel="123-456-777"))
        olist.append(organization.Organization(name="abcde2", point=1000, tel="123-456-777"))
        olist.append(organization.Organization(name="abcde3", point=1000, tel="123-456-777"))
        olist.append(organization.Organization(name="abcde4", point=100, tel="123-456-777"))
        olist.append(organization.Organization(name="abcde5", point=100, tel="123-456-777"))
        olist.append(organization.Organization(name="abcde6", point=100, tel="123-456-777"))
        olist.append(organization.Organization(name="abcdaaaaaaaaaaaaaaae7", point=100, tel="123-456-777"))
        olist.append(organization.Organization(name="abcde8", point=100, tel="123-456-777"))
        olist.append(organization.Organization(name="abcde9", point=100, tel="123-456-777"))
        olist.append(organization.Organization(name="abcde10", point=100, tel="123-456-777"))
        olist.append(organization.Organization(name="abcde11", point=100, tel="123-456-777"))
        olist.append(organization.Organization(name="abcde12", point=100, tel="123-456-777"))
        olist.append(organization.Organization(name="abcde13", point=100, tel="123-456-777"))

        for i in range(len(olist)):
            self.ui_orglist.tableWidget.insertRow(i)

        # Add 3 cols
        for i in range(len(olist[0].header)):
            self.ui_orglist.tableWidget.insertColumn(i)
        self.ui_orglist.tableWidget.setHorizontalHeaderLabels(olist[0].header)  # 헤더이름 설정
        # item 생성은 임의로 해야할듯함
        # Add some cell data
        for i in range(self.ui_orglist.tableWidget.rowCount()):
            for j in range(self.ui_orglist.tableWidget.columnCount()):
                item = QtWidgets.QTableWidgetItem(str(olist[i].var[j]))  # first class object
                item.setTextAlignment(
                    QtCore.Qt.AlignCenter)  # 3: right, 4: center http://doc.qt.io/qt-5/qt.html#AlignmentFlag-enum
                # self.ui_orglist.tableWidget.closePersistentEditor(item)
                # item.setText(str(i + j))
                self.ui_orglist.tableWidget.setItem(i, j, item)

    def showEvent(self, QShowEvent):
        self.setItem()
        # to prevent null pointer error
        self.ui_orglist.tableWidget.horizontalHeader().setSectionResizeMode(0,
                                                                            QtWidgets.QHeaderView.Stretch)  # 이러면 0번만 수정안됨
        self.scroll_per_page = math.floor((
                                          self.ui_orglist.tableWidget.height() - self.ui_orglist.tableWidget.horizontalHeader().height()) / self.ui_orglist.tableWidget.rowHeight(
            0))
