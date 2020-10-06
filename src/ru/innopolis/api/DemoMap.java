package ru.innopolis.api;

public interface DemoMap {
    /**
     * Связывает указанное значение с указанным ключом на этой карте.
     * Если карта ранее содержала сопоставление для ключа, старое значение заменяется указанным значением.
     *
     * @param key   ключ, с которым должно быть связано указанное значение
     * @param value значение, которое будет связано с указанным ключом
     * @return предыдущее значение, связанное с ключом,
     * или null, если для ключа не было сопоставления.
     * (Возврат null также может указывать на то,
     * что карта ранее связала null с ключом,
     * если реализация поддерживает нулевые значения.)
     */
    Object put(Object key, Object value);

    /**
     * Возвращает значение, которому сопоставлен указанный ключ, или null,
     * если эта карта не содержит сопоставления для ключа.
     *
     * @param key ключ, связанное значение которого должно быть возвращено
     * @return значение, которому сопоставлен указанный ключ,
     * или null, если эта карта не содержит сопоставления для ключа
     */
    Object get(Object key);

    /**
     * Удаляет сопоставление для ключа с этой карты, если оно присутствует.
     *
     * @param key ключ, отображение которого нужно удалить с карты.
     * @return предыдущее значение, связанное с ключом, или null,
     * если для ключа не было сопоставления.
     */
    Object remove(Object key);

    /**
     * Возвращает true, если эта карта содержит отображение для указанного ключа.
     *
     * @param key - ключ, наличие которого в этой карте необходимо проверить
     * @return истина, если эта карта содержит отображение для указанного ключа
     */
    boolean containsKey(Object key);

    /**
     * Возвращает количество сопоставлений "ключ-значение" на этой карте.
     * Если карта содержит более элементов Integer.MAX_VALUE, возвращает Integer.MAX_VALUE.
     *
     * @return количество сопоставлений "ключ-значение" на этой карте
     */
    int size();
}