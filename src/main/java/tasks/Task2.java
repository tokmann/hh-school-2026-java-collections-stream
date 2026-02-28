package tasks;

import common.Person;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 {


  /*
  Так, тут я хотел изначально попробовать решить с помощью двух указателей по одному на каждой коллекции и
  сначала их отсортировать, но я не уверен что это будет быстрее выбранного мной способа, так как надо бы было
  конвертировать коллекции в List для сортировки, потом отсортировать оба List. Что быстрее, чем сортировать один
  List в стриме. Но по итогу я решил выбрать другой способ, и еще отказался от concat в стриме так как этот метод
  создает еще два стрима, я воспользовался как и в прошлой задачке созданием листа с капасити так как она заранее
  известна, и лист не будет расширяться что ускорит работу. А затем просто стрим из итогового листа и сортировка
  со ссылкой на метод, с защитой от null значения поля createdAt
   */
  public static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                     Collection<Person> persons2,
                                                     int limit) {
    return Stream.concat(persons1.stream(), persons2.stream())
        .sorted(Comparator.comparing(Person::createdAt, Comparator.nullsLast(Instant::compareTo)))
        .limit(limit)
        .toList();
  }
}
