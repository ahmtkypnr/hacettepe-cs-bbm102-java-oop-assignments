import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class Main {
    public static void main(String[] args) {
        String[] input = FileInput.readFile(args[0], true, true);
        String output = "";

        ArrayList<Person> people = new ArrayList<>();
        ArrayList<Book> books = new ArrayList<>();

        Book book;
        Person person;
        LocalDate now;
        int timeLimit;

        int bookId = 1;
        int personId = 1;
        for (String line : input) {
            String[] parts = line.split("\t");
            switch (parts[0]) {
                case "addBook":
                    switch (parts[1]) {
                        case "P":
                            books.add(new Printed(bookId));
                            output += String.format("Created new book: Printed [id: %d]\n", bookId);
                            bookId += 1;
                            break;
                        case "H":
                            books.add(new Handwritten(bookId));
                            output += String.format("Created new book: Handwritten [id: %d]\n", bookId);
                            bookId += 1;
                            break;
                        default:
                            break;
                    }
                    break;
                case "addMember":
                    switch (parts[1]) {
                        case "S":
                            people.add(new Student(personId));
                            output += String.format("Created new member: Student [id: %d]\n", personId);
                            personId += 1;
                            break;
                        case "A":
                            people.add(new Academic(personId));
                            output += String.format("Created new member: Academic [id: %d]\n", personId);
                            personId += 1;
                            break;
                        default:
                            break;
                    }
                    break;
                case "borrowBook":
                    book = Functions.findBook(books, Integer.parseInt(parts[1]));
                    if (!book.isAvailable()) {
                        output += "You cannot borrow this book!\n";
                        break;
                    }
                    person = Functions.findPerson(people, Integer.parseInt(parts[2]));
                    if (book instanceof Handwritten && person instanceof Student) {
                        output += "You cannot borrow this book!\n";
                        break;
                    }
                    if (person instanceof Student && person.getBooks().size() >= 2) {
                        output += "You have exceeded the borrowing limit!\n";
                        break;
                    }
                    if (person instanceof Academic && person.getBooks().size() >= 4) {
                        output += "You have exceeded the borrowing limit!\n";
                        break;
                    }
                    person.getBooks().add(book);
                    book.setPerson(person);
                    timeLimit = person instanceof Academic ? 14 : 7;
                    book.setDeadline(LocalDate.parse(parts[3]).plusDays(timeLimit));
                    book.setDate(LocalDate.parse(parts[3]));
                    book.setAvailable(false);
                    output += String.format("The book [%s] was borrowed by member [%s] at %s\n",
                            parts[1], parts[2], parts[3]);
                    break;
                case "returnBook":
                    book = Functions.findBook(books, Integer.parseInt(parts[1]));
                    person = Functions.findPerson(people, Integer.parseInt(parts[2]));
                    now = LocalDate.parse(parts[3]);
                    int days = book.getDeadline() == null ? 0 : (int) DAYS.between(book.getDeadline(), now);
                    int fee = Math.max(days, 0);

                    person.getBooks().remove(book);
                    book.setPerson(null);
                    book.setDeadline(null);
                    book.setDate(null);
                    book.setExtended(false);
                    book.setAvailable(true);
                    output += String.format("The book [%s] was returned by member [%s] at %s Fee: %d\n",
                            parts[1], parts[2], parts[3], fee);
                    break;
                case "extendBook":
                    book = Functions.findBook(books, Integer.parseInt(parts[1]));
                    if (book.isExtended()) {
                        output += "You cannot extend the deadline!\n";
                        break;
                    }
                    person = Functions.findPerson(people, Integer.parseInt(parts[2]));
                    timeLimit = person instanceof Academic ? 14 : 7;
                    book.setDeadline(book.getDeadline().plusDays(timeLimit));
                    book.setExtended(true);
                    output += String.format("The deadline of book [%s] was extended by member [%s] at %s" +
                            "\nNew deadline of book [%s] is %s\n", parts[1], parts[2], parts[3], parts[1],
                            book.getDeadline().toString());
                    break;
                case "readInLibrary":
                    book = Functions.findBook(books, Integer.parseInt(parts[1]));
                    if (!book.isAvailable()) {
                        output += "You can not read this book!\n";
                        break;
                    }
                    person = Functions.findPerson(people, Integer.parseInt(parts[2]));
                    if (book instanceof Handwritten && person instanceof Student) {
                        output += "Students can not read handwritten books!\n";
                        break;
                    }
                    person.getBooks().add(book);
                    book.setPerson(person);
                    book.setDate(LocalDate.parse(parts[3]));
                    book.setAvailable(false);
                    output += String.format("The book [%s] was read in library by member [%s] at %s\n",
                            parts[1], parts[2], parts[3]);
                    break;
                case "getTheHistory":
                    String students = "";
                    int studentCount = Student.getCount();
                    String academics = "";
                    int academicCount = Academic.getCount();
                    String printed = "";
                    int printedCount = Printed.getCount();
                    String handwritten = "";
                    int handwrittenCount = Handwritten.getCount();
                    for (Person person1 : people) {
                        if (person1 instanceof Student) {
                            students += person1.getInfo();
                        }
                        else if (person1 instanceof Academic) {
                            academics += person1.getInfo();
                        }
                    }
                    for (Book book1 : books) {
                        if (book1 instanceof Printed) {
                            printed += book1.getInfo();
                        }
                        else if (book1 instanceof Handwritten) {
                            handwritten += book1.getInfo();
                        }
                    }
                    String borrowed = "";
                    int borrowedCount = 0;
                    String read = "";
                    int readCount = 0;
                    for (Book book1 : books) {
                        if (book1.isAvailable()) {
                            continue;
                        }
                        if (book1.getDeadline() != null) {
                            borrowed += String.format("The book [%d] was borrowed by member [%d] at %s\n",
                                    book1.getId(), book1.getPerson().getId(), book1.getDate().toString());
                            borrowedCount += 1;
                        }
                        else {
                            read += String.format("The book [%d] was read in library by member [%d] at %s\n",
                                    book1.getId(), book1.getPerson().getId(), book1.getDate().toString());
                            readCount += 1;
                        }
                    }
                    output += String.format("History of library:\n\nNumber of students: %d\n%s" +
                            "\nNumber of academics: %d\n%s\nNumber of printed books: %d\n%s" +
                            "\nNumber of handwritten books: %d\n%s\nNumber of borrowed books: %d\n%s" +
                            "\nNumber of books read in library: %d\n%s", studentCount, students,
                            academicCount, academics, printedCount, printed, handwrittenCount, handwritten,
                            borrowedCount, borrowed, readCount, read);
                    break;
                default:
                    break;
            }
        }
        FileOutput.writeToFile(args[1], output.trim(), false, false);
    }
}
