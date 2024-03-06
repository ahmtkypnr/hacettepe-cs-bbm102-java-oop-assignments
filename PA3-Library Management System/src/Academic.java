public class Academic extends Person{
    public Academic(int id) {
        super(id);
    }

    public String getInfo() {
        return String.format("Academic [id: %d]\n", this.getId());
    }
}
