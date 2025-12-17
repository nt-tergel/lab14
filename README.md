# Лабораторийн ажил №14: Maven-д суурилсан Java төсөлд Git Workflow ба GitHub Actions

<div align="center">

**Шинжлэх ухаан, Технологийн Их Сургууль**  
**Мэдээлэл, Холбооны Технологийн Сургууль**

<br><br><br>

**F.CSA313 Программ хангамжийн чанарын баталгаа ба туршилт**

<br><br><br><br><br>

# **ЛАБОРАТОРИЙН АЖИЛ №14**

<br><br><br><br><br>

**Шалгасан багш:** А.Отгонбаяр /F.SW02/  
**Гүйцэтгэсэн оюутан:** Б. Тэргэл /B222270006/, Г.Энхбаяр /B222270038/

<br><br><br>

**Улаанбаатар хот**  
**2025 он**

</div>

## Зорилго

- Maven-д суурилсан Java төслийг Git workflow-оор удирдаж, салбарлах/нэгтгэх ажиллагааг дуурайлган хэрэгжүүлэх.
- GitHub Actions ашиглан JUnit тест, Checkstyle, JaCoCo-ийн 100% branch coverage шаардлагатай CI процессыг автоматжуулах.
- main салбарыг шууд push-ээс хамгаалж, зөвхөн ногоон CI-тэй PR-ээр merge хийх горимыг тогтоох.

## Хэрэглэсэн хэрэгслүүд

- Git, GitHub (branch protection, PR merge commit)
- Maven 3.8.9+, Java 17, JUnit 5
- Maven Surefire, Checkstyle (Google-like дүрэм), JaCoCo 0.8.12 (100% branch coverage rule)
- GitHub Actions (`ubuntu-latest`, `actions/setup-java@v4`)
- IDE/CLI: IntelliJ IDEA / VS Code / терминал (Maven)

## Төслийн бүтэц ба гол файлууд

- Maven төслийн үндсэн тохиргоо: `demo/pom.xml` (JUnit 5, Surefire, Checkstyle, JaCoCo 100% branch rule)

```1:98:demo/pom.xml
<!-- JaCoCo with 100% branch coverage -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.12</version>
    <configuration>
        <rules>
            <rule>
                <element>BUNDLE</element>
                <limits>
                    <limit>
                        <counter>BRANCH</counter>
                        <value>COVEREDRATIO</value>
                        <minimum>1.0</minimum>
                    </limit>
                </limits>
            </rule>
        </rules>
    </configuration>
    ...
</plugin>
```

- Checkstyle дүрэм: `demo/checkstyle.xml` (LineLength 120, MethodLength 100, Indentation)

```1:15:demo/checkstyle.xml
<module name="Checker">
    <property name="severity" value="error"/>
    <module name="LineLength">
        <property name="max" value="120"/>
    </module>
    <module name="TreeWalker">
        <module name="Indentation"/>
        <module name="MethodLength">
            <property name="max" value="100"/>
        </module>
    </module>
</module>
```

- CI workflow: `.github/workflows/ci.yml` (push/pr to develop, release/\*; runs checkstyle → test + jacoco:check → jacoco:report)

```1:31:.github/workflows/ci.yml
name: CI Process
on:
  push:
    branches:
      - develop
      - release/*
  pull_request:
    branches:
      - develop
      - release/*
jobs:
  build-and-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: "17"
          distribution: "temurin"
      - name: Checkstyle Check
        run: cd demo && mvn checkstyle:check
      - name: Run JUnit Tests and JaCoCo Coverage
        run: cd demo && mvn test jacoco:check
```

- Эх код: `Multiplication`, `Division`

```1:20:demo/src/main/java/labxx/sict/must/edu/mn/Division.java
public class Division {
    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }
}
```

- Тестүүд (хоёр path-ийг бүрэн хамарсан):

```1:27:demo/src/test/java/labxx/sict/must/edu/mn/DivisionTest.java
@Test
void testDivideByZero() {
    Division calc = new Division();
    assertThrows(IllegalArgumentException.class,
        () -> calc.divide(4.0, 0.0),
        "Division by zero should throw IllegalArgumentException");
}
```

## Гүйцэтгэсэн ажлууд

- Maven төслийг эхлүүлж, JUnit5/Surefire/Checkstyle/JaCoCo-ийг `pom.xml`-д нэгтгэв; branch coverage 100% шаардлагыг JaCoCo-ийн rule-ээр тохируулсан.
- Checkstyle дүрмийг төслийн үндсэнд байршуулж, CI дээр заавал шалгахаар холбов.
- GitHub Actions workflow-г develop, release/\* салбаруудад push болон PR үед ажиллахаар үүсгэж, дараалсан алхмаар checkstyle → test+jacoco:check → report гүйцэтгэдэг болгосон.
- Энгийн calculator функциональ: `multiply`, `divide` + JUnit тестүүд (positive/negative multiply; normal/zero divide) — бүх branch-ууд (exception орно) тестээр хамрагдсан.
- Git workflow-гийн шаардлага дагуу салбарлах стратеги (main хамгаалалт, develop, feature/_, release/_, hotfix/\*) ба merge commit-ээр нэгтгэх горимыг баримтжуулсан.
- Conflict үүсгэж шийдвэрлэснийг `conflict_resolution.md`-д тэмдэглэсэн (feature/add-division ↔ develop, `Calculator.java` дээр гарсан гэсэн бүртгэл).

```1:32:conflict_resolution.md
A merge conflict occurred when merging feature/add-division into develop.
...
git add Calculator.java
git commit
```

## CI ба шалгалтын гүйцэтгэл

- CI-конфиг бэлэн; локалд `mvn` binary суулгагдаагүй тул (`command not found: mvn`) автомат тест/coverage-г эндээс ажиллуулж нотолж чадсангүй. Maven суулгасны дараа дараах тушаалыг ажиллуулснаар CI-тэй ижил шалгалт хийнэ:
  - `cd demo && mvn clean test`
  - `cd demo && mvn checkstyle:check`
  - `cd demo && mvn jacoco:check`
  - `cd demo && mvn jacoco:report` (тайлан: `demo/target/site/jacoco/index.html`)
- Явцын таамаг: Division-ийн хоёр branch (normal, zero) болон Multiplication-ийн энгийн замууд бүгд тестлэгдсэн тул JaCoCo-ийн 100% branch rule хангагдана.

## Conflict шийдвэрлэлтийн тайлан

- PR merge үед гарсан конфликт (feature/add-division → develop) нь `conflict_resolution.md`-д алхам бүрээр (git merge, файлыг гараар засварлах, git add/commit) баримтжуулагдсан.
- Нэгтгэлийг merge commit-ээр дуусгасан гэж тэмдэглэсэн; CI алдаа гараагүй бол merge зөвшөөрөгдөх тохиргоотой.

## Дүгнэлт ба дараагийн алхам

- Maven төслийн бүтцээс эхлээд CI хүртэл Git workflow-ийн шаардлагууд файлуудаар бүрэн тохируулсан; branch protection-г GitHub UI дээр идэвхжүүлсэн байх ёстой (локалд шалгах боломжгүй).
- Локал баталгаажуулалт хийхийн тулд Maven-ийг суулгаж дээрх `mvn ...` тушаалуудыг ажиллуул; JaCoCo тайлангаар 100% branch coverage-ийг нягтал.
- Хэрэв CI дээр `mvn` алхамд замын асуудал гарвал runner-д зориулж `working-directory: demo` эсвэл root-д `mvn -f demo/pom.xml ...` хувилбар руу сайжруулж болно.
