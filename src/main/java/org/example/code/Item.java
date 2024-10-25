package org.example.code;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Item {
    public String name;
    public String description;
    public LocalDateTime dueDate;

    public Item(String name, String description, LocalDateTime dueDate) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }


}
