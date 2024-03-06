public class Handwritten extends Book{
    public Handwritten(int id) {
        super(id);
    }

    public String getInfo() {
        return String.format("Handwritten [id: %d]\n", this.getId());
    }
}
