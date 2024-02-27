import java.util.HashMap;
import java.util.Map;

interface Cachable<K,V> {

    /**
     *
     * @param k
     * @return
     */
    V get(K k);

    /**
     *
     * @param k
     * @param v
     */
    void put(K k, V v);
}

abstract class BaseCache<K,V> implements Cachable<K,V> {

    protected Map<K, CacheEntryConnector<K,V>> cacheMapper = new HashMap<>();
    protected final int MAX_CACHE_SIZE = 4;
    protected int lastPositionIndex = 0;

    protected CacheEntryConnector<K,V> head;
    protected CacheEntryConnector<K,V> tail;

    protected void updateTail(CacheEntryConnector<K,V> cacheEntryConnector) {
        cacheEntryConnector.setPrev(tail);	// setting the inserted entry as tail
        tail.setNext(cacheEntryConnector);				// deallocating pointer to the next.
        tail = cacheEntryConnector;						// updating tail with a new entry
    }

    protected void printCache() {

//		System.out.println("Cache map");
//		for (K key : cacheMapper.keySet()) {
//			System.out.println("key: " + key + ", value: " + cacheMapper.get(key).getEntry());
//		}

        CacheEntryConnector<K,V> cacheEntryConnectorHead = head;
        CacheEntryConnector<K,V> cacheEntryConnectorTail = tail;
        System.out.println("\nCache Queue");
        while (cacheEntryConnectorHead != cacheEntryConnectorTail.getNext()) {
            System.out.println("Entry: " + cacheEntryConnectorHead.getEntry());
            if (cacheEntryConnectorHead.getPrev() != null)
                System.out.println("\t Prev Entry: " + cacheEntryConnectorHead.getPrev().getEntry());
            if (cacheEntryConnectorHead.getNext() != null)
                System.out.println("\t Next Entry: " + cacheEntryConnectorHead.getNext().getEntry());
            cacheEntryConnectorHead = cacheEntryConnectorHead.getNext();
        }
    }
}

public class LruCache<K,V> extends BaseCache<K,V> {

    public static void main(String[] args) {

        LruCache<Integer, String> cacheHandler = new LruCache<>();
        cacheHandler.put(1, "Entry1");
        cacheHandler.put(2, "Entry2");
        cacheHandler.put(3, "Entry3");
        cacheHandler.put(4, "Entry4");
        cacheHandler.put(5, "Entry5");
        cacheHandler.put(6, "Entry6");
        cacheHandler.get(3);
        cacheHandler.put(7, "Entry7");
        cacheHandler.get(5);
        cacheHandler.get(3);
        cacheHandler.put(8, "Entry8");
        cacheHandler.get(2);
        cacheHandler.put(9, "Entry9");

        cacheHandler.printCache();
        return;
    }

    @Override
    public V get(K k) {

        if (lastPositionIndex <= 0) {
            System.out.println("Nothing to get, cache is empty.");
            return null;
        }

        if (!cacheMapper.containsKey(k)) {
            System.out.println("Get failed, the key: " + k + " is not in cache.");
            return null;
        }

        CacheEntryConnector<K,V> cacheEntryConnector = cacheMapper.get(k);
        System.out.println("Get is succeeded, the returned entry: " + cacheEntryConnector.getEntry());

        // updating pointers of prev and next pointers of
        // a new least recently used cache entry
        CacheEntryConnector<K,V> cacheEntryConnectorPrev = cacheEntryConnector.getPrev();
//		System.out.println("The Prev entry: " + cacheEntryConnectorPrev.getEntry());

        CacheEntryConnector<K,V> cacheEntryConnectorNext = cacheEntryConnector.getNext();
//		System.out.println("The Next entry: " + cacheEntryConnectorNext.getEntry());

        if (cacheEntryConnector.getPrev() == null || cacheEntryConnector.getPrev().getEntry() == null) {
            head = cacheEntryConnector.getNext();	// updating head with a next entry, before putting the current entry in the end of queue
//			System.out.println("The head entry updated to : " + head.getEntry());
        } else {
            cacheEntryConnectorPrev.setNext(cacheEntryConnectorNext);
            cacheEntryConnectorNext.setPrev(cacheEntryConnectorPrev);
        }

        // updating tail with a new least recently used cache entry
        updateTail(cacheEntryConnector);

        V valueToGet = cacheEntryConnector.getEntry().getValue();

        return valueToGet;
    }

    @Override
    public void put(K k, V v) {

        CacheEntryConnector<K,V> cacheEntryConnector = new CacheEntryConnector<>(new Entry(k,v));

        if (lastPositionIndex > MAX_CACHE_SIZE) {
            // remove the least recently used and update pointers
            System.out.println("Cache is full, removing the least recently used entry: " + head.getEntry());
            System.out.println("The new least recently used entry: " + head.getNext().getEntry());
            cacheMapper.remove(head.getEntry().getKey());
            head.setPrev(null);
            head = head.getNext();
            lastPositionIndex--;
        }

        if (tail == null) {
            tail = cacheEntryConnector;
        } else {
            updateTail(cacheEntryConnector);					// updating tail with a new entry
        }

        if (lastPositionIndex == 0) {
            head = cacheEntryConnector;
        }

        lastPositionIndex++;

        cacheMapper.put(k, cacheEntryConnector);		// updating map with a new entry
    }
}

class CacheEntryConnector<K,V> {

    private Entry<K,V> entry;
    private CacheEntryConnector<K,V> prev;
    private CacheEntryConnector<K,V> next;

    public CacheEntryConnector() {}

    public CacheEntryConnector(Entry<K,V> entry) {
        this.entry = entry;
        this.prev = new CacheEntryConnector<>();
        this.next = new CacheEntryConnector<>();
    }

    public CacheEntryConnector(Entry<K, V> entry, CacheEntryConnector<K,V> prev, CacheEntryConnector<K,V> next) {
        this.entry = entry;
        this.prev = prev;
        this.next = next;
    }

    @Override
    public String toString() {
        return "CacheEntryConnector [entry=" + entry + ", prev=" + prev + ", next=" + next + "]";
    }

    /**
     * @return the entry
     */
    public Entry<K, V> getEntry() {
        return entry;
    }

    /**
     * @param entry the entry to set
     */
    public void setEntry(Entry<K, V> entry) {
        this.entry = entry;
    }

    /**
     * @return the prev
     */
    public CacheEntryConnector<K,V> getPrev() {
        return prev;
    }

    /**
     * @param prev the prev to set
     */
    public void setPrev(CacheEntryConnector<K,V> prev) {
        this.prev = prev;
    }

    /**
     * @return the next
     */
    public CacheEntryConnector<K,V> getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(CacheEntryConnector<K,V> next) {
        this.next = next;
    }
}

class Entry<K,V> {

    private K key;
    private V value;

    /**
     * @param key
     * @param value
     */
    public Entry(K key, V value) {
        super();
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "CacheEntry [key=" + key + ", value=" + value + "]";
    }

    /**
     * @return the key
     */
    public K getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public V getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(V value) {
        this.value = value;
    }
}
