
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


public class Price {
    private long id; // идентификатор в БД
    private String productCode; // код товара
    private int number; // номер цены
    private int depart; // номер отдела
    private LocalDateTime begin; // начало действия
    private LocalDateTime end; // конец действия
    private long value; // значение цены в копейках


    //Конструктор со всеми полями
    public Price(long id, String productCode, int number,
                 int depart, LocalDateTime begin,
                 LocalDateTime end, long value) {
        this.id = id;
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
        this.begin = begin;
        this.end = end;
        this.value = value;
    }

    //Конструктор копирования
    public Price(Price p) {
        this.id = p.id;
        this.productCode = p.productCode;
        this.number = p.number;
        this.depart = p.depart;
        this.begin = p.begin;
        this.end = p.end;
        this.value = p.value;
    }

    //Конструктор для нарезки цены
    public Price(Price p, LocalDateTime begin,
                 LocalDateTime end){
        this.id = p.id;
        this.productCode = p.productCode;
        this.number = p.number;
        this.depart = p.depart;
        this.begin = begin;
        this.end = end;
        this.value = p.value;
    }

    public long getId() {
        return id;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getNumber() {
        return number;
    }

    public int getDepart() {
        return depart;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public void setBegin(LocalDateTime begin) {
        this.begin = begin;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public long getValue() {
        return value;
    }


    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", number=" + number +
                ", depart=" + depart +
                ", begin=" + begin +
                ", end=" + end +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        Price price = (Price) o;
        return getId() == price.getId() &&
                getNumber() == price.getNumber() &&
                getDepart() == price.getDepart() &&
                getValue() == price.getValue() &&
                Objects.equals(getProductCode(), price.getProductCode()) &&
                Objects.equals(getBegin(), price.getBegin()) &&
                Objects.equals(getEnd(), price.getEnd());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getProductCode(), getNumber(), getDepart(), getBegin(), getEnd(), getValue());
    }
}
