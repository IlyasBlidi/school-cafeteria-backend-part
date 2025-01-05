package com.frew.crew.config;

import com.frew.crew.article.Article;
import com.frew.crew.card.Card;
import com.frew.crew.category.Category;
import com.frew.crew.category.CategoryRepository;
import com.frew.crew.article.ArticleRepository;
import com.frew.crew.user.User;
import com.frew.crew.user.UserDataGenerator;
import com.frew.crew.user.UserRepository;
import com.frew.crew.role.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            CategoryRepository categoryRepository,
            ArticleRepository articleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (categoryRepository.count() == 0) {
                // Initialize Categories
                List<Category> categories = Arrays.asList(
                        createCategory("Breakfast", "Morning meals served from 7AM to 10:30AM."),
                        createCategory("Lunch", "Main dining period from 11:30AM to 2:30PM."),
                        createCategory("Snacks", "Available throughout operating hours."),
                        createCategory("Beverages", "Hot and cold drinks available throughout operating hours."),
                        createCategory("Side Dishes", "Complementary dishes to accompany main meals."),
                        createCategory("Desserts", "Sweet treats and dessert options."),
                        createCategory("Appetizer", "Starter dishes to begin your meal."),
                        createCategory("Soup", "Hot soups and broths available daily.")
                );
                categoryRepository.saveAll(categories);

                // Initialize Articles (Menu Items)
                Map<String, List<Article>> articlesByCategory = new HashMap<>();

                // Breakfast Items
                articlesByCategory.put("Breakfast", Arrays.asList(
                        createArticle("Moroccan Msemen", "Fresh flatbread served with honey and butter", new BigDecimal("15.00"), categories.get(0)),
                        createArticle("Baghrir", "Thousand-hole pancake with butter and honey", new BigDecimal("12.00"), categories.get(0)),
                        createArticle("Moroccan Omelette", "Eggs with tomatoes, onions, and spices", new BigDecimal("25.00"), categories.get(0))
                ));

                // Lunch Items
                articlesByCategory.put("Lunch", Arrays.asList(
                        createArticle("Couscous Royal", "Traditional Friday couscous with vegetables and meat", new BigDecimal("45.00"), categories.get(1)),
                        createArticle("Tajine Kefta", "Meatballs with eggs in tomato sauce", new BigDecimal("40.00"), categories.get(1)),
                        createArticle("Chicken Tajine", "With preserved lemons and olives", new BigDecimal("42.00"), categories.get(1))
                ));

                // Beverages
                articlesByCategory.put("Beverages", Arrays.asList(
                        createArticle("Moroccan Mint Tea", "Traditional green tea with fresh mint", new BigDecimal("8.00"), categories.get(3)),
                        createArticle("Fresh Orange Juice", "Freshly squeezed Moroccan oranges", new BigDecimal("12.00"), categories.get(3)),
                        createArticle("Moroccan Coffee", "Spiced coffee with cardamom", new BigDecimal("10.00"), categories.get(3))
                ));

                // Desserts
                articlesByCategory.put("Desserts", Arrays.asList(
                        createArticle("Kaab el Ghazal", "Gazelle horns pastry with almond filling", new BigDecimal("15.00"), categories.get(5)),
                        createArticle("Briouates with Honey", "Sweet filo pastry with almonds", new BigDecimal("18.00"), categories.get(5)),
                        createArticle("Orange with Cinnamon", "Fresh orange slices with cinnamon", new BigDecimal("12.00"), categories.get(5))
                ));

                articlesByCategory.forEach((cat, articles) -> articleRepository.saveAll(articles));

                // Initialize Users
                List<User> users = UserDataGenerator.generateUsers(1000000);

                users.forEach(user -> {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    userRepository.save(user);
                });
            }
        };
    }

    private Category createCategory(String name, String description) {
        return Category.builder()
                .name(name)
                .description(description)
                .build();
    }

    private Article createArticle(String title, String description, BigDecimal price, Category category) {
        return Article.builder()
                .title(title)
                .description(description)
                .price(price)
                .category(category)
                .build();
    }

    private User createUser(String firstName, String lastName, String email, String password, Role role, BigDecimal initialBalance) {
        Card card = Card.builder()
                .balance(initialBalance)
                .lastUpdateDate(LocalDate.now())
                .build();

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .role(role)
                .build();

        card.setUser(user);
        user.setCard(card);

        return user;
    }
}