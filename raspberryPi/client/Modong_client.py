# Modong_client.py
# it extends client and proviode several application releated functions
import client

class Modong_client(client):
    def __init__(self, port = None, host = None, decode_type = None):
        client.__init__(port, host, decode_type)

    def login(self):
        pass

    def update_modong_info(self):
        pass

    def get_orglist(self):
       pass

    def get_user_info(self):
        pass

    def save_point(self):
        pass

    def donate_point(self):
        pass
