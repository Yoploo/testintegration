package org.example.test;

import org.example.code.User;
import org.example.code.ToDoList;
import org.example.code.Item;
import org.example.code.MailSender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserTest {
    private MailSender mailSender;
    private User user;

    @BeforeEach
    public void setUp() {
        mailSender = mock(MailSender.class);
        when(mailSender.sendMail(any(User.class), any(String.class), any(String.class))).thenReturn(1);
        user = new User("John", "Doe", "eeaeae@gmail.com", "Aa1aaaaa", LocalDate.now().minusYears(20).toString(), mailSender);
        user.todolist = new ToDoList();  // Initialiser ToDoList

        // Vérification de l'initialisation par impression
        System.out.println("Initialisation de l'utilisateur:");
        System.out.println("user: " + user);
        System.out.println("user.first_name: " + user.first_name);
        System.out.println("user.last_name: " + user.last_name);
        System.out.println("user.password: " + user.password);
        System.out.println("user.birthdate: " + user.birthdate);
        System.out.println("user.todolist: " + user.todolist);
        System.out.println("user.todolist.items: " + (user.todolist.items != null ? "initialisé" : "non initialisé"));
    }


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

    @Test
    public void testAddItem() {
        Item item = new Item("name", "description", null);
        user.add(item);
        assertEquals(1, user.todolist.items.length);
    }

    @Test
    public void testAdd2Items() {
        Item item = new Item("name", "description", null);
        Item item2 = new Item("name2", "description2", null);
        user.add(item);
        user.add(item2);
        assertEquals(1, user.todolist.items.length);
    }

    @Test
    public void testAddItemWithMoreThan1000Characters() {
        String description = "a".repeat(1001);
        Item item = new Item("name", description, null);
        user.add(item);
        assertEquals(0, user.todolist.items.length);
    }

    @Test
    public void testAddItemWith7Items() {
        ToDoList todolist = new ToDoList();
        for (int i = 0; i < 7; i++) {
            Item item = new Item("name" + i, "description" + i, LocalDateTime.now().minusYears(10 - i));
            todolist.addItem(item);
        }
        user.todolist = todolist;
        Item item = new Item("name", "description", null);
        user.add(item);
        assertEquals(8, user.todolist.items.length);
    }
}
