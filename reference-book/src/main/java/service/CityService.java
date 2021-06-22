package service;

import DAO.ReferenceBookDAO;
import program.City;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CityService {

    public static void downloadCities(String fileName) throws IOException {

        Scanner scanner = new Scanner(Objects.requireNonNull(CityService.class.getClassLoader().getResourceAsStream("cities.txt")));

        scanner.useDelimiter(System.getProperty("line.separator"));
        while (scanner.hasNextLine()) { // как ститать так если в конце много пробелов и переносов строк без текста

            String[] cityData = scanner.next().split(";"); // как обрезать считываемую строку от пробелов вначале и вконце
            ReferenceBookDAO.addCitiesToReferenceBook(new City(cityData[1], cityData[2], cityData[3],
                    Integer.parseInt(cityData[4]), Integer.parseInt(cityData[5])));
        }
        ReferenceBookDAO.setCityCatalog(standardCitySorter(ReferenceBookDAO.getCityCatalog()));
    }

    public static List<City> standardCitySorter(List<City> cityList) {

        cityList.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        return cityList;
    }

    public static List<City> citySorterByName(List<City> cityList) {

        cityList.sort((o1, o2) -> o2.getName().compareToIgnoreCase(o1.getName()));
        return cityList;
    }

    public  static List<City> cityDoubleSorter(List<City> cityList) {

        return cityList.stream().sorted(Comparator.comparing(City::getDistrict)
                .thenComparing(City::getName).reversed()).collect(Collectors.toList());
    }

    public static int[] cityWithMaxPopulation() {

        int[] maxPopulation = {0,0};

        City[] cities = new City[ReferenceBookDAO.getCityCatalog().size()];
        cities = ReferenceBookDAO.getCityCatalog().toArray(cities);

        for (int i = 0; i < cities.length; i++) {
            if (cities[i].getPopulation() > maxPopulation[1]) {
                maxPopulation[0] = i;
                maxPopulation[1] = cities[i].getPopulation();
            }
        }
        return maxPopulation;
    }

    public static Map<String, Integer> numberOfCitiesInRegion() {

        Map<String, Integer> map = new HashMap<>();

        for (City city : ReferenceBookDAO.getCityCatalog()) {
            if (map.containsKey(city.getRegion())){
                map.put(city.getRegion(), map.get(city.getRegion()) + 1);
            } else {
                map.put(city.getRegion(), 1);
            }
        }
        return map;
    }
}
