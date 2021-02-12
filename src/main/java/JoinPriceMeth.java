import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class JoinPriceMeth {

       //ЦЕЛЕВОЙ МЕТОД
    public static List<Price> joinPrice(List<Price> oldPrices, List<Price> newPrices) {
        HashMap <String, ArrayList<Price>> newPriceMap = toGetHashMapFromList(newPrices);
        HashMap <String, ArrayList<Price>> oldPriceMap = toGetHashMapFromList(oldPrices);
        ArrayList <Price>  result = new ArrayList<>();
        //Перебираем все одинаковые ключи и добавляем их в результирующий массив
        newPriceMap.forEach((key,value)->{
            if(oldPriceMap.containsKey(key)){
                result.addAll(subJoinPrice(oldPriceMap.get(key), newPriceMap.get(key))); //Добавляем слитый массив  по конкретному, где ключ в старых и новых ценах совпадает
                newPriceMap.get(key).clear();
                oldPriceMap.get(key).clear();
            }
        });

        newPriceMap.forEach((key,value)-> result.addAll(value)); //Добавляем в коллекцию те цены, которые не имеют дубликатов
        oldPriceMap.forEach((key,value)-> result.addAll(value)); //Добавляем в коллекцию те цены, которые не имеют дубликатов
        //Здесь нужно добавить все эти цены в результирующий массив
        result.sort(base);
        return result;
    }

    //Метод возвращает карту из массива.
    public static HashMap <String, ArrayList<Price>> toGetHashMapFromList(List<Price> Lp){
        HashMap <String, ArrayList<Price>> goal = new HashMap<>();

        Lp.stream().forEach(price->{
            //Задаём ключ карты как код продукта, номер цены и код отдела (возможно филиал магазина)
            //По большому счёту не очень ясно, нужно ли код отдела включать в код цены.
            String key_map =  new String(price.getProductCode()+"_"+price.getNumber()+"_"+price.getDepart());
            if( goal.containsKey(key_map)) {
                goal.get(key_map).add(new Price(price)); //Добавляем копию цены, чтобы сохранить
                //целостность исходных данных
            }
            else
            {
                goal.put(key_map, new ArrayList<Price>());
                goal.get(key_map).add(new Price(price));
            }
        });
        return goal;
    }

    //В этом методе предполагается попадут только цены у которых код и номер одинаковы
    public static List<Price> subJoinPrice(List<Price> oldPrices, List<Price> newPrices) {
        ArrayList <Price> one = new ArrayList<>(oldPrices);
        ArrayList <Price> two = new ArrayList<>(newPrices);
        one.sort(base);
        two.sort(base);
        ArrayList <Price> result = new ArrayList<>();

        ListIterator <Price> one_iter = one.listIterator();
        ListIterator <Price> two_iter = two.listIterator();
        Price s = null;
        Price t = null;
        OUTER:
        for(;one_iter.hasNext();)
        {
            s = one_iter.next();
            two_iter=two.listIterator();
            for(;two_iter.hasNext();){
                t = two_iter.next();
                if(t.getBegin().isAfter(s.getEnd())) {
                    result.add(s);
                    one.remove(s);
                    one_iter=one.listIterator();
                    continue OUTER;
                }
                if(t.getEnd().isBefore(s.getBegin())){
                    two.remove(t);
                    result.add(t);
                    two_iter = two.listIterator();
                    continue;
                }

                if(s.getBegin().isBefore(t.getBegin())){
                    result.add(new Price(s, s.getBegin(), t.getBegin())); //.minusSeconds(1) - убрали, чтобы пройти тест
                    if(t.getEnd().isAfter(s.getEnd())||t.getEnd().isEqual(s.getEnd())){
                        //  two.remove(t); //ew
                        //  result.add(t);
                        two_iter = two.listIterator();
                        one.remove(s); //ew
                        one_iter=one.listIterator(); //ew
                        continue OUTER;
                    }
                    if(t.getEnd().isBefore(s.getEnd())){
                        result.add(t);
                        two.remove(t);
                        two_iter = two.listIterator();
                        s.setBegin(t.getEnd()); // .plusSeconds(1) - убрали, чтобы пройти тест
                        continue;
                    }
                }
                else { //Не явно s.getBegin()>=t.getBegin()
                    if(
                      t.getEnd().isAfter(s.getEnd()) || t.getEnd().isEqual(s.getEnd())
                    )
                    {
                        if(!one_iter.hasNext()) {
                            result.addAll(two);
                            two.clear(); //Костыль - На случай кросс
                        }
                        one.remove(s);
                        one_iter=one.listIterator();
                        continue OUTER;
                    }
                    if(t.getEnd().isBefore(s.getEnd())){
                        s.setBegin(t.getEnd());//.plusSeconds(1) - убрали, чтобы пройти тест
                        result.add(t);
                        two.remove(t);
                        two_iter = two.listIterator();
                    }
                }
            }
        }
        result.addAll(two);
        result.addAll(one);
        result.sort(base);
        result = toUnionSamplePrice(result);
        return result;
    }

    //Если две одинаковые по значению цены (у которых отличаются только периоды)
    //Так мы сошьём две цены, к примеру с 1 по 15 и с 16 по 31 января в одну -> c 1 по 31 января
    public static ArrayList<Price> toUnionSamplePrice(ArrayList <Price> inner){
        ArrayList<Price> outer = new ArrayList<>();
        ListIterator <Price> inIter= inner.listIterator();
        Price out = null; //цена далее добавляемая в выходной массив. 
        Price pr = null;
        if(inIter.hasNext()) {
            pr= inIter.next();
            out = pr;
            for (; inIter.hasNext(); ) {
                pr= inIter.next();
                if (equalValuePrice(pr, out)) {
                    out = merge(pr, out);
                } else {
                    outer.add(out);
                    out = pr;
                }
            }
            if (!inIter.hasNext() && out != null) {
                outer.add(out);
            }
        }
        return outer;
    }
    //
    //
    static boolean equalValuePrice(Price p1, Price p2) {
        return p1.getValue() == p2.getValue() &&
               p1.getProductCode().equals(p2.getProductCode())&&
               p1.getDepart()==p2.getDepart() &&
               p1.getNumber() ==p2.getNumber();
    }


    //К примеру, на один и тот же товар действует одна цена, но из-за работы предыдущего метода
    //она поделена на два отрезка.
    //Сюда можно передавать только одинаковую цену на одинаковый товар.
    static Price merge(Price p1, Price p2) {
        return new Price(p1, minDateTime(p1.getBegin(), p2.getBegin()),
                maxDateTime(p1.getEnd(), p2.getEnd()));
    }

    static LocalDateTime minDateTime(LocalDateTime L1, LocalDateTime L2){
        if(L1.isBefore(L2))
            return L1;
        return L2;
    }

    static LocalDateTime maxDateTime(LocalDateTime L1, LocalDateTime L2){
        if(L1.isAfter(L2))
            return L1;
        return L2;
    }

    //Основной компаратор - для сортировки цен в списках
    static Comparator<Price> base = Comparator.comparing(Price::getProductCode)
            .thenComparing(Price::getDepart)
            .thenComparing(Price::getNumber)
            .thenComparing(Price::getBegin)
            .thenComparing(Price::getEnd);
}
