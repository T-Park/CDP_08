# user.py
# it contain user's info
# not contain entire but only required info

class User:
    def __init__(self, uid=None, id=None, name=None, point=None, donated_amount=None, phone_number=None, barcode=None):
        self.uid = uid
        self.id = id
        self.name = name
        self.point = point
        self.donated_amount = donated_amount
        self.phone_number = phone_number
        self.barcode = barcode
