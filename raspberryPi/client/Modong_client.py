# Modong_client.py
# it extends client and proviode several application releated functions
# assume recv message's first arguments has status like error, success

from client import r_packet, r_packet_type, r_client
from model import organization, user

class Modong_client(r_client.Client):
    def __init__(self, port = None, host = None, decode_type = None):
        r_client.Client.__init__(self, port, host, decode_type)
        self.packet = r_packet.Packet()

    # login to server and get Modong info
    # #CcLogin%cid
    def login(self, cid):
        self.send(self.packet.assemble_packet(r_packet_type.Packet_type.login, cid)) # send message to server
        recv_msg = self.packet.deassemble_packet(self.recv()) # recv message from server
        if recv_msg[0] == "Error":
            print(recv_msg[1])
            return None
        else:
            print("login success")
            return recv_msg[1] # return aquired acculmulated amount

    # logout to server
    # #CcLogout
    def logout(self):
        self.send(self.packet.assemble_packet(r_packet_type.Packet_type.logout)) # send message to server
        recv_msg = self.packet.deassemble_packet(self.recv()) # recv message from server
        if recv_msg[0] == "Error":
            print(recv_msg[1])
        else:
            print("logout success")

    # update server info
    # #CcUpdateInfo
    def update_modong_info(self, coin_collector):
        self.send(self.packet.assemble_packet(r_packet_type.Packet_type.update_info, coin_collector.accumulated_amount)) # send message to server
        recv_msg = self.packet.deassemble_packet(self.recv()) # recv message from server
        if recv_msg[0] == "Error":
            print(recv_msg[1])
        else:
            print("update success")


    # get number of organization
    # CcGetOrgNum
    def get_orglist_num(self):
        self.send(self.packet.assemble_packet(r_packet_type.Packet_type.get_org_num)) # send message to server
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
            for index in range(1,orglist_num+1):
                org = organization.Organization()
                self.send(self.packet.assemble_packet(r_packet_type.Packet_type.get_org_list, index)) # send message to server
                recv_msg = self.packet.deassemble_packet(self.recv()) # recv message from server
                if recv_msg[0] == "Error":
                    print(recv_msg[1])
                # order : did, name, point, tel, type
                else:
                    org.did, org.name, org.point, org.tel, org.type = recv_msg[1:6] # set org info
                    for item in recv_msg:
                        print( item, end=' ')
                    print()
                    orglist.append(org)

            # print("ex: ", orglist[0].did,orglist[0].name,orglist[0].point,orglist[0].tel, orglist[0].type)
            return orglist # return result
        return None

    # get user info
    def get_user_info(self, user_barcode):
        self.send(self.packet.assemble_packet(r_packet_type.Packet_type.get_user_info, user_barcode))
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
        print("save input: ", user_barcode, point)
        # user_barcode = str(user_barcode)
        # if user_barcode[-1] != '\n':
        #     user_barcode += '\n'

        self.send(self.packet.assemble_packet(r_packet_type.Packet_type.save_point, user_barcode, point))
        recv_msg = self.packet.deassemble_packet(self.recv()) # recv message from server
        if recv_msg[0] == "Error":
            print(recv_msg[1])
        else:
            print("save success")

    # donate point
    def donate_point(self, did, user_barcode, point):
        # user_barcode = str(user_barcode)
        # if user_barcode[-1] != '\n':
        #     user_barcode += '\n'

        print("save input: ", did, user_barcode, point)
        self.send(self.packet.assemble_packet(r_packet_type.Packet_type.donate_point, did, user_barcode, point))
        recv_msg = self.packet.deassemble_packet(self.recv()) # recv message from server
        if recv_msg[0] == "Error":
            print(recv_msg[1])
        else:
            print("donate success")


