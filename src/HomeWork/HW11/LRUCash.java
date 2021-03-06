package HW11;
import java.util.Map;
import java.util.HashMap;

    class LRUCache {
        class Node {
            int key;
            int val;
            Node prev;
            Node next;

            public Node(int k, int v) {
                key = k;
                val = v;
            }
        }

        Node head;
        Node tail;
        Map<Integer, Node> map;
        int capacity;
        int size;

        public LRUCache(int capacity) {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            map = new HashMap<>();
            head.next = tail;
            tail.prev = head;
            this.capacity = capacity;
            size = 0;
        }

        public int get(int key) {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                remove(key);
                addHead(key, node.val);
                return node.val;
            } else
                return -1;
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                remove(key);
                addHead(key, value);
            } else {
                addHead(key, value);
            }
        }

        private void remove(int key) {
            Node cur = map.get(key);
            Node prev = cur.prev;
            Node next = cur.next;
            prev.next = next;
            next.prev = prev;
            size--;
            map.remove(key);
        }

        private void addHead(int key, int val) {
            Node node = new Node(key, val);
            Node next = head.next;
            head.next = node;
            node.next = next;
            next.prev = node;
            node.prev = head;
            map.put(key, node);
            size++;
            if (size > capacity) {
                Node preTail = tail.prev;
                remove(preTail.key);
            }
        }

        public static void main(String[] args) {
            LRUCache cache = new LRUCache( 2 /* capacity */ );

            cache.put(1, 1);
            cache.put(2, 2);
            cache.get(1);       // returns 1
            cache.put(3, 3);    // evicts key 2
            cache.get(2);       // returns -1 (not found)
            cache.put(4, 4);    // evicts key 1
            cache.get(1);       // returns -1 (not found)
            cache.get(3);       // returns 3
            cache.get(4);       // returns 4

//            System.out.println(cache.get(any number));
        }
    }
