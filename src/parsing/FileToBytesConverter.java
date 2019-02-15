package parsing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileToBytesConverter {

    //Vet inte om det fungerar att skicka in en fil (och pathen till den automatisk ingår) eller om det är lättare
    //att bara skicka in en sträng med den path till filen man vill åt. Troligtvis är den andra metoden bättre eftersom
    //en path till en fil baseras relativt på var denna klassen ligger men vi kan ju testa och se.


    public byte[] convertFileToBytes(File file){

        byte[] fileContentInBytes = null;

        try {
            fileContentInBytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContentInBytes;
    }



    public byte[] convertFileToBytes2(String pathToFile){

        File file = new File(pathToFile);
        byte[] fileContentInBytes = null;

        try {
            fileContentInBytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContentInBytes;
    }





}
