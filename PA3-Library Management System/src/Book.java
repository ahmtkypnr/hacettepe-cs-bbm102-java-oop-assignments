import java.time.LocalDate;

public abstract class Book {
    static int count;
    boolean extended;
    boolean available;
    int id;
    LocalDate date;
    LocalDate deadline;
    Person person;

    public Book(int id) {
        this.id = id;
        this.available = true;
        count += 1;
    }

    public static int getCount() {
        return count;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isExtended() {
        return extended;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public LocalDate getDate() {
        return date;
    }

    public Person getPerson() {
        return person;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public abstract String getInfo();
}
