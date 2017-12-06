package QRCode;

public class NonValidQRCodeMessageException extends Exception {
    public String message;
    public NonValidQRCodeMessageException(){
        //TODO: reword this if WR codes cannot handle some characters
        this.message = "QR Code message is too long";
    }
}