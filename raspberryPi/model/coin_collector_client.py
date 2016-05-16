# coin_collector_client.py
# it is client for coin_collector
# it contain coin collector info and socket
# and it receive user info from server, maintain inserted coin ammount, coincollector's state

from model import coin_collecter
from util import service_type

class Coin_collector_clinet():
    def __init__(self):
        self.coin_collector = coin_collecter.CoinCollecter()
        # self.socket = socket
        self.inserted_coin = 0
        self.current_state = service_type.Service_Type.Beginning
        self.user = None
        self.input_barcode = None


