package pl.klolo.workshops.logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pl.klolo.workshops.domain.*;
import pl.klolo.workshops.domain.Currency;
import pl.klolo.workshops.mock.HoldingMockGenerator;

class WorkShop {

  /**
   * Lista holdingów wczytana z mocka.
   */
  private final List<Holding> holdings;

  // Predykat określający czy użytkownik jest kobietą
  private final Predicate<User> isWoman = null;

  WorkShop() {
    final HoldingMockGenerator holdingMockGenerator = new HoldingMockGenerator();
    holdings = holdingMockGenerator.generate();
  }

  /**
   * Metoda zwraca liczbę holdingów w których jest przynajmniej jedna firma.
   */
  long getHoldingsWhereAreCompanies() {
    long result = 0;
    for (Holding holding: holdings) {
        if(holding.getCompanies().size() >= 1){
          result++;
        }
    }
    return result;
  }

  /**
   * Metoda zwraca liczbę holdingów w których jest przynajmniej jedna firma. Napisz to za pomocą strumieni.
   */
  long getHoldingsWhereAreCompaniesAsStream() {
    return holdings.stream()
            .filter(c -> c.getCompanies().size() >= 1)
            .count();
  }

  /**
   * Zwraca nazwy wszystkich holdingów pisane z małej litery w formie listy.
   */
  List<String> getHoldingNames() {
    List<String> result = new LinkedList<>();

    for (Holding holding: holdings) {
      result.add(holding.getName().toLowerCase());
//      result.add(holding.getName().substring(0,1).toLowerCase() + holding.getName().substring(1));
    }
    return result;
  }

  /**
   * Zwraca nazwy wszystkich holdingów pisane z małej litery w formie listy. Napisz to za pomocą strumieni.
   */
  List<String> getHoldingNamesAsStream() {
    return holdings.stream()
            .map(holding -> holding.getName().toLowerCase())
            .collect(Collectors.toList());
  }

  /**
   * Zwraca nazwy wszystkich holdingów sklejone w jeden string i posortowane. String ma postać: (Coca-Cola, Nestle, Pepsico)
   */
  String getHoldingNamesAsString() {
    StringBuilder stringBuilder = new StringBuilder();
    holdings.sort(Comparator.comparing(holding -> holding.getName().substring(0, 1)));
    stringBuilder.append("(");
    for(int i = 0; i < holdings.size(); i++){
      stringBuilder.append(holdings.get(i).getName());
      if(i < holdings.size() - 1){
        stringBuilder.append(", ");
      }

    }

    stringBuilder.append(")");
    String result = stringBuilder.toString();
    return result;
  }

  /**
   * Zwraca nazwy wszystkich holdingów sklejone w jeden string i posortowane. String ma postać: (Coca-Cola, Nestle, Pepsico). Napisz to za pomocą strumieni.
   */
  String getHoldingNamesAsStringAsStream() {
    return holdings.stream()
            .sorted(Comparator.comparing(holding -> holding.getName().substring(0, 1)))
            .map(h -> h.getName())
            .collect(Collectors.joining(", ", "(", ")"));
  }

  /**
   * Zwraca liczbę firm we wszystkich holdingach.
   */
  long getCompaniesAmount() {
    int result = 0;

    for (Holding h: holdings) {
      result += h.getCompanies().size();
    }

    return result;
  }

  /**
   * Zwraca liczbę firm we wszystkich holdingach. Napisz to za pomocą strumieni.
   */
  long getCompaniesAmountAsStream() {
    return holdings.stream()
            .map(h -> h.getCompanies().size())
            .reduce(0, Integer::sum);
//            .collect(Collectors.summingLong(h -> h.getCompanies().size()));
  }

  /**
   * Zwraca liczbę wszystkich pracowników we wszystkich firmach.
   */
  long getAllUserAmount() {
    int result = 0;

    for (Holding holding: holdings) {
      for (Company c: holding.getCompanies()) {
        result += c.getUsers().size();
      };
    }

    return result;
  }

  /**
   * Zwraca liczbę wszystkich pracowników we wszystkich firmach. Napisz to za pomocą strumieni.
   */
  long getAllUserAmountAsStream() {
    return holdings.stream()
            .flatMap(h -> h.getCompanies().stream())
            .mapToLong(c -> c.getUsers().size())
            .sum();
  }

  /**
   * Zwraca listę wszystkich nazw firm w formie listy.
   */
  List<String> getAllCompaniesNames() {

    return null;
  }



  /**
   * Zwraca listę wszystkich nazw firm w formie listy. Tworzenie strumienia firm umieść w osobnej metodzie którą później będziesz wykorzystywać. Napisz to za
   * pomocą strumieni.
   */

  Stream<Company> createCompanyStream() {
    return holdings.stream()
            .flatMap(h -> h.getCompanies().stream());
  }

  List<String> getAllCompaniesNamesAsStream() {
    return createCompanyStream()
            .map(c -> c.getName())
            .collect(Collectors.toList());
  }

  /**
   * Zwraca listę wszystkich firm jako listę, której implementacja to LinkedList.
   */
  LinkedList<String> getAllCompaniesNamesAsLinkedList() {
    return null;
  }

  /**
   * Zwraca listę wszystkich firm jako listę, której implementacja to LinkedList. Obiektów nie przepisujemy po zakończeniu działania strumienia. Napisz to za
   * pomocą strumieni.
   */
  LinkedList<String> getAllCompaniesNamesAsLinkedListAsStream() {
    return holdings.stream()
            .flatMap(h -> h.getCompanies().stream())
            .map(c -> c.getName())
            .collect(Collectors.toCollection(LinkedList::new));
  }

  /**
   * Zwraca listę firm jako string gdzie poszczególne firmy są oddzielone od siebie znakiem "+"
   */
  String getAllCompaniesNamesAsString() {
    return null;
  }

  /**
   * Zwraca listę firm jako string gdzie poszczególne firmy są oddzielone od siebie znakiem "+" Napisz to za pomocą strumieni.
   */
  String getAllCompaniesNamesAsStringAsStream() {
    return holdings.stream()
            .flatMap(h -> h.getCompanies().stream())
            .map(c -> c.getName())
            .collect(Collectors.joining("+"));
  }

  /**
   * Zwraca listę firm jako string gdzie poszczególne firmy są oddzielone od siebie znakiem "+". Używamy collect i StringBuilder. Napisz to za pomocą
   * strumieni.
   * <p>
   * UWAGA: Zadanie z gwiazdką. Nie używamy zmiennych.
   */
  String getAllCompaniesNamesAsStringUsingStringBuilder() {
    return holdings.stream()
            .flatMap(h -> h.getCompanies().stream())
            .map(Company::getName)
            .collect(Collectors.joining("+"));
  }

  /**
   * Zwraca liczbę wszystkich rachunków, użytkowników we wszystkich firmach.
   */
  long getAllUserAccountsAmount() {
    return -1;
  }

  /**
   * Zwraca liczbę wszystkich rachunków, użytkowników we wszystkich firmach. Napisz to za pomocą strumieni.
   */
  long getAllUserAccountsAmountAsStream() {
    return holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .map(Company::getUsers)
            .flatMap(List::stream)
            .map(a -> a.getAccounts().size())
            .count();
  }

  /**
   * Zwraca listę wszystkich walut w jakich są rachunki jako string, w którym wartości występują bez powtórzeń i są posortowane.
   */
  String getAllCurrencies() {
    return null;
  }

  /**
   * Zwraca listę wszystkich walut w jakich są rachunki jako string, w którym wartości występują bez powtórzeń i są posortowane. Napisz to za pomocą strumieni.
   */
  String getAllCurrenciesAsStream() {
    return holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .map(Company::getUsers)
            .flatMap(List::stream)
            .map(User::getAccounts)
            .flatMap(List::stream)
            .map(Account::getCurrency)
            .collect(Collectors.toSet())
            .stream().sorted(Comparator.comparing(Enum::name))
            .map(Enum::name)
            .collect(Collectors.joining(", "));
  }

  /**
   * Metoda zwraca analogiczne dane jak getAllCurrencies, jednak na utworzonym zbiorze nie uruchamiaj metody stream, tylko skorzystaj z  Stream.generate.
   * Wspólny kod wynieś do osobnej metody.
   *
   * @see #getAllCurrencies()
   */
  String getAllCurrenciesUsingGenerate() {
    return null;
  }

  /**
   * Zwraca liczbę kobiet we wszystkich firmach.
   */
  long getWomanAmount() {
    return -1;
  }

  /**
   * Zwraca liczbę kobiet we wszystkich firmach. Powtarzający się fragment kodu tworzący strumień uzytkowników umieść w osobnej metodzie. Predicate określający
   * czy mamy doczynienia z kobietą inech będzie polem statycznym w klasie. Napisz to za pomocą strumieni.
   */
  Stream<User> getUsersStream(){
    return holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .map(Company::getUsers)
            .flatMap(List::stream);
  }

  long getWomanAmountAsStream() {
    return getUserStream()
            .filter(s -> s.getSex() == Sex.WOMAN)
            .count();
  }


  /**
   * Przelicza kwotę na rachunku na złotówki za pomocą kursu określonego w enum Currency. Ustaw precyzje na 3 miejsca po przecinku.
   */
  BigDecimal getAccountAmountInPLN(final Account account) {
    return Stream.of(account)
            .map(a -> (a.getAmount().multiply(new BigDecimal(a.getCurrency().rate))).setScale(3, RoundingMode.FLOOR))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }


  /**
   * Przelicza kwotę na rachunku na złotówki za pomocą kursu określonego w enum Currency. Napisz to za pomocą strumieni.
   */
  BigDecimal getAccountAmountInPLNAsStream(final Account account) {
//    BigDecimal cos = getUserStream()
//            .map(User::getAccounts)
//            .flatMap(List::stream)
//            .map( a -> a.getAmount().multiply(new BigDecimal(Float.toString(a.getCurrency().rate))))
//            .collect(Collectors.toList())
//            .stream()
//            .reduce(BigDecimal.ZERO, BigDecimal::add);

    return null;

  }

  /**
   * Przelicza kwotę na podanych rachunkach na złotówki za pomocą kursu określonego w enum Currency  i sumuje ją.
   */
  BigDecimal getTotalCashInPLN(final List<Account> accounts) {
    return null;
  }

  /**
   * Przelicza kwotę na podanych rachunkach na złotówki za pomocą kursu określonego w enum Currency  i sumuje ją. Napisz to za pomocą strumieni.
   */
  BigDecimal getTotalCashInPLNAsStream(final List<Account> accounts) {

    return accounts.stream()
            .map(a -> (a.getAmount().multiply(new BigDecimal(a.getCurrency().rate))).setScale(3, RoundingMode.FLOOR))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  /**
   * Zwraca imiona użytkowników w formie zbioru, którzy spełniają podany warunek.
   */
  Set<String> getUsersForPredicate(final Predicate<User> userPredicate) {

    return holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .map(Company::getUsers)
            .flatMap(List::stream)
            .filter(userPredicate)
            .map(User::getFirstName)
            .collect(Collectors.toSet());
  }

  /**
   * Zwraca imiona użytkowników w formie zbioru, którzy spełniają podany warunek. Napisz to za pomocą strumieni.
   */
  Set<String> getUsersForPredicateAsStream(final Predicate<User> userPredicate) {
    return null;
  }

  /**
   * Metoda filtruje użytkowników starszych niż podany jako parametr wiek, wyświetla ich na konsoli, odrzuca mężczyzn i zwraca ich imiona w formie listy.
   */
  List<String> getOldWoman(final int age) {

    return holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .map(Company::getUsers)
            .flatMap(List::stream)
            .filter(u -> u.getAge() > age)
            .peek(a -> System.out.println(a.getFirstName()))
            .filter(u -> u.getSex() == Sex.WOMAN)
            .map(User::getFirstName)
            .collect(Collectors.toList());

  }

  /**
   * Metoda filtruje użytkowników starszych niż podany jako parametr wiek, wyświetla ich na konsoli, odrzuca mężczyzn i zwraca ich imiona w formie listy. Napisz
   * to za pomocą strumieni.
   */
  List<String> getOldWomanAsStream(final int age) {
    return null;
  }

  /**
   * Dla każdej firmy uruchamia przekazaną metodę.
   */
  void executeForEachCompany(final Consumer<Company> consumer) {
    holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .forEach(consumer::accept);
//    throw new IllegalArgumentException();
  }

  /**
   * Wyszukuje najbogatsza kobietę i zwraca ja. Metoda musi uzwględniać to że rachunki są w różnych walutach.
   */
  Optional<User> getRichestWoman() {
    return holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .map(Company::getUsers)
            .flatMap(List::stream)
            .filter(u -> u.getSex() == Sex.WOMAN)
            .max(Comparator.comparing(u -> u.getAccounts().stream()
              .mapToInt(a -> a.getAmount()
                      .multiply(new BigDecimal(a.getCurrency().rate))
                      .intValue())
                        .sum()));
  }

  /**
   * Wyszukuje najbogatsza kobietę i zwraca ja. Metoda musi uzwględniać to że rachunki są w różnych walutach. Napisz to za pomocą strumieni.
   */
  Optional<User> getRichestWomanAsStream() {
    return null;
  }

  /**
   * Zwraca nazwy pierwszych N firm. Kolejność nie ma znaczenia.
   */
  Set<String> getFirstNCompany(final int n) {
    return holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .map(Company::getName)
            .limit(5)
            .collect(Collectors.toSet());
  }

  /**
   * Zwraca nazwy pierwszych N firm. Kolejność nie ma znaczenia. Napisz to za pomocą strumieni.
   */



  Set<String> getFirstNCompanyAsStream(final int n) {
    return null;
  }

  /**
   * Metoda zwraca jaki rodzaj rachunku jest najpopularniejszy. Stwórz pomocniczą metdę getAccountStream. Jeżeli nie udało się znaleźć najpopularnijeszego
   * rachunku metoda ma wyrzucić wyjątek IllegalStateException. Pierwsza instrukcja metody to return.
   */

  Stream<Account> getAccountStream(){
    return holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .map(Company::getUsers)
            .flatMap(List::stream)
            .map(User::getAccounts)
            .flatMap(List::stream);
  }


  AccountType getMostPopularAccountType() {

    return getAccountStream()
            .map(Account::getType)
            .collect(Collectors.groupingBy(AccountType::name, Collectors.counting()))
            .entrySet().stream()
            .max(Comparator.comparing(Map.Entry::getValue))
            .map(m -> {
              try {
                return AccountType.valueOf(m.getKey());
              }catch (NullPointerException e){
                throw new IllegalStateException();
              }})
            .get();
  }


  /**
   * Metoda zwraca jaki rodzaj rachunku jest najpopularniejszy. Stwórz pomocniczą metdę getAccountStream. Jeżeli nie udało się znaleźć najpopularnijeszego
   * rachunku metoda ma wyrzucić wyjątek IllegalStateException. Pierwsza instrukcja metody to return. Napisz to za pomocą strumieni.
   */
  AccountType getMostPopularAccountTypeAsStream() {
    return null;
  }

  /**
   * Zwraca pierwszego z brzegu użytkownika dla podanego warunku. W przypadku kiedy nie znajdzie użytkownika wyrzuca wyjątek IllegalArgumentException.
   */


  User getUser(final Predicate<User> predicate) throws IllegalArgumentException{
    return getUserStream()
            .filter(predicate::test)
            .findFirst()
            .get();
  }

  /**
   * Zwraca pierwszego z brzegu użytkownika dla podanego warunku. W przypadku kiedy nie znajdzie użytkownika wyrzuca wyjątek IllegalArgumentException. Napisz to
   * za pomocą strumieni.
   */
  User getUserAsStream(final Predicate<User> predicate) {
    return null;
  }

  /**
   * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników.
   */
  Map<String, List<User>> getUserPerCompany() {
    return holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .collect(Collectors.toMap(Company::getName, Company::getUsers));
  }

  /**
   * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników. Napisz to za pomocą strumieni.
   */
  Map<String, List<User>> getUserPerCompanyAsStream() {
    return null;
  }

  /**
   * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako string składający się z imienia i nazwiska. Podpowiedź:
   * Możesz skorzystać z metody entrySet.
   */
  Map<String, List<String>> getUserPerCompanyAsString() {
    return holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .collect(Collectors.toMap(Company::getName, c -> c.getUsers()
                    .stream()
                    .map(u -> u.getFirstName() + " " + u.getLastName())
                    .collect(Collectors.toList())));
  }

  /**
   * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako string składający się z imienia i nazwiska. Podpowiedź:
   * Możesz skorzystać z metody entrySet. Napisz to za pomocą strumieni.
   */
  Map<String, List<String>> getUserPerCompanyAsStringAsStream() {
    return null;
  }

  /**
   * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako obiekty typu T, tworzonych za pomocą przekazanej
   * funkcji.
   */
  <T> Map<String, List<T>> getUserPerCompany(final Function<User, T> converter) {
    return holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .collect(Collectors.toMap(Company::getName, c -> c.getUsers().stream()
                    .map(u -> converter.apply(u))
                    .collect(Collectors.toList())));
  }

  /**
   * Zwraca mapę firm, gdzie kluczem jest jej nazwa a wartością lista pracowników przechowywanych jako obiekty typu T, tworzonych za pomocą przekazanej funkcji.
   * Napisz to za pomocą strumieni.
   */
  <T> Map<String, List<T>> getUserPerCompanyAsStream(final Function<User, T> converter) {
    return null;
  }

  /**
   * Zwraca mapę gdzie kluczem jest flaga mówiąca o tym czy mamy do czynienia z mężczyzną, czy z kobietą. Osoby "innej" płci mają zostać zignorowane. Wartością
   * jest natomiast zbiór nazwisk tych osób.
   */

  boolean isMan(Sex sex){
    return sex.equals(Sex.MAN)? true : false;
  }

  Map<Boolean, Set<String>> getUserBySex() {

     holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .map(Company::getUsers)
            .flatMap(List::stream)
            .filter(u -> u.getSex() != Sex.OTHER)
            .collect(Collectors.partitioningBy(user -> user.getSex().equals(Sex.MAN), Collectors.toSet()));
    return null;
//            .collect(Collectors.toMap(a -> a.getSex().equals(Sex.MAN), streamOf));
//    return getUserStream()
//            .filter(u -> u.getSex() != Sex.OTHER)
//            .collect(Collectors.toMap(u -> u.getSex().equals(Sex.MAN), User::getLastName));
//            .collect(Collectors.toMap(a -> a.getSex().equals(Sex.MAN)? true : false, ));
  }
//a -> a.getSex().equals(Sex.MAN)? true : false
  /**
   * Zwraca mapę gdzie kluczem jest flaga mówiąca o tym czy mamy do czynienia z mężczyzną, czy z kobietą. Osoby "innej" płci mają zostać zignorowane. Wartością
   * jest natomiast zbiór nazwisk tych osób. Napisz to za pomocą strumieni.
   */
  Map<Boolean, Set<String>> getUserBySexAsStream() {
    return null;
  }

  /**
   * Zwraca mapę rachunków, gdzie kluczem jesy numer rachunku, a wartością ten rachunek.
   */
  Map<String, Account> createAccountsMap() {
    return null;
  }

  /**
   * Zwraca mapę rachunków, gdzie kluczem jesy numer rachunku, a wartością ten rachunek. Napisz to za pomocą strumieni.
   */
  Map<String, Account> createAccountsMapAsStream() {
    return null;
  }

  /**
   * Zwraca listę wszystkich imion w postaci Stringa, gdzie imiona oddzielone są spacją i nie zawierają powtórzeń.
   */
  String getUserNames() {
    return null;
  }

  /**
   * Zwraca listę wszystkich imion w postaci Stringa, gdzie imiona oddzielone są spacją i nie zawierają powtórzeń. Napisz to za pomocą strumieni.
   */
  String getUserNamesAsStream() {
    return null;
  }

  /**
   * zwraca zbiór wszystkich użytkowników. Jeżeli jest ich więcej niż 10 to obcina ich ilość do 10.
   */
  Set<User> getUsers() {
    return null;
  }

  /**
   * zwraca zbiór wszystkich użytkowników. Jeżeli jest ich więcej niż 10 to obcina ich ilość do 10. Napisz to za pomocą strumieni.
   */
  Set<User> getUsersAsStream() {
    return null;
  }

  /**
   * Zwraca użytkownika, który spełnia podany warunek.
   */
  Optional<User> findUser(final Predicate<User> userPredicate) {
    return null;
  }

  /**
   * Zwraca użytkownika, który spełnia podany warunek. Napisz to za pomocą strumieni.
   */
  Optional<User> findUserAsStream(final Predicate<User> userPredicate) {
    return null;
  }

  /**
   * Dla podanego użytkownika zwraca informacje o tym ile ma lat w formie: IMIE NAZWISKO ma lat X. Jeżeli użytkownik nie istnieje to zwraca text: Brak
   * użytkownika.
   * <p>
   * Uwaga: W prawdziwym kodzie nie przekazuj Optionali jako parametrów. Napisz to za pomocą strumieni.
   */
  String getAdultantStatusAsStream(final Optional<User> user) {
    return null;
  }

  /**
   * Metoda wypisuje na ekranie wszystkich użytkowników (imie, nazwisko) posortowanych od z do a. Zosia Psikuta, Zenon Kucowski, Zenek Jawowy ... Alfred
   * Pasibrzuch, Adam Wojcik
   */
  void showAllUser() {
    throw new IllegalArgumentException("not implemented yet");
  }

  /**
   * Metoda wypisuje na ekranie wszystkich użytkowników (imie, nazwisko) posortowanych od z do a. Zosia Psikuta, Zenon Kucowski, Zenek Jawowy ... Alfred
   * Pasibrzuch, Adam Wojcik. Napisz to za pomocą strumieni.
   */
  void showAllUserAsStream() {
    throw new IllegalArgumentException("not implemented yet");
  }

  /**
   * Zwraca mapę, gdzie kluczem jest typ rachunku a wartością kwota wszystkich środków na rachunkach tego typu przeliczona na złotówki.
   */
  Map<AccountType, BigDecimal> getMoneyOnAccounts() {
    return null;
  }

  /**
   * Zwraca mapę, gdzie kluczem jest typ rachunku a wartością kwota wszystkich środków na rachunkach tego typu przeliczona na złotówki. Napisz to za pomocą
   * strumieni. Ustaw precyzje na 0.
   */
  Map<AccountType, BigDecimal> getMoneyOnAccountsAsStream() {
    return null;
  }

  /**
   * Zwraca sumę kwadratów wieków wszystkich użytkowników.
   */
  int getAgeSquaresSum() {
    return -1;
  }

  /**
   * Zwraca sumę kwadratów wieków wszystkich użytkowników. Napisz to za pomocą strumieni.
   */
  int getAgeSquaresSumAsStream() {
    return -1;
  }

  /**
   * Metoda zwraca N losowych użytkowników (liczba jest stała). Skorzystaj z metody generate. Użytkownicy nie mogą się powtarzać, wszystkie zmienną muszą być
   * final. Jeżeli podano liczbę większą niż liczba użytkowników należy wyrzucić wyjątek (bez zmiany sygnatury metody).
   */
  List<User> getRandomUsers(final int n) {
    return null;
  }

  /**
   * Metoda zwraca N losowych użytkowników (liczba jest stała). Skorzystaj z metody generate. Użytkownicy nie mogą się powtarzać, wszystkie zmienną muszą być
   * final. Jeżeli podano liczbę większą niż liczba użytkowników należy wyrzucić wyjątek (bez zmiany sygnatury metody). Napisz to za pomocą strumieni.
   */
  List<User> getRandomUsersAsStream(final int n) {
    return null;
  }

  /**
   * Stwórz mapę gdzie kluczem jest typ rachunku a wartością mapa mężczyzn posiadających ten rachunek, gdzie kluczem jest obiekt User a wartoscią suma pieniędzy
   * na rachunku danego typu przeliczona na złotkówki.
   */
  Map<AccountType, Map<User, BigDecimal>> getAccountUserMoneyInPLNMap() {
    return null;
  }

  /**
   * Stwórz mapę gdzie kluczem jest typ rachunku a wartością mapa mężczyzn posiadających ten rachunek, gdzie kluczem jest obiekt User a wartoscią suma pieniędzy
   * na rachunku danego typu przeliczona na złotkówki.  Napisz to za pomocą strumieni.
   */
  Map<AccountType, Map<User, BigDecimal>> getAccountUserMoneyInPLNMapAsStream() {
    return null;
  }

  /**
   * Podziel wszystkich użytkowników po ich upoważnieniach, przygotuj mapę która gdzie kluczem jest upoważnenie a wartością lista użytkowników, posortowana po
   * ilości środków na koncie w kolejności od największej do najmniejszej ich ilości liczonej w złotówkach.
   */

  Map<Permit, List<User>> getUsersByTheyPermitsSorted() {
    return null;
  }

  /**
   * Podziel wszystkich użytkowników po ich upoważnieniach, przygotuj mapę która gdzie kluczem jest upoważnenie a wartością lista użytkowników, posortowana po
   * ilości środków na koncie w kolejności od największej do najmniejszej ich ilości liczonej w złotówkach. Napisz to za pomoca strumieni.
   */

  Map<Permit, List<User>> getUsersByTheyPermitsSortedAsStream() {
    return null;
  }

  /**
   * Podziel użytkowników na tych spełniających podany predykat i na tych niespełniających. Zwracanym typem powinna być mapa Boolean => spełnia/niespełnia,
   * List<Users>
   */
  Map<Boolean, List<User>> divideUsersByPredicate(final Predicate<User> predicate) {
    return null;
  }

  /**
   * Podziel użytkowników na tych spełniających podany predykat i na tych niespełniających. Zwracanym typem powinna być mapa Boolean => spełnia/niespełnia,
   * List<Users>. Wykonaj zadanie za pomoca strumieni.
   */
  Map<Boolean, List<User>> divideUsersByPredicateAsStream(final Predicate<User> predicate) {
    return null;
  }

  /**
   * Zwraca strumień wszystkich firm.
   */
  private Stream<Company> getCompanyStream() {
    return null;
  }

  /**
   * Zwraca zbiór walut w jakich są rachunki.
   */
  private Set<Currency> getCurenciesSet() {
    return null;
  }

  /**
   * Tworzy strumień rachunków.
   */
  private Stream<Account> getAccoutStream() {
    return holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .map(Company::getUsers)
            .flatMap(List::stream)
            .map(User::getAccounts)
            .flatMap(List::stream);
  }

  /**
   * Tworzy strumień użytkowników.
   */
  private Stream<User> getUserStream() {
    return holdings.stream()
            .map(Holding::getCompanies)
            .flatMap(List::stream)
            .map(Company::getUsers)
            .flatMap(List::stream);
  }

}
