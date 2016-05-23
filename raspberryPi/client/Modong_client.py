# Modong_client.py
# it extends client and proviode several application releated functions
# assume recv message's first arguments has status like error, success

import client
import packet
import packet_type
from model import organization, user

class Modong_client(client.Client):
    def __init__(self, port = None, host = None, decode_type = None):
        client.__init__(port, host, decode_type)
        self.packet = packet.Packet()

    # login to server and get Modong info
    def login(self, cid):
        self.send(self.packet.assemble_packet(packet_type.login, cid)) # send message to server
        recv_msg = self.packet.deassemble_packet(self.recv()) # recv message from server
        if recv_msg[0] == "Error":
            print(recv_msg[1])
        else:
            print("login success")

    # logout to server
    def logout(self):
        self.send(self.packet.assemble_packet(packet_type.logout)) # send message to server
        recv_msg = self.packet.deassemble_packet(self.recv()) # recv message from server
        if recv_msg[0] == "Error":
            print(recv_msg[1])
        else:
            print("logout success")

    # update server info
    def update_modong_info(self, coin_collector):
        self.send(self.packet.assemble_packet(packet_type.update_info, coin_collector.accumulated_amount)) # send message to server
        recv_msg = self.packet.deassemble_packet(self.recv()) # recv message from server
        if recv_msg[0] == "Error":
            print(recv_msg[1])
        else:
            print("update success")


    # get number of organization
    def get_orglist_num(self):
        self.send(self.packet.assemble_packet(packet_type.get_org_num)) # send message to server
        recv_msg = self.packet.deassemble_packet(self.recv()) # recv message from server
        if recv_msg[0] == "Error":
            print(recv_msg[1])
            return None
        else:
            return int(recv_msg[1]) # return num of orglist
    # get list of organization
    def get_orglist(self):
        orglist_num = self.get_orglist_num()
        if orglist_num != None:
            orglist = list()
            for index in range(orglist_num):
                org = organization.Organization()
                self.send(self.packet.assemble_packet(packet_type.get_org_list, index)) # send message to server
                recv_msg = self.packet.deassemble_packet(self.recv()) # recv message from server
                if recv_msg[0] == "Error":
                    print(recv_msg[1])
                # order : did, name, point, tel, type
                else:
                    org.did, org.name, org.point, org.tel, org.type = recv_msg[1:] # set org info
                    orglist.append(org)
            return orglist # return result
        return None

    # get user info
    def get_user_info(self, user_barcode):
        self.send(self.packet.assemble_packet(packet_type.get_user_info, user_barcode))
        recv_msg = self.packet.deassemble_packet(self.recv()) # recv message from server
        if recv_msg[0] == "Error":
            print(recv_msg[1])
            return None
        # order : uid, id, name, point, donated_amount, phone_number
        else:
            usr = user.User()
            usr.uid, usr.id, usr.name, usr.point, usr.donated_amount, usr.phone_number = recv_msg[1:]
            return usr

    # save point
    def save_point(self, user_barcode, point):
        self.send(self.packet.assemble_packet(packet_type.save_point, user_barcode, point))
        recv_msg = self.packet.deassemble_packet(self.recv()) # recv message from server
        if recv_msg[0] == "Error":
            print(recv_msg[1])
        else:
            print("save success")

    # donate point
    def donate_point(self, did, point):
        self.send(self.packet.assemble_packet(packet_type.donate_point, did, point))
        recv_msg = self.packet.deassemble_packet(self.recv()) # recv message from server
        if recv_msg[0] == "Error":
            print(recv_msg[1])
        else:
            print("donate success")


