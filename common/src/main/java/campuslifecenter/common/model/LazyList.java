package campuslifecenter.common.model;

import java.util.List;
import java.util.function.Consumer;

public class LazyList<T> {

    private long total;
    private int page;
    private long size;

    private List<T> items;

    public void forEach(Consumer<T> consumer) {
        items.forEach(consumer);
    }

    public int size() {
        return items.size();
    }

    public long getTotal() {
        return total;
    }

    public LazyList<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public int getPage() {
        return page;
    }

    public LazyList<T> setPage(int page) {
        this.page = page;
        return this;
    }

    public long getSize() {
        return size;
    }

    public LazyList<T> setSize(long size) {
        this.size = size;
        return this;
    }

    public List<T> getItems() {
        return items;
    }

    public LazyList<T> setItems(List<T> items) {
        this.items = items;
        return this;
    }
}
