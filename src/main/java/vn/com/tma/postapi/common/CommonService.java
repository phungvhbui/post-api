package vn.com.tma.postapi.common;

import java.util.List;

public interface CommonService<T> {
    List<T> getAll();
    T get(long id);
    T add(T object);
    T update(long id, T object);
    void delete(long id);
}
