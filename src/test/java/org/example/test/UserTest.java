package org.example.test;

import org.example.code.User;
import org.example.code.ToDoList;
import org.example.code.Item;
import org.example.code.MailSender;

//import maven dependencies
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserTest {
    //test User
    private MailSender mailSender;
    private User user;

    //Before use good user
    @BeforeEach
    public void setUp() {
        mailSender = mock(MailSender.class);

        // Définir le comportement du mock
        when(mailSender.sendMail(any(User.class), any(String.class), any(String.class))).thenReturn(1);

        // Créer un utilisateur avec le MailSender mocké
        user = new User("John", "Doe", "eeaeae@gmail.com", "Aa1aaaaa", LocalDate.now().minusYears(20).toString(), mailSender);

        // Initialiser la ToDoList pour l'utilisateur
        user.todolist = new ToDoList();
    }

    //test isValid
    @Test
    public void testIsValidValid() {
        assertTrue(user.isValid());
    }

    @Test
    public void testIsValidInvalidPassword() {
        user.password = "aa";
        assertFalse(user.isValid());
    }

    @Test
    public void testIsValidInvalidBirthdate() {
        user.birthdate = LocalDate.now().minusYears(10).toString();
        assertFalse(user.isValid());
    }

    @Test
    public void testIsValidInvalidFirstName() {
        user.first_name = "";
        assertFalse(user.isValid());
    }

    @Test
    public void testIsValidInvalidLastName() {
        user.last_name = "";
        assertFalse(user.isValid());
    }

    //test add item
    @Test
    public void testAddItem() {
        Item item = new Item("name", "description", null);
        user.add(item);
        assertEquals(1, user.todolist.items.length);
    }

    //test add 2 items
    @Test
    public void testAdd2Items() {
        Item item = new Item("name", "description", null);
        Item item2 = new Item("name2", "description2", null);
        user.add(item);
        user.add(item2);
        assertEquals(2git , user.todolist.items.length);
    }

    //test items with more than 1000 characters
    @Test
    public void testAddItemWithMoreThan1000Characters() {
        String description = "a".repeat(1001);
        Item item = new Item("name", description, null);
        user.add(item);
        assertEquals(0, user.todolist.items.length);
    }

    //Test add item with already 7 items in the list
    @Test
    public void testAddItemWith7Items() {
        Item[] items = new Item[7];
        for (int i = 0; i < 7; i++) {
            items[i] = new Item("name" + i, "description" + i, LocalDateTime.now().minusYears(10 - i));
        }
        ToDoList todolist = new ToDoList();
        todolist.items = items;
        user.todolist = todolist;
        Item item = new Item("name", "description", null);
        user.add(item);
        assertEquals(8, user.todolist.items.length);
    }
}
