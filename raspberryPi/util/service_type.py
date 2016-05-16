# service_type.py
# it implements enum type for service type
# 1. Save
# 2. Donate

from enum import Enum

class Service_Type(Enum):
    Save = 0
    Donate = 1
    Beginning = 2 # initial state