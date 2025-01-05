package com.frew.crew.user;

import com.frew.crew.card.CardRepository;
import com.frew.crew.role.Role;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class UserDataGenerator {
    private static final String[] FIRST_NAMES = {"Mohammed", "Fatima", "Youssef", "Amina", "Hassan", "Laila",
            "Karim", "Nadia", "Omar", "Samira", "Ahmed", "Zineb", "Rachid", "Houda"};
    private static final String[] LAST_NAMES = {"Alami", "Benani", "Idrissi", "Mansouri", "Tazi", "El Fassi",
            "Benjelloun", "Chraibi", "Bennani", "Elomari", "Ziani", "Berrada"};
    private final CardRepository cardRepository;
    private static final Random random = new Random();

    public UserDataGenerator(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public static List<User> generateUsers(int count) {
        List<User> users = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            users.add(generateRandomUser());
        }
        return users;
    }

    private static User generateRandomUser() {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        String email = generateEmail(firstName, lastName);
        String password = UUID.randomUUID().toString().substring(0, 10);
        Role role = random.nextFloat() < 0.1 ? Role.MANAGER : Role.USER;
        BigDecimal balance = new BigDecimal(random.nextInt(10000)).divide(new BigDecimal("100"));

        return createUser(firstName, lastName, email, password, role, balance);
    }

    private static String generateEmail(String firstName, String lastName) {
        return firstName.toLowerCase().charAt(0) + "." +
                lastName.toLowerCase() + random.nextInt(1000) + "@usms.ac.ma";
    }

    private static User createUser(String firstName, String lastName, String email,
                                   String password, Role role, BigDecimal balance) {

        return new User.UserBuilder().firstName(firstName).lastName(lastName).email(email).password(password).role(role).build();
    }
}