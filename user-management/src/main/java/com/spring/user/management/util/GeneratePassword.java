package com.spring.user.management.util;


import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

public class GeneratePassword {
    public static String passwordGeneration(){
        CharacterRule lowerCase=new CharacterRule(EnglishCharacterData.LowerCase);
        lowerCase.setNumberOfCharacters(2);

        CharacterRule upperCase=new CharacterRule(EnglishCharacterData.UpperCase);
        upperCase.setNumberOfCharacters(3);

        CharacterRule numbers=new CharacterRule(EnglishCharacterData.Digit);
        numbers.setNumberOfCharacters(1);

        CharacterRule special=new CharacterRule(EnglishCharacterData.Special);
        special.setNumberOfCharacters(2);

        PasswordGenerator passGen=new PasswordGenerator();
        String password=passGen.generatePassword(8,lowerCase,upperCase,numbers,special);

        return new String(password);
    }
    public static void main(String[] args){
        String pass=passwordGeneration();

        System.out.println("password is   "+pass);

    }
}