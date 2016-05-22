# client.py
# it establishes connection between server and coin collector
# performs communication, and provides interface for coincollector
import socket

class Client:
    def __init__(self, host=None, port=None,  decode_type = None):
        # set port and host
        self.host = 'localhost' if host == None else host
        self.port = 5555 if port == None else port
        # set decode_type
        self.decode_type = 'euc-kr' if decode_type == None else decode_type

        # create socket
        try:
            self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        except socket.error as err:
            print("socket creation was failed with error: %s", err)


    # connect to the server
    def connect(self):
        try:
            self.socket.connect((self.host, self.port))
        except socket.error as err:
            print("connection to server was failed: %s", err)

    # send message
    def send(self, msg):
        try:
            self.socket.sendall(bytes(msg, 'utf-8'))
        except IOError as err:
            print("I/O exception: %s", err)

    # recv message
    def recv(self):
        try:
            # decode bytes and remove '\n', '\r'
            rev_msg = (self.socket.recv(4096)).decode(self.decode_type)[:-2]
            return rev_msg
        except IOError as err:
            print("I/O exception: %s", err)

    # close connection
    def close_connection(self):
        try:
            self.socket.close()
        except socket.error as err:
            print("close socket was failed with error: %s", err)
