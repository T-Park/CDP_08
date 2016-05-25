# packet_type.py
# Declare packet type

from enum import Enum

class Packet_type(Enum):
    login = "Login"
    logout = "Logout"
    update_info = "UpdateInfo"
    get_org_num = "GetOrgNum"
    get_org_list = "GetOrg"
    get_user_info = "GetUserInfo"
    save_point = "SavePoint"
    donate_point = "DonatePoint"
