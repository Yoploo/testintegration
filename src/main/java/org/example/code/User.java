package org.example.code;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class User {
    public String first_name;
    public String last_name;
    public String email;
    public String password;
    public String birthdate;
    public ToDoList todolist;
    public MailSender mailSender;

    public User(String first_name, String last_name, String email, String password, String birthdate, MailSender mailSender) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.birthdate = birthdate;
        this.todolist = new ToDoList();
        this.password = password;
        this.mailSender = mailSender;
    }

    public boolean isValid() {
        //valid password from 8 to 40 char and 1 min 1 maj 1 number
        if (password != null) {
            String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,40}$";
            if (!password.matches(passwordRegex)) {
                return false;
            }
        }

        int age = 0;
        if (birthdate != null) {
            String[] dateParts = birthdate.split("-");
            if (dateParts.length == 3) {
                int year = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int day = Integer.parseInt(dateParts[2]);
                java.time.LocalDate localDate = java.time.LocalDate.now();
                int currentYear = localDate.getYear();
                age = currentYear - year;
                if (month > localDate.getMonthValue() || (month == localDate.getMonthValue() && day > localDate.getDayOfMonth())) {
                    age--;
                }
            }
        }
        if (email != null) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            if (!email.matches(emailRegex)) {
                return false;
            }
        }
        return (first_name != null && !first_name.isBlank()) && (last_name != null && !last_name.isBlank()) && password != null && email != null && age >= 13;
    }

    public int add(Item item) {
        // check User valid
        if (!isValid()) {
            return 0;
        }
        // verif limite 10 items
        if (todolist.items.length >= 10) {
            return 0;
        }
        //verif unique name
        for (Item i : todolist.items) {
            if (i.name.equals(item.name)) {
                return 0;
            }
        }
        // verif limite char
        if (item.description.length() > 1000) {
            return 0;
        }
        //verif last item add > 30 min
        if (todolist.items.length > 0) {
            LocalDateTime lastdate = todolist.items[todolist.items.length - 1].dueDate;
            LocalDateTime currentdate = LocalDateTime.now();
            if (lastdate.plusMinutes(30).isAfter(currentdate)) {
                return 0;
            }
        }

        // add item
        LocalDateTime date = LocalDateTime.now();
        Item newitem = new Item(item.name, item.description, date);
        Item[] newitems = new Item[todolist.items.length + 1];
        for (int i = 0; i < todolist.items.length; i++) {
            newitems[i] = todolist.items[i];
        }
        newitems[todolist.items.length] = newitem;
        todolist.items = newitems;
        //mail if 8 items
        if (todolist.items.length == 8 && mailSender != null) {
            mailSender.sendMail(this, "ToDoList", "You have 8 items in your ToDoList");
        }

        return 1;
    }

    public int save() {
        throw new IllegalArgumentException("Not implemented yet");
    }
}
