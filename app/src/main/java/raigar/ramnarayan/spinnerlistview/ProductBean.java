package raigar.ramnarayan.spinnerlistview;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kriscent on 7/5/18.
 */

public class ProductBean implements Serializable {
    private ArrayList<String> weight;
    private ArrayList<String> price;

    public ArrayList<String> getWeight() {
        return weight;
    }

    public void setWeight(ArrayList<String> weight) {
        this.weight = weight;
    }

    public ArrayList<String> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<String> price) {
        this.price = price;
    }
}
