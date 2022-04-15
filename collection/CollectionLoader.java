package ru.itmo.lab.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для подгрузки коллекции
 * @param <K>
 * @param <V>
 */
public class CollectionLoader<K,V> {
    Map<K,V> loadedCollection = new HashMap<>();
    public void addElementToCollection(K key,V object){
        loadedCollection.put(key,object);
    }
    public Map<K,V> getCollection(){
        return loadedCollection;
    }


}
