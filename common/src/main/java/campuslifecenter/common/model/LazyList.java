package campuslifecenter.common.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

public class LazyList<T> {

    private long total;
    private int page;
    private long size;

    private List<T> items;

    public static <T> LazyList<T> withData(List<T> data, int page, int pageSize, ToLongFunction<T> getScore) {
        if (data.isEmpty()) {
            return new LazyList<T>().setItems(new ArrayList<>());
        }
        Map<T, Long> scoreMap = data.stream()
                .collect(Collectors.toMap(Function.identity(), item -> -getScore.applyAsLong(item)));
        LazyList<T> list = new LazyList<>();
        int start = page * pageSize;
        list.setTotal(data.size());
        list.setPage(page);
        list.setSize(pageSize);
        list.setItems(data
                .stream()
                .sorted(Comparator.comparingLong(scoreMap::get))
                .skip(start)
                .limit(pageSize)
                .collect(Collectors.toList()));
        return list;
    }

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
