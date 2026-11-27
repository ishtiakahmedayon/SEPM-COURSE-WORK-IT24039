# CampusBoot (IT24039)

Custom annotations + reflection mini-framework. Java 11+, no external libraries.

## Zip structure

```
IT24039_CampusBoot.zip
├── Component.java
├── Value.java
├── Range.java
├── Route.java
├── Container.java
├── Validator.java
├── Router.java
├── GradeController.java
├── ReportPrinter.java
├── Student.java
├── Main.java
└── answers.txt
```

## Run

```
javac *.java
java Main
```

## Parts

| Part | Files |
|---|---|
| A. Annotations | Component, Value, Range, Route |
| B. Container | Container |
| C. Validator | Validator |
| D. Router | Router |
| E. Answers | answers.txt |
| Starter (unmodified) | GradeController, ReportPrinter, Student, Main (imports added) |

Note: the starter uses `@Route("/hello")`, which requires an element named `value`. `Route` therefore declares both `value()` and `path()` (default `""`); `Router` uses `path()` if set, else `value()`.

## Output

```
[Container] Registered: gradeController (GradeController)
[Container] Skipped: ReportPrinter (no @Component)
[Container] Injected: app.name -> GradeController.appName
[Container] Injected: app.passMark -> GradeController.passMark
--- Validation ---
VIOLATION: score - Score must be between 0 and 100 (actual: 120)
OK
VIOLATION: semester - Value out of range (actual: 15)
--- Routing ---
[Router] /hello -> Welcome to CampusBoot
[Router] /status -> Pass mark is 40
[Router] /unknown -> 404 Not Found: /unknown
```

## Code

### Component.java

```java
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    String value() default "";
}
```

### Value.java

```java
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
    String value();
}
```

### Range.java

```java
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {
    int min();
    int max();
    String message() default "Value out of range";
}
```

### Route.java

```java
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Route {
    String value() default "";
    String path() default "";
}
```

### Container.java

```java
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Container {
    private final Map<String, Object> beans = new LinkedHashMap<>();

    public void scan(Class<?>... classes) {
        for (Class<?> c : classes) {
            Component comp = c.getAnnotation(Component.class);
            if (comp == null) {
                System.out.println("[Container] Skipped: " + c.getSimpleName() + " (no @Component)");
                continue;
            }
            String name = comp.value();
            if (name.isEmpty()) {
                String s = c.getSimpleName();
                name = Character.toLowerCase(s.charAt(0)) + s.substring(1);
            }
            try {
                Object bean = c.getDeclaredConstructor().newInstance();
                beans.put(name, bean);
                System.out.println("[Container] Registered: " + name + " (" + c.getSimpleName() + ")");
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Cannot create bean: " + c.getName(), e);
            }
        }
    }

    public void injectValues(Map<String, String> config) {
        for (Object bean : beans.values()) {
            for (Field f : bean.getClass().getDeclaredFields()) {
                Value v = f.getAnnotation(Value.class);
                if (v == null) continue;
                String raw = config.get(v.value());
                if (raw == null) continue;
                try {
                    f.setAccessible(true);
                    Class<?> t = f.getType();
                    if (t == int.class || t == Integer.class) {
                        f.set(bean, Integer.parseInt(raw.trim()));
                    } else if (t == String.class) {
                        f.set(bean, raw);
                    } else {
                        continue;
                    }
                    System.out.println("[Container] Injected: " + v.value() + " -> "
                            + bean.getClass().getSimpleName() + "." + f.getName());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public Object getBean(String name) {
        return beans.get(name);
    }

    public Collection<Object> getBeans() {
        return beans.values();
    }
}
```

### Validator.java

```java
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Validator {
    public static List<String> validate(Object obj) {
        List<String> violations = new ArrayList<>();
        for (Field f : obj.getClass().getDeclaredFields()) {
            Range r = f.getAnnotation(Range.class);
            if (r == null || f.getType() != int.class) continue;
            try {
                f.setAccessible(true);
                int val = f.getInt(obj);
                if (val < r.min() || val > r.max()) {
                    violations.add(f.getName() + " - " + r.message() + " (actual: " + val + ")");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return violations;
    }
}
```

### Router.java

```java
import java.lang.reflect.Method;

public class Router {
    public static String dispatch(Container c, String path) {
        for (Object bean : c.getBeans()) {
            for (Method m : bean.getClass().getDeclaredMethods()) {
                Route r = m.getAnnotation(Route.class);
                if (r != null && (r.path().isEmpty() ? r.value() : r.path()).equals(path)) {
                    try {
                        m.setAccessible(true);
                        return (String) m.invoke(bean);
                    } catch (ReflectiveOperationException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return "404 Not Found: " + path;
    }
}
```

### GradeController.java

```java
@Component("gradeController")
public class GradeController {
    @Value("app.name")     private String appName;
    @Value("app.passMark") private int passMark;

    @Route("/hello")
    public String hello() { return "Welcome to " + appName; }

    @Route("/status")
    public String status() { return "Pass mark is " + passMark; }
}
```

### ReportPrinter.java

```java
public class ReportPrinter { }
```

### Student.java

```java
public class Student {
    @Range(min = 0, max = 100, message = "Score must be between 0 and 100")
    private int score;

    @Range(min = 1, max = 12)
    private int semester;

    public Student(int score, int semester) { this.score = score; this.semester = semester; }
}
```

### Main.java

```java
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String,String> config = new HashMap<>();
        config.put("app.name", "CampusBoot");
        config.put("app.passMark", "40");

        Container container = new Container();
        container.scan(GradeController.class, ReportPrinter.class);
        container.injectValues(config);

        System.out.println("--- Validation ---");
        for (Student s : List.of(new Student(120, 5), new Student(85, 5), new Student(70, 15))) {
            List<String> errors = Validator.validate(s);
            if (errors.isEmpty()) System.out.println("OK");
            else errors.forEach(e -> System.out.println("VIOLATION: " + e));
        }

        System.out.println("--- Routing ---");
        for (String p : new String[]{"/hello", "/status", "/unknown"})
            System.out.println("[Router] " + p + " -> " + Router.dispatch(container, p));
    }
}
```

## answers.txt

1. Without @Retention(RetentionPolicy.RUNTIME), the annotations default to CLASS retention. They stay in the .class file but are not available to the JVM at runtime, so getAnnotation() returns null. The container would skip every class, nothing would be injected or validated, and every route would return 404.

2. @Component maps to Spring's @Component, @Value to Spring's @Value, @Range to Bean Validation's @Min/@Max (or @Range in Hibernate Validator), and @Route to @GetMapping/@RequestMapping. Real Spring also does dependency injection between beans (@Autowired), classpath component scanning, bean scopes/lifecycle, and AOP proxies, which CampusBoot does not.

3. setAccessible(true) is powerful because it lets code read and write private fields and call private methods, which makes injection and validation possible without setters. It is risky because it breaks encapsulation, can bypass invariants and security checks, and ties code to internal implementation details that may change. Modern Java modules can also block it.
