package DAO;

import database.ReferenceBook;
import program.City;
import service.CityService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReferenceBookDAO {

    static ReferenceBook referenceBook = new ReferenceBook();

    public static List<City> getCityCatalog() {

        return new ArrayList<>(referenceBook.cityCatalog);
    }

    public static void setCityCatalog(List<City> cityCatalog) {
        referenceBook.cityCatalog = cityCatalog;
    }

    public static void addCitiesToReferenceBook(City city) {
        referenceBook.cityCatalog.add(city);
    }

    public static void addCitiesToReferenceBook(String file) throws IOException {
        CityService.downloadCities(file);
    }
}
