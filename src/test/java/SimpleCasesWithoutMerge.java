import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Comparator;

public class SimpleCasesWithoutMerge {//Простые случаи, когда нет перекрытия

    //Пустые списки
     @Test
    void EmptyLists() {
        ArrayList<Price> oldPrice = new ArrayList <Price>();
        ArrayList<Price> newPrice = new ArrayList <Price>();
        ArrayList<Price> resultPrice = new ArrayList <Price>();
        assertEquals(JoinPriceMeth.joinPrice(oldPrice, newPrice), resultPrice);
    }


    //Код продукта - один. Номер отдела - один. В старых ценах - первая и вторая цены
    //В новых ценах - третья и четвёртая.
    //Время начала и окончания - одинаково
    @Test
    void Not_Overlap_if_different_Number_price() {
        ArrayList<Price> oldPrice = new ArrayList <Price>();
        ArrayList<Price> newPrice = new ArrayList <Price>();
        ArrayList<Price> resultPrice = new ArrayList <Price>();
        oldPrice.add(new Price(1, "1", 1, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                11000));
        oldPrice.add(new Price(1, "1", 2, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                13000));
        newPrice.add(new Price(1, "1", 3,1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                15000));
        newPrice.add(new Price(1, "1", 4, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                17000));
        resultPrice.add(new Price(1, "1", 1, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                11000));
        resultPrice.add(new Price(1, "1", 2, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                13000));
        resultPrice.add(new Price(1, "1", 3,1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                15000));
        resultPrice.add(new Price(1, "1", 4, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                17000));
        assertEquals(JoinPriceMeth.joinPrice(oldPrice, newPrice), resultPrice);
    }

    //Код продукта - один. Номер отдела - разный. Номер цены - один
    //В новых ценах - третья и четвёртая.
    //Время начала и окончания - одинаково
    @Test
    void Not_Overlap_if_different_department() {
        ArrayList<Price> oldPrice = new ArrayList<Price>();
        ArrayList<Price> newPrice = new ArrayList<Price>();
        ArrayList<Price> resultPrice = new ArrayList<Price>();
        oldPrice.add(new Price(1, "1", 1, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                11000));
        oldPrice.add(new Price(1, "1", 1, 2,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                13000));
        newPrice.add(new Price(1, "1", 1, 3,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                15000));
        newPrice.add(new Price(1, "1", 1, 4,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                17000));
        resultPrice.add(new Price(1, "1", 1, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                11000));
        resultPrice.add(new Price(1, "1", 1, 2,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                13000));
        resultPrice.add(new Price(1, "1", 1, 3,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                15000));
        resultPrice.add(new Price(1, "1", 1, 4,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T23:59:59"),
                17000));
        assertEquals(JoinPriceMeth.joinPrice(oldPrice, newPrice), resultPrice);
    }

        //Код продукта - один. Номер отдела - один. Номер цены - один
        //Время начала и окончания - отличаются
        @Test
        void Not_Overlap_if_different_Time() {
            ArrayList<Price> oldPrice = new ArrayList <Price>();
            ArrayList<Price> newPrice = new ArrayList <Price>();
            ArrayList<Price> resultPrice = new ArrayList <Price>();
            oldPrice.add(new Price(1, "1", 1, 1,
                    LocalDateTime.parse("2013-01-01T00:00:00"),
                    LocalDateTime.parse("2013-01-15T23:59:59"),
                    11000));
            oldPrice.add(new Price(1, "1", 1, 1,
                    LocalDateTime.parse("2013-01-16T00:00:00"),
                    LocalDateTime.parse("2013-02-01T23:59:59"),
                    13000));
            newPrice.add(new Price(1, "1", 1,1,
                    LocalDateTime.parse("2013-02-02T00:00:00"),
                    LocalDateTime.parse("2013-02-15T23:59:59"),
                    15000));
            newPrice.add(new Price(1, "1", 1, 1,
                    LocalDateTime.parse("2013-02-16T00:00:00"),
                    LocalDateTime.parse("2013-03-01T23:59:59"),
                    17000));
            resultPrice.add(new Price(1, "1", 1, 1,
                    LocalDateTime.parse("2013-01-01T00:00:00"),
                    LocalDateTime.parse("2013-01-15T23:59:59"),
                    11000));
            resultPrice.add(new Price(1, "1", 1, 1,
                    LocalDateTime.parse("2013-01-16T00:00:00"),
                    LocalDateTime.parse("2013-02-01T23:59:59"),
                    13000));
            resultPrice.add(new Price(1, "1", 1,1,
                    LocalDateTime.parse("2013-02-02T00:00:00"),
                    LocalDateTime.parse("2013-02-15T23:59:59"),
                    15000));
            resultPrice.add(new Price(1, "1", 1, 1,
                    LocalDateTime.parse("2013-02-16T00:00:00"),
                    LocalDateTime.parse("2013-03-01T23:59:59"),
                    17000));
            assertEquals(JoinPriceMeth.joinPrice(oldPrice, newPrice), resultPrice);
    }

    //Код продукта - отличается. Номер отдела - один. Номер цены - один
    //Времена начала и окончания - одни.
    @Test
    void Not_Overlap_if_different_Product_Code() {
        ArrayList<Price> oldPrice = new ArrayList <Price>();
        ArrayList<Price> newPrice = new ArrayList <Price>();
        ArrayList<Price> resultPrice = new ArrayList <Price>();
        oldPrice.add(new Price(1, "1", 1, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-15T23:59:59"),
                11000));
        oldPrice.add(new Price(1, "1", 1, 1,
                LocalDateTime.parse("2013-01-16T00:00:00"),
                LocalDateTime.parse("2013-02-01T23:59:59"),
                13000));
        newPrice.add(new Price(1, "2", 1,1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-15T23:59:59"),
                15000));
        newPrice.add(new Price(1, "2", 1, 1,
                LocalDateTime.parse("2013-01-16T00:00:00"),
                LocalDateTime.parse("2013-02-01T23:59:59"),
                17000));
        resultPrice.add(new Price(1, "1", 1, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-15T23:59:59"),
                11000));
        resultPrice.add(new Price(1, "1", 1, 1,
                LocalDateTime.parse("2013-01-16T00:00:00"),
                LocalDateTime.parse("2013-02-01T23:59:59"),
                13000));
        resultPrice.add(new Price(1, "2", 1,1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-15T23:59:59"),
                15000));
        resultPrice.add(new Price(1, "2", 1, 1,
                LocalDateTime.parse("2013-01-16T00:00:00"),
                LocalDateTime.parse("2013-02-01T23:59:59"),
                17000));
        assertEquals(JoinPriceMeth.joinPrice(oldPrice, newPrice), resultPrice);
    }
}
