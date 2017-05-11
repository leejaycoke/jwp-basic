package core.ref;

import next.model.Question;
import next.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> logger.debug("field: {}", field.getName()));

        Arrays.stream(clazz.getConstructors()).forEach(constructor -> logger.debug("constructor: {}", constructor.getName()));

        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> logger.debug("method: {}", method.getName()));
    }

    @Test
    public void newInstanceWithConstructorArgs() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        Class[] args = new Class[]{String.class, String.class, String.class, String.class};
        Constructor<User> constructor = clazz.getDeclaredConstructor(args);
        User user = constructor.newInstance("1", "1234", "이주현", "asd@asd.com");

        Assert.assertEquals("1", user.getUserId());
        Assert.assertEquals("1234", user.getPassword());
        Assert.assertEquals("이주현", user.getName());
        Assert.assertEquals("asd@asd.com", user.getEmail());
    }

    @Test
    public void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Class<Student> clazz = Student.class;
        Student student = clazz.newInstance();

        Field nameField = clazz.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(student, "이주현");

        Field ageField = clazz.getDeclaredField("age");
        ageField.setAccessible(true);
        ageField.set(student, 30);

        Assert.assertEquals(student.getName(), "이주현");
        Assert.assertEquals(student.getAge(), 30);
    }

}
