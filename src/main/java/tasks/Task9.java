package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  // Костыль, эластик всегда выдает в топе "фальшивую персону". (ПОПРАВЛЕНО)
  // Конвертируем начиная со второй
  /*
  Тут я убрал это удаление из начала листа так как из за этого все будет сдвигаться влево, очень замедлит работу, и к
  тому же это меняет исходный список, что то может сломаться во внешней логике какой то. Просто пропускаю первый
  элемент в стриме. И это будет корректно для моментов когда 0 или 1 элементов в списке, будет возвращаться пустой
  список
   */
  public List<String> getNames(List<Person> persons) {
    return persons.stream()
        .skip(1)
        .map(Person::firstName)
        .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется) (ПОПРАВЛЕНО)
  /*
  Здесь вобще distinct() не имел значения так как все по итогу собиралось в сет. И в целом если убрать disctinct() то
  остается только терминальный collect в сет, и почему бы тогда вобще не создавать стрим, а просто воспользоваться
  конструктором сета и все, намного проще вышло.
   */
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО (ПОПРАВЛЕНО)
  /*
  Ну во первых здесь было что то не так с полями, два раза одно и то же повторялось, это поправлено. Во вторых вместо
  конкатенации строк я сделал String.join и еще с проверкой на null, я думаю так локаничнее, удобнее и читабельнее
   */
  public String convertPersonToString(Person person) {
    return String.join("",
        person.secondName() == null ? "" : person.secondName(),
        person.firstName() == null ? "" : person.firstName(),
        person.middleName() == null ? "" : person.middleName());
  }

  // словарь id персоны -> ее имя (ПОПРАВЛЕНО)
  /*
  Здесь я убрал изначальную капасити мапы равную 1, так как она будет часто расширяться, это долго. Я думаю можно
  поставить ее равной длине persons. И затем вместо двух поисков по хешу можно использовать красивый метод
  computeIfAbsent(), тогда не надо будет отдельно проверять наличие ключа в мапе.
   */
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    Map<Integer, String> map = new HashMap<>(persons.size());
    for (Person person : persons) {
      map.computeIfAbsent(person.id(), id -> convertPersonToString(person));
    }
    return map;
  }

  // есть ли совпадающие в двух коллекциях персоны? (ПОПРАВЛЕНО)
  /*
  Здесь можно было ретурнить true во вложенном цикле если нашлось, и в конце метода false ретурнить чтобы ускорить
  метод. Но это в целом не очень алгоритм, с вложенным циклом, сложность получатеся O(m * n) где m - длина первой
  коллекции и n - длина второй коллекции. Я решил сделать другую реализацию, можно добавить элементы из одной коллекции
  в сет, а затем пройтись по другой и проверить каждый Person на содержание в сете, такая реализация по времени
  будет O(n + m)
   */
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> personSet = new HashSet<>(persons1);
    for (Person person : persons2) {
      if (personSet.contains(person)) {
        return true;
      }
    }
    return false;
  }

  // Посчитать число четных чисел (ПОПРАВЛЕНО)
  /*
  Я помню что вы говорили на лекции что лучше не надо делать метод который принимает стрим, но тут я ничего не могу
  поделать. Но кое что исправил, есть специально для подсчета терминальный метод count() в стримах, можно вернуть его
  результат, вместо того чтобы использовать глобальную переменную.
   */
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен (ОТГАДАЛ)
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  /*
  Тут суть в том что хэш сет работает во первых так же как и мапа, только вместо значений заглушки. То есть
  хэш сет распределяет по бакетам ключи с помощью нахождения их хэша и наши ключи это Integer. А у Integer хэш это само
  значение числа. То есть условно для 1 будет хэшкод 1, для 2 - 2 и т.д. И получается что он их распределил по бакетам
  по порядку по возрастанию. А shapshot это отсортированный список, там такое же содержимое как в integers до перемешки,
  а там использовалось rangeClosed это то же самое что заполнение в цикле по порядку. Надеюсь понятно объяснил
   */
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
