import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Comparator;

//Класс - с наличием сливания цен. Это когда result.size()<=oldPrice.size()+newPrice.size();
//В данном классе тестируются 4 случая из задания и
class JoinWithMerge {

    //Метод слива цен в соответствии с заданием.
    //
    @Test
    void joinPriceLike_CSI_task_1() {
      ArrayList<Price> oldPrice = new ArrayList <Price>();
      ArrayList<Price> newPrice = new ArrayList <Price>();
      ArrayList<Price> resultPrice = new ArrayList <Price>();
     oldPrice.add(new Price(122, "122856", 1, 1,
             LocalDateTime.parse("2013-01-01T00:00:00"),
             LocalDateTime.parse("2013-01-31T23:59:59"),
             11000));
     oldPrice.add(new Price(122, "122856", 2, 1,
                   LocalDateTime.parse("2013-01-10T00:00:00"), 
                   LocalDateTime.parse("2013-01-20T23:59:59"),
                99000));
     oldPrice.add(new Price(66, "6654", 1, 2,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-31T00:00:00"),
                5000));
     newPrice.add(new Price(122, "122856", 1,1,
                LocalDateTime.parse("2013-01-20T00:00:00"),
                LocalDateTime.parse("2013-02-20T23:59:59"),
                11000));
     newPrice.add(new Price(122, "122856", 2, 1,
                LocalDateTime.parse("2013-01-15T00:00:00"),
                LocalDateTime.parse("2013-01-25T23:59:59"),
                92000));
     newPrice.add(new Price(66, "6654", 1, 2,
                LocalDateTime.parse("2013-01-12T00:00:00"),
                LocalDateTime.parse("2013-01-13T00:00:00"),
                4000));
        resultPrice.add(new Price(122, "122856", 1,1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-02-20T23:59:59"),
                11000));
        resultPrice.add(new Price(122, "122856", 2, 1,
                LocalDateTime.parse("2013-01-10T00:00:00"),
                LocalDateTime.parse("2013-01-15T00:00:00"),
                99000));
        resultPrice.add(new Price(122, "122856", 2, 1,
                LocalDateTime.parse("2013-01-15T00:00:00"),
                LocalDateTime.parse("2013-01-25T23:59:59"),
                92000));
        resultPrice.add(new Price(66, "6654", 1, 2,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-12T00:00:00"),
                5000));
        resultPrice.add(new Price(66, "6654", 1, 2,
                LocalDateTime.parse("2013-01-12T00:00:00"),
                LocalDateTime.parse("2013-01-13T00:00:00"),
                4000));
        resultPrice.add(new Price(66, "6654", 1, 2,
                LocalDateTime.parse("2013-01-13T00:00:00"),
                LocalDateTime.parse("2013-01-31T00:00:00"),
                5000));
        assertEquals(JoinPriceMeth.joinPrice(oldPrice, newPrice), resultPrice);
    }

    @Test
    //Даём в старом списке 3 цены, причем средняя отличается от крайних. То есть две одинаковы - одна отличается
    //Во втором  списке даём одну цену, чтобы полностью перекрыть среднюю.
    //На выходе хотим получить одну цену - чтобы две оставшиеся слились в едином порыве
    void FromThreeToOnePrice(){
        ArrayList<Price> oldPrice = new ArrayList <Price>();
        ArrayList<Price> newPrice = new ArrayList <Price>();
        ArrayList<Price> resultPrice = new ArrayList <Price>();

        oldPrice.add(new Price(500, "500", 1,1,
                LocalDateTime.parse("2021-01-01T00:00:00"),
                LocalDateTime.parse("2021-01-15T00:00:00"),
                10000));
        //ЭТА ЦЕНА ДОЛЖНА БЫТЬ ПЕРЕКРЫТА - МАГАЗИН ПЕРЕДУМАЛ ВВОДИТЬ СКИДКУ
        oldPrice.add(new Price(500, "500", 1,1,
                LocalDateTime.parse("2021-01-15T00:00:00"),
                LocalDateTime.parse("2021-01-25T00:00:00"),
                5000));
        oldPrice.add(new Price(500, "500", 1,1,
                LocalDateTime.parse("2021-01-25T00:00:00"),
                LocalDateTime.parse("2021-02-15T00:00:00"),
                10000));

        newPrice.add(new Price(500, "500", 1,1,
                LocalDateTime.parse("2021-01-15T00:00:00"),
                LocalDateTime.parse("2021-01-25T00:00:00"),
                10000));

        resultPrice.add(new Price(500, "500", 1,1,
                LocalDateTime.parse("2021-01-01T00:00:00"),
                LocalDateTime.parse("2021-02-15T00:00:00"),
                10000));
        assertEquals(JoinPriceMeth.joinPrice(oldPrice, newPrice), resultPrice);
    }

    //как в примере №1 задания
    //Даём одну старую и длинную цену. (50 рублей с 1 января по 1 марта)
    //по середине даём новую и короткую цену (60 рублей, с 1 по 15 февраля)
    //На выходе у нас три цены. 50 рублей - с 1 января по 1 февраля, 60 рублей, с 1 по 15 февраля,
    // 50 рулей с 16 февраля по 1 марта
    @Test
    public void two_divided_into_three(){
        ArrayList<Price> oldPrice = new ArrayList <Price>();
        ArrayList<Price> newPrice = new ArrayList <Price>();
        ArrayList<Price> resultPrice = new ArrayList <Price>();
        oldPrice.add(new Price(100, "100", 1, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-03-01T00:00:00"),
                5000));

        newPrice.add(new Price(100, "100", 1,1,
                LocalDateTime.parse("2013-02-01T00:00:00"),
                LocalDateTime.parse("2013-02-15T23:59:59"),
                6000));
        resultPrice.add(new Price(100, "100", 1, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-02-01T00:00:00"),
                5000));
        resultPrice.add(new Price(100, "100", 1, 1,
                LocalDateTime.parse("2013-02-01T00:00:00"),
                LocalDateTime.parse("2013-02-15T23:59:59"),
                6000));
        resultPrice.add(new Price(100, "100", 1, 1,
                LocalDateTime.parse("2013-02-15T23:59:59"),
                LocalDateTime.parse("2013-03-01T00:00:00"),
                5000));
        assertEquals(JoinPriceMeth.joinPrice(oldPrice, newPrice), resultPrice);
    }

    //как в примере №2 задания
    //Даём две старые и длинные цены:
    // 100 рублей с 1 января по 1 февраля
    //120 рублей - с 1 февраля по 1 марта
    //По середине даём новую и короткую цену:
    // 110 рублей, с 15 января по 15 февраля.
    //На выходе у нас три цены:100 рублей с 1 января по 15 января):
    //  100 рублей с 1 января по 15 января
     // 110 рублей, с 15 января по 15 февраля
    // 120 рублей с 15 февраля по 1 марта
    @Test
    public void two_price_with_one_overlap(){
        ArrayList<Price> oldPrice = new ArrayList <Price>();
        ArrayList<Price> newPrice = new ArrayList <Price>();
        ArrayList<Price> resultPrice = new ArrayList <Price>();
        oldPrice.add(new Price(100, "100", 1, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-02-01T00:00:00"),
                10000));
        oldPrice.add(new Price(100, "100", 1, 1,
                LocalDateTime.parse("2013-02-01T00:00:00"),
                LocalDateTime.parse("2013-03-01T00:00:00"),
                12000));

        newPrice.add(new Price(100, "100", 1,1,
                LocalDateTime.parse("2013-01-15T00:00:00"),
                LocalDateTime.parse("2013-02-15T00:00:00"),
                11000));
        resultPrice.add(new Price(100, "100", 1, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-01-15T00:00:00"),
                10000));
        resultPrice.add(new Price(100, "100", 1, 1,
                LocalDateTime.parse("2013-01-15T00:00:00"),
                LocalDateTime.parse("2013-02-15T00:00:00"),
                11000));
        resultPrice.add(new Price(100, "100", 1, 1,
                LocalDateTime.parse("2013-02-15T00:00:00"),
                LocalDateTime.parse("2013-03-01T00:00:00"),
                12000));
        assertEquals(JoinPriceMeth.joinPrice(oldPrice, newPrice), resultPrice);
    }

    //как в примере №3 задания
    //Даём три старые и длинные цены:
    // 80 рублей с 1 января по 1 февраля
    //87 рублей - с 1 февраля по 1 марта
    //90 рублей - с 1 марта по 1 апреля
    //По середине даём две новых цены:
    // 80 рублей, с 15 января по 15 февраля.
    // 85 рублей, с 15 февраля по 15 марта.
    //На выходе у нас три цены:100 рублей с 1 января по 15 января):
    //  80 рублей с 1 января по 15 февраля
    // 85 рублей, с 15 февраля по 15 марта
    // 90 рублей с 15 марта по 1 апреля
    @Test
    public void three_price_with_two_overlap(){
        ArrayList<Price> oldPrice = new ArrayList <Price>();
        ArrayList<Price> newPrice = new ArrayList <Price>();
        ArrayList<Price> resultPrice = new ArrayList <Price>();
        oldPrice.add(new Price(100, "100", 1, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-02-01T00:00:00"),
                8000));
        oldPrice.add(new Price(100, "100", 1, 1,
                LocalDateTime.parse("2013-02-01T00:00:00"),
                LocalDateTime.parse("2013-03-01T00:00:00"),
                8700));
        oldPrice.add(new Price(100, "100", 1, 1,
                LocalDateTime.parse("2013-03-01T00:00:00"),
                LocalDateTime.parse("2013-04-01T00:00:00"),
                9000));

        newPrice.add(new Price(100, "100", 1,1,
                LocalDateTime.parse("2013-01-15T00:00:00"),
                LocalDateTime.parse("2013-02-15T00:00:00"),
                8000));
        newPrice.add(new Price(100, "100", 1,1,
                LocalDateTime.parse("2013-02-15T00:00:00"),
                LocalDateTime.parse("2013-03-15T00:00:00"),
                8500));
        resultPrice.add(new Price(100, "100", 1, 1,
                LocalDateTime.parse("2013-01-01T00:00:00"),
                LocalDateTime.parse("2013-02-15T00:00:00"),
                8000));
        resultPrice.add(new Price(100, "100", 1,1,
                LocalDateTime.parse("2013-02-15T00:00:00"),
                LocalDateTime.parse("2013-03-15T00:00:00"),
                8500));
        resultPrice.add(new Price(100, "100", 1, 1,
                LocalDateTime.parse("2013-03-15T00:00:00"),
                LocalDateTime.parse("2013-04-01T00:00:00"),
                9000));
        assertEquals(JoinPriceMeth.joinPrice(oldPrice, newPrice), resultPrice);
    }
}









































































































































































































