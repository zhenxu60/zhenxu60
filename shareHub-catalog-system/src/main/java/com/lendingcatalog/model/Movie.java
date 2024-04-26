package com.lendingcatalog.model;

import com.lendingcatalog.util.FileStorageService;
import com.lendingcatalog.util.exception.FileStorageException;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Movie implements CatalogItem{

    private String id;
    private String name;
    private String director;

    private LocalDate releaseDate;


    public Movie(String name, String director, LocalDate releaseDate) {
        this.name = name;
        this.director = director;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString(){
        return "* " + name + System.lineSeparator()
                + " - Directed by: " + director + System.lineSeparator()
                + " - Came out: " + releaseDate + System.lineSeparator()
                + " - Id: " + id;
    }

    @Override
    public boolean matchesName(String searchStr) {
        return name.toLowerCase().equals(searchStr.toLowerCase());
    }

    @Override
    public boolean matchesCreator(String searchStr) {
        return director.toLowerCase().equals(searchStr.toLowerCase());
    }

    @Override
    public boolean matchesYear(int searchYear) {
        return releaseDate.getYear() == searchYear;

    }

    @Override
    public void registerItem() {
        this.id = UUID.randomUUID().toString();
        Date data = new Date();
        String contents = data.toString() + "\n" + toString();
        String fileName = "src/main/resources/logs/movies.log";
        try {
            FileStorageService.writeContentsToFile(contents, fileName, true);
        } catch (FileStorageException e) {
            throw new RuntimeException(e);
        }
    }
}
