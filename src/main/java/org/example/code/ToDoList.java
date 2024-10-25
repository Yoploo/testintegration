package org.example.code;

import java.util.Arrays;

public class ToDoList {
    public Item[] items;

    public ToDoList() {
        this.items = new Item[0];
    }

    public void addItem(Item item) {
        if (item != null) { // Assure que l'item n'est pas nul
            // Crée un nouveau tableau avec une place supplémentaire
            Item[] newItems = Arrays.copyOf(items, items.length + 1);
            // Ajoute l'item à la fin du tableau
            newItems[items.length] = item;
            // Remplace l'ancien tableau par le nouveau
            items = newItems;
        }
    }
}
