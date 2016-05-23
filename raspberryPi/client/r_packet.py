# packet.py
# assemble and deassemble packet

class Packet:
    def __init__(self):
        self.sufix = 'Cc'
        self.start_flag = '#'  # flag for packet
        self.arg_spliter = '%'  # flag for argument spliter

    # assemble packet
    def assemble_packet(self, packet_type, *args):
        packet = self.start_flag + self.sufix + packet_type.value
        for arg in args:
            packet += self.arg_spliter + str(arg)
        packet += '\n'

        return packet

    # deassemble_packet
    def deassemble_packet(self, msg):
        return msg[1:].split(self.arg_spliter)
