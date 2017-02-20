package model;


class KeyValuePair<K, V> {
	K key;
	V value;
	KeyValuePair<K, V> next = null;

	public KeyValuePair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
		this.next = null;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public KeyValuePair<K, V> getNext() {
		return next;
	}

	public void setNext(KeyValuePair<K, V> next) {
		this.next = next;
	}

}