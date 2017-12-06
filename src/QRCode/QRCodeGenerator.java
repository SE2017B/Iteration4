package QRCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class QRCodeGenerator {

    private final int width;
    private final int height;
    private final int white;
    private final int black;
    private final int lineBound;

    public QRCodeGenerator(){
        this.width = 256;
        this.height = 256;
        //The << operator shits bit pattern to the left
        this.white = 255 << 16 | 255 << 8 | 255;
        this.black = 0;
        this.lineBound = 20;
    }

    //Call this method of you premptively know that your message will be longer than 35 lines. This will generate multiple files
    //Each file will be names Filename + number starting from 1 to indicate order of qr codes to be displayed
    public void writeLongQRList(ArrayList<String> list, String filename) throws NonValidQRCodeMessageException {
        ArrayList<ArrayList<String>> cutLists = cutLists(list);
        int iteration = 1;
        for(ArrayList<String> tempList: cutLists){
            writeQRList(tempList, (filename + "_Order:" + iteration));
            iteration++;
        }
    }

    //This will generate one QR code, and throws an expression if the lines are over the limit
    public void writeQRList(ArrayList<String> list, String filename) throws NonValidQRCodeMessageException {
        String path = "";
        for(String direction: list){
            String endString = ".";
            if(!((list.indexOf(direction) + 1) == list.size()))
                endString = ".\n";
            path += direction + endString;
        }
        writeQRCode(path, filename);
    }

    //This writes a QR code from a string. Call this if you want to print something basic.
    public void writeQRCode(String message, String filename) throws NonValidQRCodeMessageException{
        if(!validMessage(message)){
            throw new NonValidQRCodeMessageException();
        }
        QRCodeWriter writer = new QRCodeWriter();
        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB); // create an empty image
        try {
            BitMatrix bitMatrix = writer.encode(message, BarcodeFormat.QR_CODE, this.width, this.height);
            for (int i = 0; i < this.width; i++) {
                for (int j = 0; j < this.height; j++) {
                    image.setRGB(i, j, bitMatrix.get(i, j) ? this.black : this.white); // set pixel one by one
                }
            }

            try {
                ImageIO.write(image, "jpg", new File(filename + ".jpg")); // save QR image to disk
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        QRCodeGenerator gen = new QRCodeGenerator();
        ArrayList<String> samplelist = new ArrayList<>();
        //Go straight from Retail C001L1, Take a right at Hallway C009L1, Go down Elevator KL1 until floor L2, Exit Elevator KL2 and
        //towards Lab C002L2, Take a right at Lab C002L2, Continue straight past  Service C001L2, Stop at Restroom C002L2]
        samplelist.add("Go straight from Retail C001L1");
        samplelist.add("Take a right at Hallway C009L1");
        samplelist.add("Go down Elevator KL1 until floor L2");
        samplelist.add("Exit Elevator KL2 and continue towards Lab C002L2");
        samplelist.add("Take a right at Lab C002L2");
        samplelist.add("Continue straight past  Service C001L2");
        samplelist.add("Stop at Restroom C002L2");

        samplelist.add("Look forward");
        samplelist.add("Look back");
        samplelist.add("Look ahead again");
        samplelist.add("Fuck okay look at the kiosk I forgot where you are heading");
        samplelist.add("Nevermind I remembered go forward");
        samplelist.add("You have arrived dipshit");
        samplelist.add("Look forward");
        samplelist.add("Look back");
        samplelist.add("Look ahea again");
        samplelist.add("Fuck okay look at the kiosk I forgot where you are heading");
        samplelist.add("Nevermind I remembered go forward");

        samplelist.add("You have arrived dipshit");
        samplelist.add("Look forward");
        samplelist.add("Look back");
        samplelist.add("Look ahead again");
        samplelist.add("Fuck okay look at the kiosk I forgot where you are heading");
        samplelist.add("Nevermind I remembered go forward");
        samplelist.add("You have arrived dipshit");
        samplelist.add("Look forward");
        samplelist.add("Look back");
        samplelist.add("Look ahead again");

        samplelist.add("Fuck okay look at the kiosk I forgot where you are heading");
        samplelist.add("Nevermind I remembered go forward");
        samplelist.add("LASTLINE HEHHEHHEHHE");
        samplelist.add("Look forward");
        samplelist.add("Look back");
        samplelist.add("Look ahead again");
        samplelist.add("HELLO THIS IS TESTING THE MAX LINE LIMIT");
        samplelist.add("Look forward");
        samplelist.add("Look forward");
        samplelist.add("Look forward");

        samplelist.add("Look forward");
        samplelist.add("Look forward");
        samplelist.add("LAST LINE AGAIN - This is the end of 1");
        samplelist.add("Look forward - beginning of 2");
        samplelist.add("Look back");
        samplelist.add("Look ahead again");
        samplelist.add("Fuck okay look at the kiosk I forgot where you are heading");
        samplelist.add("Nevermind I remembered go forward");
        samplelist.add("You have arrived dipshit");
        samplelist.add("Look forward");

        samplelist.add("Look back");
        samplelist.add("Look ahead again");
        samplelist.add("Fuck okay look at the kiosk I forgot where you are heading");
        samplelist.add("Nevermind I remembered go forward");
        samplelist.add("You have arrived dipshit");
        samplelist.add("Look forward");
        samplelist.add("Look back");
        samplelist.add("Look ahead again");
        samplelist.add("Fuck okay look at the kiosk I forgot where you are heading");
        samplelist.add("Nevermind I remembered go forward");

        samplelist.add("You have arrived dipshit");
        samplelist.add("Look forward");
        samplelist.add("Look back");
        samplelist.add("Look ahead again");
        samplelist.add("Fuck okay look at the kiosk I forgot where you are heading");
        samplelist.add("Nevermind I remembered go forward");
        samplelist.add("LASTLINE HEHHEHHEHHE");
        samplelist.add("Look forward");
        samplelist.add("Look back");
        samplelist.add("Look ahead again");

        samplelist.add("HELLO THIS IS TESTING THE MAX LINE LIMIT");
        samplelist.add("Look forward");
        samplelist.add("Look forward");
        samplelist.add("Look forward");
        samplelist.add("Look forward");
        samplelist.add("Look forward");
        samplelist.add("LAST LINE AGAIN");
        samplelist.add("Look forward");
        samplelist.add("Look forward");
        samplelist.add("Look forward");

        samplelist.add("Look forward");
        samplelist.add("Look forward");
        samplelist.add("LAST LINE AGAIN");

        try{
            //gen.writeQRList(samplelist, "directions");
            //gen.writeQRCode("OwO what's this?*notices your QR code*", "NathanSiegel");
            //gen.writeQRCode("( ͡° ͜ʖ ͡°)", "lenny");
            gen.writeQRCode("https://www.google.com/search?q=puppy&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiwhKK62_TXAhXFNSYKHf9oCZ4Q_AUICigB&biw=1920&bih=990", "kylerEllo");
        }
        catch (NonValidQRCodeMessageException e){
            System.out.println("Fix it :(");
        }
    }

    /////////////////////
    //  Helpers Below  //
    /////////////////////
    private boolean validMessage(String message){
        //35 lines of text seems to be the max
        //TODO check if its a character limit and not a newline limit in iteration 4
        int newLines = 0;
        for(int i = 1; i < message.length(); i++)
            if(message.substring(i-1, i).equals(".")){
                newLines++;
            }
        return newLines <= this.lineBound;
    }

    private ArrayList<ArrayList<String>> cutLists(ArrayList<String> list) {
        ArrayList<ArrayList<String>> returnVal = new ArrayList<>();
        int iteration = 1;
        System.out.println(list.size());
        ArrayList<String> tempList = new ArrayList<>();
        for(String line: list){
            if((!(iteration < this.lineBound)) && iteration%this.lineBound == 0){
                returnVal.add(tempList);
                tempList = new ArrayList<>();
                System.out.println("Making a newlist");
            }
            tempList.add(line);
            if(iteration+1 > list.size()){
                returnVal.add(tempList);
                System.out.println("terminating the loop");
            }
            iteration++;
        }
        return returnVal;
    }
}
