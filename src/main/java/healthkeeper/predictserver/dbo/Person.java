package main.java.healthkeeper.predictserver.dbo;

public class Person {
    private String  birth_date;
    private String  username;
    private String  password;
    private int     id;
    private boolean is_active;
    
    public Person(){
        
    }
    
    public Person(String birth_date, String username, String password, int id, boolean is_active) {
        super();
        this.birth_date = birth_date;
        this.username = username;
        this.password = password;
        this.id = id;
        this.is_active = is_active;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "Person [birth_date=" + birth_date + ", username=" + username + ", password=" + password + ", id=" + id
                + ", is_active=" + is_active + "]";
    }
}
