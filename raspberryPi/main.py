# main.py
# coincollecter's main function
import coin_collecter as cc

global coin_collecter # coin_collecter
global connection # connection to server

def init():
    coin_collecter = cc.CoinCollecter()
    # connection

while True:
    print("메뉴를 선택하세요:")
    select = input()

    if select == 1:
        flag = False;
        print("카메라 앞에 바코드를 위치하여 주세요")
        for i in range(5):

        if flag == False:
            print("인식에 실패했습니다")
        else :
            print("바코드: " + barcode)
    else :
        print("input err")
