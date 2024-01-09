package model;

//modele du voiture avec ses champs, constructeur, getter et setter
public class Car {

    private String name;
    private Integer price;
    private Integer nb_places;
    private Integer state;

    private String image;

    public Car(String name, Integer price, Integer nb_places, Integer state,String image) {
        this.name = name;
        this.price = price;
        this.nb_places = nb_places;
        this.state = state;
        this.image=image;
    }



    public Car() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Number getPrice() {
        return price;
    }

    public Number getNb_places() {
        return nb_places;
    }

    public Number getState() {
        return state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setNb_places(Integer nb_places) {
        this.nb_places = nb_places;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
