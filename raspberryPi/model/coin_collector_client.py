# coin_collector_client.py
# it is client for coin_collector
# it contain coin collector info and socket
# and it receive user info from server, maintain inserted coin ammount, coincollector's state

from model import coin_collecter
from util import service_type
from client import Modong_client

class Coin_collector_clinet():
    def __init__(self):
        self.coin_collector = coin_collecter.CoinCollecter()
        self.connection = Modong_client()
        self.inserted_coin = 0 # inserted coin ammount
        self.current_state = service_type.Service_Type.Beginning  # current service type
        self.target_org_index = None# organization index for target
        self.user = None
        self.input_barcode = None





