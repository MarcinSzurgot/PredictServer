package main.java.healthkeeper.predictserver.dbo;

public class Accident {
    public static final Accident DEFAULT_ACCIDENT = new Accident(1, "DEFAULT");
    
    private int id;
    private String name;
    
    public Accident(){
        
    }
    
    public Accident(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Accident [id=" + id + ", name=" + name + "]";
    }
    
    
    
    
}
