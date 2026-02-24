package tasks;

import common.Area;
import common.Person;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  /*
  Здесь все решается мапой так как нужно отношение id (айдишник региона) to Area. Она создается за O(m) m - это кол-во
  регионов, затем в цикле проходимся по персонам, это еще O(n), n - кол-во персон, и для каждой проходимся по сету за
  O(k), k - это кол-во регионов у персоны, и с помощью мапы находим название регионов и добавляем строчки в ответ.
  Тут я решил не использовать никакой sb, так как для трех строк это ничего не ускорит. Получается ассимптотика
  O(m + n * k), я думаю самый быстрый вариант
   */
  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {
    Map<Integer, Area> idAndArea = new HashMap<>();
    for (Area area : areas) {
      idAndArea.put(area.getId(), area);
    }

    Set<String> result = new HashSet<>();
    for (Person person : persons) {
      Set<Integer> regions = personAreaIds.get(person.id());
      for (int regionId : regions) {
        result.add(person.firstName() + " - " + idAndArea.get(regionId).getName());
      }
    }
    return result;
  }
}
