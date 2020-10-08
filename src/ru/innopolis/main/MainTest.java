package ru.innopolis.main;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTest {
    private final HashDemoMap expected = new HashDemoMap();
    private final Map actual = new HashMap();
    private final Map map = new HashMap();


    @Before
    public void setUp() {
        //Создаем тестовые данные
        expected.put(1, "ana");
        expected.put(5, "baa");
        expected.put(3, "yyy");

        //Создаем данные для сравнения
        actual.put(1, "ana");
        actual.put(5, "baa");
        actual.put(3, "yyy");

        map.put(11, "11");
        map.put(22, "22");
        map.put(33, "33");
    }

    @Test
    public void checkingTheMethodsPutGet() {
        //Запускаем тест
        //Провяем метод put() и get()
        Assert.assertEquals(actual.get(1), expected.get(1));

        //Проверяем обновление элементов
        expected.put(5, "gggg");
        actual.put(5, "gggg");
        Assert.assertEquals(actual.get(5), expected.get(5));
    }

    @Test
    public void checkingTheMethodRemove() {
        //Проверяем метод remove()
        expected.remove(5);
        actual.remove(5);
        Assert.assertEquals(actual.get(5), expected.get(5));
    }

    @Test
    public void checkingTheMethodSize() {
        //Проверяем метод size()
        Assert.assertEquals(actual.size(), expected.size());
    }

    @Test
    public void checkingTheMethodContainsKey() {
        //Проверяем метод containsKey()
        Assert.assertEquals(actual.containsKey(1), expected.containsKey(1));
    }

    @Test
    public void checkingTheMethodContainsValue() {
        //Проверяем метод containsValue()
        Assert.assertEquals(actual.containsValue("ana"), expected.containsValue("ana"));
    }

    @Test
    public void checkingTheKeySet() {
        //Проверяем метод keySet()
        Assert.assertEquals(actual.keySet(), expected.keySet());
    }

    @Test
    public void checkingTheEntrySet() {
        //Проверяем метод entrySet()
        Assert.assertEquals(actual.entrySet(), expected.entrySet());
    }

    @Test
    public void checkingThePullAll() {
        //Проверяем метод putAll()
        actual.putAll(map);
        expected.putAll(map);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void checkingTheClear() {
        //Проверяем метод clear()
        actual.clear();
        expected.clear();
        Assert.assertEquals(actual, expected);
    }
}