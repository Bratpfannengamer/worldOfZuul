package de.szut.zuul;

public class Item {
    String name;
    String description;
    double weight;
    public Item(String name, String description,double weight){
        this.name=name;
        this.description=description;
        this.weight=weight;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ", " +description + ", weight=" + weight;
    }
}
