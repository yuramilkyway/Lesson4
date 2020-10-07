package ru.innopolis.main;

import java.util.*;

public class HashDemoMap<K, V> implements Map {
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final int MAXIMUM_CAPACITY = 1 << 30;

    private final int initialCapacity;
    private final float loadFactor;
    private int threshold;
    private int size = 0;
    private Node<K, V>[] hashTable;

    /**
     * Создает пустой {@code HashMap} с указанной начальной
     * вместимостью и коэффициентом загрузки.
     * @param initialCapacity начальная емкость
     * @param loadFactor Фактор коэффициента загрузки
     * @ выдает исключение IllegalArgumentException, если начальная емкость отрицательная
     * или коэффициент загрузки неположительный
     */
    public HashDemoMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        this.initialCapacity = initialCapacity;
        hashTable = new Node[initialCapacity];
        threshold = (int) (initialCapacity * loadFactor);
    }

    /**
    * Создает пустую {@code HashMap} с указанным начальным
    * емкость и коэффициент загрузки по умолчанию (0,75).
    *
    * @param initialCapacity - начальная емкость.
    * @ выдает исключение IllegalArgumentException, если начальная емкость отрицательная.
    */
    public HashDemoMap(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        this.initialCapacity = initialCapacity;
        loadFactor = DEFAULT_LOAD_FACTOR;
        hashTable = new Node[initialCapacity];
        threshold = (int) (initialCapacity * loadFactor);

    }

    /**
    * Создает пустую {@code HashMap} с начальной емкостью по умолчанию.
    * (16) и коэффициент нагрузки по умолчанию (0,75).
    */
    public HashDemoMap() {
        this.initialCapacity = DEFAULT_INITIAL_CAPACITY;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        hashTable = new Node[initialCapacity];
        threshold = (int) (initialCapacity * loadFactor);
    }

    /**
     * Узел базового хеш-бункера, используемый для большинства записей.
     */
    private class Node<K, V> {
        private final List<Node<K, V>> nodes;
        private int hash;
        private final K key;
        private V value;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
            nodes = new LinkedList<>();
        }

        private List<Node<K, V>> getNodes() {
            return nodes;
        }

        private int hash() {
            return hashCode() % hashTable.length;
        }

        private K getKey() {
            return key;
        }

        private V getValue() {
            return value;
        }

        private void setValue(V value) {
            this.value = value;
        }
        @Override
        public int hashCode() {
            hash = 31;
            hash = hash * 17 + key.hashCode();
            return hash;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj instanceof Node) {
                Node<K, V> node = (Node) obj;
                return (Objects.equals(key, node.getKey()) &&
                        Objects.equals(value, node.getValue()) ||
                        Objects.equals(hash, node.hashCode()));
            }
            return false;
        }

    }

    /**
     *Связывает указанное значение с указанным ключом на этой карте.
     * Если карта ранее содержала сопоставление для ключа, старый
     * значение заменяется.
     * @param key   ключ, с которым должно быть связано указанное значение
     * @param value значение, которое будет связано с указанным ключом
     * @return результат выполнения
     */
    @Override
    public Object put(Object key, Object value) {
        if (size + 1 >= threshold) {
            threshold *= 2;
            arrayDoubling();
        }

        Node<K, V> newNode = new Node(key, value);
        int index = newNode.hash();

        if (hashTable[index] == null) {
            return simpleAdd(index, newNode);
        }

        List<Node<K, V>> nodeList = hashTable[index].getNodes();

        for (Node<K, V> node : nodeList) {
            if (keyExistButValueNew(node, newNode, (V) value) ||
                    collisionProcessing(node, newNode, nodeList)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Добавляет ноду
     * @param index номер бакета
     * @param newNode нода
     * @return результат выполнения
     */
    private boolean simpleAdd(int index, Node<K, V> newNode) {
        hashTable[index] = new Node<>(null, null);
        hashTable[index].getNodes().add(newNode);
        size++;
        return true;
    }

    private boolean keyExistButValueNew(
            final Node<K, V> nodeFromList,
            final Node<K, V> newNode,
            final V value) {
        if (newNode.getKey().equals(nodeFromList.getKey()) &&
                !newNode.getValue().equals(nodeFromList.getValue())) {
            nodeFromList.setValue(value);
            return true;
        }
        return false;
    }

    private boolean collisionProcessing(
            final Node<K, V> nodeFromList,
            final Node<K, V> newNode,
            final List<Node<K, V>> nodes) {

        if (newNode.hashCode() == nodeFromList.hashCode() &&
                !Objects.equals(newNode.key, nodeFromList.key) &&
                !Objects.equals(newNode.value, nodeFromList.value)) {

            nodes.add(newNode);
            size++;
            return true;
        }
        return false;
    }

    /**
     * Создаем новый лист с размером в 2 раза больше предыдущего и перезаписываем ноды.
     */
    private void arrayDoubling() {
        Node<K, V>[] oldHashTable = hashTable;
        hashTable = new Node[oldHashTable.length * 2];
        size = 0;
        for (Node<K, V> node : oldHashTable) {
            if (node != null) {
                for (Node<K, V> n : node.getNodes()) {
                    put(n.key, n.value);
                }
            }
        }
    }

    /**
     * Возвращает значение, которому сопоставлен указанный ключ, или null,
     * если эта карта не содержит сопоставления для ключа.
     *
     * @param key ключ, связанное значение которого должно быть возвращено
     * @return значение, которому сопоставлен указанный ключ,
     * или null, если эта карта не содержит сопоставления для ключа
     */
    @Override
    public V get(Object key) {
        int index = hash(key);
        if (index < hashTable.length && hashTable[index] != null) {
            List<Node<K, V>> list = hashTable[index].getNodes();
            for (Node<K, V> node : list) {
                if (key.equals(node.getKey())) {
                    return node.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Удаляет сопоставление для ключа с этой карты, если оно присутствует.
     *
     * @param key ключ, отображение которого нужно удалить с карты.
     * @return предыдущее значение, связанное с ключом, или null,
     * если для ключа не было сопоставления.
     */
    @Override
    public Object remove(Object key) {
        int index = hash(key);
        if (hashTable[index] == null) {
            return false;
        }

        if (hashTable[index].getNodes().size() == 1) {
            hashTable[index].getNodes().remove(0);
            size--;
            return true;
        }

        List<Node<K, V>> nodeList = hashTable[index].getNodes();
        for (Node<K, V> node : nodeList) {
            if (key.equals(node.getKey())) {
                nodeList.remove(node);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public void putAll(Map m) {
       Set<K> setM = keySet();

        for (K k: setM) {
            put(k, get(k));
        }
    }


    @Override
    public void clear() {
        Node<K,V>[] tab;
        if ((tab = hashTable) != null && size > 0) {
            size = 0;
            for (int i = 0; i < tab.length; ++i)
                tab[i] = null;
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (int i = 0; i < initialCapacity; i++) {
            if (hashTable[i] != null) {
                List<Node<K, V>> list = hashTable[i].getNodes();
                for (Node<K, V> node : list) {
                    set.add(node.getKey());
                }
            }
        }
        return set;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }

    /**
     * Возвращает true, если эта карта содержит отображение для указанного ключа.
     *
     * @param key - ключ, наличие которого в этой карте необходимо проверить
     * @return истина, если эта карта содержит отображение для указанного ключа
     */
    @Override
    public boolean containsKey(Object key) {
        int index = hash(key);
        if (index < hashTable.length &&
                hashTable[index] != null) {

            List<Node<K, V>> list = hashTable[index].getNodes();
            for (Node<K, V> node : list) {
                if (key.equals(node.getKey())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        Node<K, V>[] tab;
        V v;
        if ((tab = hashTable) != null && size > 0) {
            for (Node<K, V> elemNode : tab) {
                try {
                    for (Node<K, V> node : elemNode.nodes) {
                        if ((v = node.value) == value ||
                                (value != null && value.equals(v)))
                            return true;
                    }
                } catch (NullPointerException e) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Возвращает количество сопоставлений "ключ-значение" на этой карте.
     * Если карта содержит более элементов Integer.MAX_VALUE, возвращает Integer.MAX_VALUE.
     *
     * @return количество сопоставлений "ключ-значение" на этой карте
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int hash(final Object key) {
        int hash = 31;
        hash = hash * 17 + key.hashCode();
        return hash % hashTable.length;
    }
}
