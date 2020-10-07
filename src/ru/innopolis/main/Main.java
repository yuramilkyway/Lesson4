package ru.innopolis.main;

import java.util.Set;

public class Main {
    public static void main(String[] args) {

        HashDemoMap<Integer, String> hashDemoMap = new HashDemoMap(10);
        hashDemoMap.put(1, "cat");
        hashDemoMap.put(2, "dog");
        hashDemoMap.put(9, "yyy");
        hashDemoMap.put(5, "gggg");

        System.out.println(hashDemoMap.containsValue("do"));
        System.out.println("=========================");
        Set<Integer> set = hashDemoMap.keySet();
        for (Integer s : set) {
            System.out.println(s);
        }
        System.out.println("=========================");

        HashDemoMap<Integer, String> h = new HashDemoMap<>();
        h.put(11, "11");
        h.put(22, "22");
        h.put(33, "33");

        hashDemoMap.putAll(h);
        System.out.println(hashDemoMap.get(11));//не работает
    }
}
