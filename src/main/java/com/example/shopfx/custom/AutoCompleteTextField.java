package com.example.shopfx.custom;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is a TextField which implements an "autocomplete" functionality, based on a supplied list of entries.
 */
public class AutoCompleteTextField extends TextField {
    /**
     * The existing autocomplete entries.
     */
    private final SortedSet<String> entries;
    /**
     * The popup used to select an entry.
     */
    private ContextMenu entriesPopup;

    /**
     * Construct a new AutoCompleteTextField.
     */
    public AutoCompleteTextField() {
        super();
        entries = new TreeSet<>();
        entriesPopup = new ContextMenu();
        textProperty().addListener((observableValue, s, s2) -> {
            if (getText().length() == 0) {
                entriesPopup.hide();
            } else {
                //                LinkedList<String> searchResult = new LinkedList<>(entries.subSet(getText(), getText() + Character.MAX_VALUE));
                LinkedList<String> searchResult = entries.stream().filter(e -> e.contains(getText())).distinct().collect(Collectors.toCollection(LinkedList::new));
                if (!entries.isEmpty()) {
                    populatePopup(searchResult);
                    if (!entriesPopup.isShowing()) {
                        entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    entriesPopup.hide();
                }
            }
        });

        focusedProperty().addListener((observableValue, aBoolean, aBoolean2) -> entriesPopup.hide());

    }

    /**
     * Get the existing set of autocomplete entries.
     *
     * @return The existing autocomplete entries.
     */
    public SortedSet<String> getEntries() {
        return entries;
    }

    /**
     * Populate the entry set with the given search results.
     * Display is limited to 10 entries, for performance.
     *
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        // If you'd like more entries, modify this line.
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(actionEvent -> {
                setText(result);
                entriesPopup.hide();
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }
}
