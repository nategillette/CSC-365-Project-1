public class HashTable {
    static class Node
    {
        String key;
        int value;
        Node next;
        Node (String k, int v, Node n)
        {
            key = k;
            value = v;
            next = n;
        }
    }

    Node[] table = new Node[20];
    int count = 0;
    boolean first_node = true;
    Node first;

  public int get(String k)
    {
        int h = k.hashCode();
        int i = h & (table.length-1);
        for (Node p = table[i]; p != null; p = p.next)
        {
            if (k.equals(p.key))
            {
                return p.value;
            }
        }

        return 0;
    }

    public boolean contains(String key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key)) {
                return true;
            }
        }
        return false;
    }

   public void add(String k)
    {
        int h = k.hashCode();
        int i = h & (table.length-1);
        for (Node p = table[i]; p != null; p = p.next)
        {
            if (k.contentEquals(p.key))
            {
                ++ p.value;
                return;
            }
        }

        table[i] = new Node (k, 1, table[i]);
        if (first_node) {
            first = table[i];
            first_node = false;
    }

        if (++count > (0.75 * table.length))
        {
            resize();
        }
    }

   public void resize()
    {
        int newSize = 2 * table.length;
        int oldSize = table.length;
        Node[] oldHash = table;
        table = new Node[newSize];

        for (int i = 0; i < oldSize; i++)
        {
            while(oldHash[i] != null)
            {
                rehash(oldHash[i].key, oldHash[i].value);
                oldHash[i] = oldHash[i].next;
            }
        }
    }

   public void rehash(String key, int value)
    {
        int h = key.hashCode();
        int i = h & (table.length-1);
        table[i] = new Node (key, value, table[i]);
    }
}
