# organization.py
# it contain organization's info

class Organization():
    def __init__(self, did=None, name=None, point=None, tel=None, type=None):
        self.did = did
        self.name = name
        self.point = point
        self.tel = tel
        self.type = type

        self.header = ["name","type","tel","point"] # for table hader
        self.var = [self.name, self.type, self.tel, self.point] # for table access


