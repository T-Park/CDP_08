# packet.py
# assemble and deassemble packet

class Packet:
    def __init__(self):
        self.start_flag = '#' # flag for packet
        self.arg_spliter = '%' # flag for argument spliter

    # assemble packet
    def assemble_packet(self, packet_type, *args):
        packet = self.start_flag + packet_type.value
        for arg in args:
            packet += self.arg_spliter + str(arg)
        packet += '\n'

        return packet

    # deassemble_packet
    def deassemble_packet(self, msg):
        return msg.split(self.arg_spliter)


string = "a%b%c"
a,b,c = string.split('%')
print(a,b,c)

