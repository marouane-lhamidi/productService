package com.example.productservice.common.utiles;


import com.example.productservice.common.exception.HttpUnauthorizedException;
import com.example.productservice.common.exception.InvalidInputException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Utiles {
    public static final Random RANDOM = new Random();
    private Utiles(){}

    public static <E extends RuntimeException> Map<String, String> decodeJwt(String token) {
        var base64Url = new Base64(true);
        String[] jwtTokenValues = token.split("\\.");
        if (jwtTokenValues.length > 2) {
            parseBase64(base64Url, jwtTokenValues[0]);
            HashMap<String, String> payload = new HashMap<>();
            JsonObject jsonPayloadObject = parseBase64(base64Url, jwtTokenValues[1]);
            jsonPayloadObject.entrySet().stream().forEach(element -> {
                if (!(element.getValue()).isJsonNull() && (element.getValue()).isJsonPrimitive()) {
                    payload.put(element.getKey(), (element.getValue()).getAsString());
                } else {
                    payload.put(element.getKey(), null);
                }

            });
            return payload;
        } else {
            throw new HttpUnauthorizedException(" message 1");
        }
    }
    private static JsonObject parseBase64(Base64 base64Url, String value) {
        return JsonParser.parseString(new String(base64Url.decode(value.getBytes()))).getAsJsonObject();
    }

    public static void validateFirstNameAndLastName(String firstName, String lastName, String action)
            throws InvalidInputException {
        if (Utiles.isNotNullAndNotEmpty(firstName) && firstName.trim().length() > 255) {

            throw new InvalidInputException(MessagesCode.TOOMUCHCARACTERSFIRSTNAME);
        }
        if (Utiles.isNotNullAndNotEmpty(lastName) && lastName.trim().length() > 255) {

            throw new InvalidInputException(MessagesCode.TOOMUCHCARACTERSLASTNAME);
        }

        if (Constants.SIGNUP.equals(action)) {
            if (Utiles.isNullOrEmpty(firstName)) {

                throw new InvalidInputException(MessagesCode.EMPTYFIRSTNAME);
            }
            if (Utiles.isNullOrEmpty(lastName)) {

                throw new InvalidInputException(MessagesCode.EMPTYLASTNAME);
            }
        }
    }

    public static void validateDate(Date debut, Date fin)
            throws InvalidInputException {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(debut);
        long diff =  fin.getTime() - debut.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);


        if (diff < 900000)
            throw new InvalidInputException(MessagesCode.THERENDEZVOUSSHOULDBELESS);

        if(minutes % 15 != 0) {
            throw new InvalidInputException(MessagesCode.DURATIONISNOTCORRECT);
        }

        if(calendar1.get(Calendar.MINUTE) != 0 && calendar1.get(Calendar.MINUTE) != 15 && calendar1.get(Calendar.MINUTE) != 30 && calendar1.get(Calendar.MINUTE) != 45) {
            throw new InvalidInputException(MessagesCode.DURATIONISNOTCORRECTNOTSTARTED);
        }



    }

    public static boolean isNotNullAndNotEmpty(String st) {
        return st != null && !st.trim().isEmpty();
    }

    public static boolean isNotNullAndNotEmpty(@SuppressWarnings("rawtypes") Collection c) {
        return c != null && !c.isEmpty();
    }

    public static boolean validateLanguage(String language) {
        return !Utiles.isNotNullAndNotEmpty(language) || language.length() <= 20;
    }

    public static boolean isNullOrEmpty(String st) {
        return st == null || st.trim().isEmpty();
    }

    public static boolean isNullOrEmpty(@SuppressWarnings("rawtypes") Collection c) {
        return c == null || c.isEmpty();
    }

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static boolean isTheSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }
    public static boolean isTheDebutAndTheFinAreCorrect(Date date1, Date date2, Long diff) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
                && minutes % 15 == 0;
    }

    public static Date minusMin(Date date){
        // Adding 10 mins using Date constructor.
        Calendar date1 = Calendar.getInstance();
        date1.setTime(date);
        long timeInSecs = date1.getTimeInMillis();
        return new Date(timeInSecs - (60 * 1000));
    }

    public static byte[] compressImage(byte[] data) {

        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception e) {
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception exception) {
        }
        return outputStream.toByteArray();
    }

}
