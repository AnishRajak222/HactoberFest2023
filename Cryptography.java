abstract class Cryptography {
    abstract String encode(String plainText);

    abstract String decode(String encryptedText);
}

class CaesarCipher extends Cryptography {
    private int shift;

    public CaesarCipher(int shift) {
        this.shift = shift;
    }

    @Override
    String encode(String plainText) {
        StringBuilder encryptedText = new StringBuilder();
        for (char c : plainText.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                encryptedText.append((char) (base + (c - base + shift) % 26));
            } else {
                encryptedText.append(c);
            }
        }
        return encryptedText.toString();
    }

    @Override
    String decode(String encryptedText) {
        StringBuilder plainText = new StringBuilder();
        for (char c : encryptedText.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                plainText.append((char) (base + (c - base - shift) % 26));
            } else {
                plainText.append(c);
            }
        }
        return plainText.toString();
    }
}

class TranspositionCipher extends Cryptography {
    private char[] key;

    public TranspositionCipher(String key) {
        this.key = key.toCharArray();
    }

    @Override
    String encode(String plainText) {
        char[] encryptedText = new char[plainText.length()];
        encryptedText = plainText.toCharArray();
        int i=0;
        while( i<plainText.length() && i < key.length){
            char a = key[i];
            int temp_index = a - '0';
            char temp;
            if(temp_index< plainText.length()){
            temp = encryptedText[temp_index];
            encryptedText[temp_index] = encryptedText[i];
            encryptedText[i]= temp;
            }
            i++;
        }
        return new String(encryptedText);
    }

    @Override
    String decode(String encryptedText) {
        char[] decryptedText = new char[encryptedText.length()];
        decryptedText= encryptedText.toCharArray();
        int i= key.length<= encryptedText.length() ? key.length-1 : encryptedText.length()-1;
         while(i >-1){
            char a = key[i];
            int temp_index = a - '0';
            char temp;
            if(temp_index< encryptedText.length()){
            temp = decryptedText[temp_index];
            decryptedText[temp_index] = decryptedText[i];
            decryptedText[i] = temp;
            }
            i--;
        }
        return new String(decryptedText);
    }
}

public class Main {
    public static void main(String[] args) {
        Cryptography caesarCipher = new CaesarCipher(3);
        Cryptography transpositionCipher = new TranspositionCipher("24561457884969");

        String plainText = "Hello, World!";
        String encryptedCaesar = caesarCipher.encode(plainText);
        String decryptedCaesar = caesarCipher.decode(encryptedCaesar);

        String encryptedTransposition = transpositionCipher.encode(plainText);
        String decryptedTransposition = transpositionCipher.decode(encryptedTransposition);

        System.out.println("Original Text: " + plainText);
        System.out.println("Caesar Encrypted Text: " + encryptedCaesar);
        System.out.println("Caesar Decrypted Text: " + decryptedCaesar);
        System.out.println("Transposition Encrypted Text: " + encryptedTransposition);
        System.out.println("Transposition Decrypted Text: " + decryptedTransposition);
    }
}
