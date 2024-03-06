public class Student extends Person{
    public Student(int id) {
        super(id);
    }

    public String getInfo() {
        return String.format("Student [id: %d]\n", this.getId());
    }
}
