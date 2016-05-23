# widget.py
# it implements enum type for widget index
from enum import Enum

class Widget_type(Enum):
    intro = 0
    get_barcode = 1
    insert_coin = 2
    orglist = 3
    save_result = 4
    donate_result = 5
    error_message = 6

