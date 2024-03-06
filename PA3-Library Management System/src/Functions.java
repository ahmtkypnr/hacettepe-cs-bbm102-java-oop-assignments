import java.util.ArrayList;

public class Functions {
    public static Book findBook (ArrayList<Book> arrayList, int id) {
        for (Book book : arrayList) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public static Person findPerson (ArrayList<Person> arrayList, int id) {
        for (Person person : arrayList) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }
}
