# barcode_reader.py
# take picture and get barcode
# it use zxing
#        python-zxing opensource

import zxing
import picamera

class BarcodeReader():

    def __init__(self):
        self.camera = picamera.PiCamera()
        # test path
        # self.reader = zxing.BarCodeReader("d:\zxing\zxing")
        self.reader = zxing.BarCodeReader("/home/pi/zxing/zxing")
        self.image_file = "../model/image.jpg"
        
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

    def run(self):
        self.take_picture()
        return self.parse_barcode()
        
        
        
    
    
