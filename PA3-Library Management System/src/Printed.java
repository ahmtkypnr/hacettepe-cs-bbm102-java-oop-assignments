public class Printed extends Book{
    public Printed(int id) {
        super(id);
    }

    public String getInfo() {
        return String.format("Printed [id: %d]\n", this.getId());
    }
}
