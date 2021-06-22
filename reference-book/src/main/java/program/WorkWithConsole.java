package program;

import DAO.ReferenceBookDAO;
import service.CityService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WorkWithConsole {

    private Scanner scanner ;

    public void startProgram() throws IOException {

        scanner = new Scanner(System.in);

        System.out.println("\nУкажите с каким справочником хотите работать:" +
                "\n1. Загрузить стандартный." +
                "\n2. Загрузить свой вариант." +
                "\nДля выхода из программы введите \"0\".\n");

        endProgram:
        {
            switch (userInput()) {
                case (1):
                    referenceBookFromFile("Cities");
                    break;
                case (2):
                    referenceBookFromFile(scanner.nextLine());
                    break;
                case (0):
                    break endProgram;
            }

            while (true) {

                System.out.println("\nВы обратились к справочнику городов, какие действия вы хотите выполнить?" +
                        "\n1. Вывести список городов." +
                        "\n2. Отсортировать список городов по названию." +
                        "\n3. Отсортировать список городов по федеральному округу и названию." +
                        "\n4. Найти город с наибольшим числом жителей." +
                        "\n5. Количество городов в каждом регионе." +
                        "\n6. Добавить города в справочник." +
                        "\nДля выхода из программы введите \"0\".\n");

                switch (userInput()) {
                    case (1):
                        selectionSorting(1);
                        break;
                    case (2):
                        selectionSorting(2);
                        break;
                    case (3):
                        selectionSorting(3);
                        break;
                    case (4):

                        int[] output = CityService.cityWithMaxPopulation();
                        System.out.printf("[%d] = %d", output[0], output[1]);
                        break;
                    case (5):
                        Map<String, Integer> resultMap = CityService.numberOfCitiesInRegion();
                        for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
                            System.out.println(entry.getKey() + " - " + entry.getValue());
                        }
                        break;
                    case (6):
                        referenceBookFromFile(scanner.nextLine());
                        break;
                    case (0):
                        break endProgram;
                }
            }
        }

        scanner.close();
    }

    public void referenceBookFromFile(String fileName) throws IOException {

        ReferenceBookDAO.addCitiesToReferenceBook(String.format("/Users/u19223188/Documents/Task Files/%s.txt", fileName));
    }

    public void selectionSorting(int number){

        List<City> cities;
        if (number == 1) {
            cities = ReferenceBookDAO.getCityCatalog();
        } else if (number == 2) {
            cities = CityService.citySorterByName(ReferenceBookDAO.getCityCatalog());
        } else {
            cities = CityService.cityDoubleSorter(ReferenceBookDAO.getCityCatalog());
        }
        cities.forEach(System.out::println);
    }

    public int userInput() {

        int selectionResult;

        while (true){
            try {
                selectionResult = Integer.parseInt(scanner.nextLine());
                break;
            } catch (RuntimeException e){
                System.out.println("Неверный формат, повторите ввод!");
            }
        }
        return selectionResult;
    }
}
