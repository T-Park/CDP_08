# client.py
# it establishes connection between server and coin collector
# performs communication, and provides interface for coincollector
import socket


class Client:
    def __init__(self, host=None, port=None, decode_type=None):
        # set port and host
        self.host = 'localhost' if host == None else host
        self.port = 5555 if port == None else port
        print(host, port)
        print(self.host, self.port)

        # set decode_type
        # self.decode_type = 'euc-kr' if decode_type == None else decode_type
        self.decode_type = 'utf-8' if decode_type == None else decode_type

        # create socket
        try:
            self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        except socket.error as err:
            print("socket creation was failed with error: %s", err)

    # connect to the server
    def connect(self):
        try:
            self.socket.connect((self.host, self.port))
            print("success to connect to server")
        except socket.error as err:
            print("connection to server was failed: %s", err)

    # send message
    def send(self, msg):
        try:
            self.socket.sendall(bytes(msg, 'utf-8'))
        except IOError as err:
            print("I/O exception: %s", err)
            print("while sending msg: %s", msg)

    # recv message
    def recv(self):
        try:
            # decode bytes and remove '\n', '\r'
            data = self.socket.recv(4096)
            rev_msg = data.decode(self.decode_type)[:-2]
            return rev_msg
        except IOError as err:
            print("I/O exception: %s", err)
            print("termiate communication")
            self.socket.close()
            return None

    # close connection
    def close_connection(self):
        try:
            self.socket.close()
            print("success close")
        except socket.error as err:
            print("close socket was failed with error: %s", err)
