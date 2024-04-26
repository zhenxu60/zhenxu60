package com.lendingcatalog.model;

import com.lendingcatalog.util.FileStorageService;
import com.lendingcatalog.util.exception.FileStorageException;

import java.util.Date;
import java.util.UUID;

public class Tool implements CatalogItem{

    private String id;
    private String type;
    private String manufacturer;
    private int count;

    public Tool(String type, String manufacturer, int count) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.count = count;
    }

    @Override
    public String toString(){
        return "* " + type + System.lineSeparator()
                + " - Made by: " + manufacturer + System.lineSeparator()
                + " - Sale: " + count + System.lineSeparator()
                + " - Id: " + id;
    }

    @Override
    public boolean matchesName(String searchStr) {
        return type.toLowerCase().equals(searchStr.toLowerCase());
    }

    @Override
    public boolean matchesCreator(String searchStr) {
        return manufacturer.toLowerCase().equals(searchStr.toLowerCase());
    }

    @Override
    public boolean matchesYear(int searchYear) {
        return false;
    }

    @Override
    public void registerItem() {
        this.id = UUID.randomUUID().toString();
        Date data = new Date();
        String contents = data.toString() + "\n" + toString();
        String fileName = "src/main/resources/logs/tools.log";
        try {
            FileStorageService.writeContentsToFile(contents, fileName, true);
        } catch (FileStorageException e) {
            throw new RuntimeException(e);
        }

    }
}
