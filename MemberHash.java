package CHC5223;

import java.util.Objects;

public class MemberHash implements IMemberDB {
    // 此文件为 500 个信息  所以可以设为 1 << 10  因为 1 << 10 * 0.75 > 500  此处设为 1 << 4 测试 reSize 函数
    myHashMap hash = new myHashMap(1 << 4, 0.75f);

    public MemberHash() {
        System.out.println("-- Hash Table --");
    }

    @Override
    public void clearDB() {
        hash.clear();
    }

    @Override
    public boolean containsName(String name) {
        return hash.containsKey(name);
    }

    @Override
    public Member get(String name) {
        String affiliation;
        if (containsName(name))
        {
            affiliation = hash.get(name);
        }
        else return null;
        return new Member(name, affiliation);
    }

    @Override
    public int size() {
        return hash.size();
    }

    @Override
    public boolean isEmpty() {
        return hash.isEmpty();
    }

    @Override
    public Member put(Member member) {
        if (containsName(member.getName()))
        {
            hash.put(member.getName(), member.getAffiliation());
            return member;
        }else
        {
            hash.put(member.getName(), member.getAffiliation());
            return null;
        }
    }

    @Override
    public Member remove(String name) {
        String affiliation;
        if (containsName(name))
        {
            System.out.println("Before delete:");
            affiliation = hash.get(name);
            Member member = new Member(name, affiliation);
            System.out.println("After delete:");
            hash.remove(name, affiliation);
            return member;
        } else return null;
    }

    @Override
    public void displayDB() {
        hash.display();
    }
}


class myHashMap {
    // 默认初始化长度 为 2 的幂
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    // 数组最长长度 1 << 30
    private static final int MAXIMUM_CAPACITY = 1 << 30;


    /* --------------- Fields ------------------- */

    // map 元素个数
    private transient int size;

    // map 操作数
    transient int modCount;

    float loadFactor;
    int threshold;
    int capacity;
    Node[] table = new Node[DEFAULT_INITIAL_CAPACITY];


    // ---------------------------------------- constructor ------------------------------------------------

    public myHashMap(int capacity, float loadFactor) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal Initial Capacity" + capacity);
        }
        if (capacity > MAXIMUM_CAPACITY) {
            capacity = MAXIMUM_CAPACITY;
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal LoadFactory" + loadFactor);
        }
        this.size = 0;
        this.modCount = 0;
        this.loadFactor = loadFactor;
        this.capacity = capacity;
        this.threshold = (int) (capacity * loadFactor);
    }


    // ------------------------------------- Node ----------------------------------------------------
    static class Node {
        final int hash;
        final String key;
        String value;
        Node next;

        public Node(int hash, String key, String value, Node  next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public String getValue() {
            return this.value;
        }

        public String getKey() {
            return this.key;
        }

        public final String toString() {
            return this.key + "=" + this.value;
        }

        public final int hashCode() {
            return myHashCode(key) ^ myHashCode(value);
        }

    }


    // ------------------------------------- OutsideMethod -------------------------------------

    public String put(String k, String v) {
        return putVal(hash(k), k, v);
    }

    public String get(String k) {
        System.out.println("name: " + k + " hash: " + hash(k) + " threshold: " + threshold + " size: " + size() + " loadFactor: " + size() * 1.0 / threshold * loadFactor );
        return Objects.requireNonNull(getNode(hash(k), k)).value;
    }

    public Node remove(String k, String v) {
        return removeNode(hash(k), k, v);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(String k) {
        return getNode(hash(k), k) != null;
    }

    public void display() {
        int n = 1;
        int num = 0;
        while (n != size + 1){
            Node temp = table[num ++];
            while (temp != null) {
                System.out.println("N-"+ n +++ ": name = " + temp.getKey() + "\naffiliation = " + temp.getValue() + "\n");
                temp = temp.next;
            }
        }
    }

    public void clear() {
        int n = 1;
        int num = 0;
        while (n != size + 1){
            Node temp = table[num ++];
            while (temp != null) {
                remove(temp.key, temp.value);
                temp = temp.next;
            }
        }
    }


    // ------------------------ method ----------------------

    public static int myHashCode(String value) {
        int h = 0;
        if (value.length() > 0) {
            for (int i = 0; i < value.length(); i++) {
                h = 31 * h + value.charAt(i);
            }
        }
        return h;
    }

    final Node getNode(int hash, String key) {
        Node[] tab = table;
        int n;
        Node p;
        if (tab == null || (n = tab.length) == 0) {
            return null;
        }
        if ((p = tab[indexFor(hash, n)]) == null) {
            return null;
        } else if (p.hash == hash && (Objects.equals(key, p.key))) {
            return p;
        } else {
            Node  e;
            if ((e = p.next) != null) {
                do {
                    if (e.hash == hash && (Objects.equals(key, e.key))) {
                        return e;
                    }
                } while ((e = e.next) != null);
            }

        }
        return null;
    }

    static int hash(String key) {
        int h;
        return key == null ? 0 : (h = myHashCode(key)) ^ (h >>> 16);
    }

    public String putVal(int hash, String key, String value) {
        int i;
        Node[] tab = table;
        Node p;
        int n;
        if (tab == null || (n = tab.length) == 0) {
            n = (tab = resize()).length;
        }
        if ((p = tab[i = (hash & (n - 1))]) == null) {
            tab[i] = new Node(hash, key, value, null);
        } else {
            Node e;
            String k;
            if (p.hash == hash && (Objects.equals(k = p.key, key) || k.equals(key))) {
                e = p;
            } else {
                while (true) {
                    if ((e = p.next) == null) {
                        p.next = new Node(hash, key, value, null);
                        break;
                    }
                    if (e.hash == hash && Objects.equals(e.key, key)) {
                        break;
                    }
                    p = e;
                }
            }
            if (e != null) {
                String oldVal = e.value;
                e.value = value;
                System.out.println("name: " + key + " hash: " + hash + " threshold: " + threshold + " size: " + size() + " loadFactor: " + size() * 1.0 / threshold * loadFactor );
                return oldVal;
            }
        }
        this.table = tab;
        ++modCount;
        if (++size > threshold) {
            resize();
        }
        System.out.println("name: " + key + " hash: " + hash + " threshold: " + threshold + " size: " + size() + " loadFactor: " + size() * 1.0 / threshold * loadFactor );
        return null;
    }


    // -------------- resize ------------ // 或根据大小直接设置 DEFAULT_INITIAL_CAPACITY

    final Node[] resize() {
        Node[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap > MAXIMUM_CAPACITY) {
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap > DEFAULT_INITIAL_CAPACITY) {
                newCap = oldCap << 1;
                newThr = (int) (oldCap * loadFactor);
            }
        } else if (oldThr == 0) {
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (loadFactor * DEFAULT_INITIAL_CAPACITY);
        } else {
            newCap = oldThr;
        }
        threshold = newThr;
        Node[] newTab = new Node[newCap];
        assert oldTab != null;
        transfer(oldTab, newTab);
        this.table = newTab;
        return newTab;
    }

    final void transfer(Node[] oldTab, Node[] newTab) {
        int oldCap = oldTab.length;
        int newCap = newTab.length;
        for (int i = 0; i < oldTab.length; i++) {
            Node e = oldTab[i];
            if (e != null) {
                oldTab[i] = null;
                if (e.next == null) {
                    newTab[e.hash & (newCap - 1)] = e;
                } else {
                    Node loHead = null, loTail = null;
                    Node hiHead = null, hiTail = null;
                    while (true) {
                        if (indexFor(e.hash, newCap) == 0) {
                            if (loTail == null) {
                                loHead = e;
                            } else {
                                loTail.next = e;
                            }
                            loTail = e;
                        } else {
                            if (hiTail == null) {
                                hiHead = e;
                            } else {
                                hiTail.next = e;
                            }
                            hiTail = e;
                        }
                        if (e.next == null) {
                            break;
                        }
                        e = e.next;
                    }
                    if (loTail != null) {
                        loTail.next = null;
                        newTab[i] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTab[i + oldCap] = hiHead;
                    }
                }
            }
        }
    }

    final int indexFor(int h, int length) {
        return h & (length - 1);
    }

    final Node removeNode(int hash, String k, String v) {
        Node[] tab = table;
        int n;
        int index;
        Node p;
        if (tab == null || (n = tab.length) == 0) {
            return null;
        }
        if ((p = tab[index = indexFor(hash, n)]) == null) {
            return null;
        } else {
            Node e;
            Node node = null;
            String key = p.key;
            if (p.hash == hash && k.equals(key) || key.equals(k)) {
                node = p;
            } else if ((e = p.next) != null) {
                do {
                    if (e.hash == hash && (Objects.equals(k, e.key))) {
                        node = e;
                        break;
                    }
                    p = e;
                } while ((e = e.next) != null);
            }
            if (node != null && Objects.equals(node.value, v)) {
                if (node == p) {
                    tab[index] = node.next;
                } else {
                    p.next = node.next;
                }
                ++modCount;
                --size;
                System.out.println("name: " + key + " hash: " + hash + " threshold: " + threshold + " size: " + size() + " loadFactor: " + size() * 1.0 / threshold * loadFactor );
                return node;
            }
        }
        return null;
    }

}
