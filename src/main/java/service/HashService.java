package service;

public class HashService<K, V> {
	private CustomHashMap<String, CustomHashMap<Object, Object>> customMaps = new CustomHashMap<String, CustomHashMap<Object, Object>>();

	public void create(String id) {
		customMaps.put(id, new CustomHashMap<Object, Object>());
	}

	public void delete(String id) {
		customMaps.remove(id);
	}

	public void put(String id, K key, V value) {
		CustomHashMap<Object, Object> mapInstance = customMaps.get(id);
		mapInstance.put(key, value);
	}

	public Object get(String id, K key) {
		CustomHashMap<Object, Object> mapInstance = customMaps.get(id);
		return mapInstance.get(key);

	}
	
	public void remove(String id, K key){
		CustomHashMap<Object, Object> mapInstance = customMaps.get(id);
		mapInstance.remove(key);
	}
}
