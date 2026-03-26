package com.sportsmanager.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NameGenerator {
    private static final Random random = new Random();

    private static final List<String> FIRST_NAMES = Arrays.asList(
        "Ahmet", "Mehmet", "Ali", "Mustafa", "Ibrahim", "Hasan", "Husseyin", "Omer", "Yusuf", "Murat",
        "Emre", "Serkan", "Burak", "Arda", "Caner", "Kerem", "Oguzhan", "Hakan", "Volkan", "Cenk",
        "James", "John", "Michael", "David", "Carlos", "Marco", "Lucas", "Luca", "Pierre", "Antoine",
        "Mohamed", "Omar", "Karim", "Riyad", "Sadio", "Victor", "Neymar", "Paulo", "Bruno", "Bernardo",
        "Erling", "Kevin", "Kylian", "Vinicius", "Rodri", "Pedri", "Gavi", "Jamal", "Florian", "Bukayo"
    );

    private static final List<String> LAST_NAMES = Arrays.asList(
        "Yilmaz", "Kaya", "Demir", "Celik", "Sahin", "Dogan", "Arslan", "Ozturk", "Aydin", "Ozdemir",
        "Turan", "Yilmazer", "Torun", "Caliskan", "Bozkurt", "Kaplan", "Polat", "Erdogan", "Acar", "Gunes",
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Martinez", "Lopez", "Hernandez", "Moore",
        "Silva", "Santos", "Ferreira", "Oliveira", "Costa", "Muller", "Schneider", "Becker", "Hoffman", "Weber",
        "Benzema", "Mbappe", "Pogba", "Griezmann", "Haaland", "De Bruyne", "Salah", "Firmino", "Mane", "Diaz"
    );

    private static final List<String> COACH_FIRST_NAMES = Arrays.asList(
        "Roberto", "Carlo", "Pep", "Jurgen", "Jose", "Diego", "Zinedine", "Antonio", "Mauricio", "Thomas",
        "Senol", "Fatih", "Ersun", "Abdullah", "Yilmaz", "Riza", "Ersoy", "Serdar", "Bulent", "Samet"
    );

    private static final List<String> COACH_LAST_NAMES = Arrays.asList(
        "Mancini", "Ancelotti", "Guardiola", "Klopp", "Mourinho", "Simeone", "Zidane", "Conte", "Pochettino", "Tuchel",
        "Gunes", "Terim", "Yanal", "Avci", "Demir", "Calimbay", "Yilmaz", "Kaplan", "Kocaman", "Bulbul"
    );

    public static String generateName() {
        String first = FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size()));
        String last = LAST_NAMES.get(random.nextInt(LAST_NAMES.size()));
        return first + " " + last;
    }

    public static String generateCoachName() {
        String first = COACH_FIRST_NAMES.get(random.nextInt(COACH_FIRST_NAMES.size()));
        String last = COACH_LAST_NAMES.get(random.nextInt(COACH_LAST_NAMES.size()));
        return first + " " + last;
    }
}
