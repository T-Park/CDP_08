# barcode_reader.py
# take picture and get barcode
# it use zxing
#        python-zxing opensource

import zxing
import picamera

class BarcodeReader():

    camera = None
    reader = None

    image_file = "image.jpg"

    def __init__(self):
        self.camera = picamera.PiCamera()
        self.reader = zxing.BarCodeReader("/home/pi/zxing/zxing")
        
    def take_picture(self):
        self.camera.start_preview()
        self.camera.capture(self.image_file) # take picture
        self.camera.stop_preview()
                                # and store

    def parse_barcode(self):
        barcode = self.reader.decode(self.image_file) # get barcode using zxing
                                            # library

        if barcode == None :
            return False, None
        else :
            return True, barcode.data
        
        
        
    
    
