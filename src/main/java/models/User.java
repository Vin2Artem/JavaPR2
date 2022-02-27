package models;

public class User {
    public static final int DEFAULTID = -1;

    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private boolean sex;
    private String birth;
    private String email;
    private String password;
    private String phone;
    private String city;
    private String street;

    public String toString() {
        return this.id + " | " +
                this.surname + " | " +
                this.name + " | " +
                this.patronymic + " | " +
                this.sex + " | " +
                this.birth + " | " +
                this.email + " | " +
                this.password + " | " +
                this.phone + " | " +
                this.city + " | " +
                this.street;
    }

    public User (int id, String surname, String name, String patronymic, boolean sex, String birth, String email, String password, String phone, String city, String street) {
        this.setId(id);
        this.setSurname(surname);
        this.setName(name);
        this.setPatronymic(patronymic);
        this.setSex(sex);
        this.setBirth(birth);
        this.setEmail(email);
        this.setPassword(password);
        this.setPhone(phone);
        this.setCity(city);
        this.setStreet(street);
    }

    public User (int id, String surname, String name, String patronymic, String sex, String birth, String email, String password, String phone, String city, String street) throws RuntimeException {
        switch (sex) {
            case "мужской":
                this.setSex(true);
                break;
            case "женский":
                this.setSex(false);
                break;
            default:
                throw new RuntimeException("Unknown sex");
        }
        this.setId(id);
        this.setSurname(surname);
        this.setName(name);
        this.setPatronymic(patronymic);
        this.setBirth(birth);
        this.setEmail(email);
        this.setPassword(password);
        this.setPhone(phone);
        this.setCity(city);
        this.setStreet(street);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
