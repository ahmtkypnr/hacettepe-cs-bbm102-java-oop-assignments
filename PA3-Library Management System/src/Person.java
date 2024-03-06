import java.util.ArrayList;

public abstract class Person {
    static int count;
    int id;
    ArrayList<Book> books = new ArrayList<>();
    public Person(int id) {
        this.id = id;
        count += 1;
    }

    public static int getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String getInfo();

}
