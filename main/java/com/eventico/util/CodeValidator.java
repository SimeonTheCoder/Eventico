package com.eventico.util;

public class CodeValidator {
    public static int validate(String code) {
        int decimal = 0;
        code = code.toLowerCase();

        for(int i = 0; i < code.length(); i++) {
            if(code.charAt(i) >= '0' && code.charAt(i) <= '9') {
                decimal += (code.charAt(i) - '0') * Math.pow(16, code.length() - i - 1);
            }else if(code.charAt(i) >= 'a' && code.charAt(i) <= 'f') {
                decimal += ((code.charAt(i) - 'a') + 10) * Math.pow(16, code.length() - i - 1);
            }
        }

        String asString = String.valueOf(decimal);
        int sumOfDigits = 0;

        for(int i = 0; i < asString.length(); i++) {
            sumOfDigits += asString.charAt(i) - '0';
        }

        if(decimal % 10 == 5 && sumOfDigits == 27) {
            return 0;
        }else if(decimal % 10 == 7 && sumOfDigits == 23) {
            return 1;
        } else if(decimal % 10 == 5 && sumOfDigits == 37) {
            return 2;
        }

        System.out.println(decimal);

        return -1;
    }
}
