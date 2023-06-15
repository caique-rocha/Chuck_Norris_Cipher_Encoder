package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        label:
        while (true) {

            System.out.println("Please input operation (encode/decode/exit):");
            String option = scanner.nextLine();
            switch (option) {
                case "exit":
                    System.out.println("Bye!");
                    break label;
                case "encode":
                    StringBuilder charAsBinary = new StringBuilder();
                    System.out.println("Input string:");
                    String input = scanner.nextLine();
                    for (int i = 0; i < input.length(); i++) {
                        char character = input.charAt(i);
                        charAsBinary.append(String.format("%7s", Integer.toBinaryString(character)).replace(" ", "0"));
                    }
                    System.out.println("Encoded string:");
                    System.out.println(chuckNorrisEncode(charAsBinary.toString()));
                    System.out.println();
                    break;
                case "decode":
                    System.out.println("Input encoded string:");
                    String decode = scanner.nextLine();


                    System.out.println(chuckNorrisDecode(decode));
                    System.out.println();
                    break;
                default:
                    System.out.printf("There is no '%s' operation\n\n", option);
                    break;
            }
        }

    }

    private static String chuckNorrisEncode(String charAsBinary) {

        StringBuilder encode = new StringBuilder(charAsBinary.charAt(0) == '0' ? "00 " : "0 ");
        char currentBlock = charAsBinary.charAt(0) == '0' ? '0' : '1';

        for (int i = 0; i < charAsBinary.length(); i++) {

            if (charAsBinary.charAt(i) == currentBlock) {
                encode.append("0");
            } else {
                encode.append(charAsBinary.charAt(i) == '0' ? " 00 0" : " 0 0");
                currentBlock = charAsBinary.charAt(i) == '0' ? '0' : '1';
            }
        }

        return encode.toString();
    }

    private static String chuckNorrisDecode(String inputCipher) {

        if (!inputCipher.startsWith("0 ") && !inputCipher.startsWith("00 ") ||
        inputCipher.replace(" ", "").length() % 2 != 0 ) {
            return "Encoded string is not valid.";
        }

        String[] splitCipher = inputCipher.split(" ");
        StringBuilder binaryDecode = new StringBuilder();
        StringBuilder decode = new StringBuilder();



        for (int i = 0; i < splitCipher.length - 1; i+=2) {
            if (splitCipher[i].equals("0")) {
                binaryDecode.append(splitCipher[i + 1].replace("0", "1"));
            } else if (splitCipher[i].equals("00")) {
                binaryDecode.append(splitCipher[i + 1]);
            } else { // is invalid sequence
                binaryDecode.append("X");
            }

        }

        if (binaryDecode.length() % 7 != 0) {
            return "Encoded string is not valid.";
        }
        String[] binaryArray = binaryDecode.toString().split("(?<=\\G.{7})");

        try {
            for (String binary : binaryArray) {
                int decimal = Integer.parseInt(binary, 2);
                char character = (char) decimal;
                decode.append(character);
            }
        } catch (NumberFormatException e) {
            return "Encoded string is not valid.";
        }

        System.out.println("Decoded string:");
        return decode.toString();
    }
}