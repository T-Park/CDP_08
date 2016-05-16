# coin_collecter.py
# it contain coin_collecter's infomation
# 구를 나머지 주소로

import pickle

file_name = "coin.txt"

class CoinCollecter:
    cid = None
    province = None
    city = None
    detail_addr = None
    accumulated_amount = None

    def __init__(self, cid = None, province = None, city = None, detail_addr = None, accumlated_amout = None ):
        self.cid = cid
        self.province = province
        self.city = city
        self.detail_addr = detail_addr
        self.accumulated_amount = accumlated_amout
        self.load();

    # add amount
    def add_money(self, amount):
        self.accumulated_amount += amount

    # Load coincollecter info from file
    # it must substitue to c-s communication
    def load(self):
        try:
            fin = open(file_name, 'rb')
            data = pickle.load(fin) # deserialization

            self.cid = data.cid
            self.province = data.province
            self.city = data.city
            self.detail_addr = data.detail_addr
            self.accumulated_amount = data.accumulated_amount

            fin.close()

        except:
            # default info
            self.cid = 0
            self.province = "대구광역시"
            self.city = "산격동"
            self.detail_addr = "경북대학교"
            self.accumulated_amount = 0


    def save(self):
        fout = open(file_name, 'wb')

        pickle.dump(self, fout) # serialization
        fout.close()



