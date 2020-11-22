import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product", schema = "public")
public class Product {
    @Id
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    @ManyToMany(mappedBy = "producten")
    private List<OVChipkaart> ovChipkaarten = new ArrayList<>();

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product() {

    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public void addToOVKaarten(OVChipkaart ovChipkaart) {
        ovChipkaarten.add(ovChipkaart);
    }

    public void removeFromOVKaarten(OVChipkaart ovChipkaart) {
        ovChipkaarten.remove(ovChipkaart);
    }

    public List<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(ArrayList<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    @Override
    public String toString() {
        return "Product {" +
                "productnummer: " + product_nummer +
                ", naam: '" + naam + '\'' +
                ", beschrijving: '" + beschrijving + '\'' +
                ", prijs: " + prijs +
                '}';
    }
}
