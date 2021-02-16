package campuslifecenter.common.component;

import brave.ScopedSpan;
import brave.Span;
import brave.Tracer;
import brave.propagation.CurrentTraceContext;

import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class TracerUtil {

    private Tracer tracer;

    public TracerUtil(Tracer tracer) {
        this.tracer = tracer;
    }

    public static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static ExecutorService threadPoolExecutor;

    static {
        CurrentTraceContext currentTraceContext = CurrentTraceContext.Default.create();
        threadPoolExecutor = currentTraceContext.executorService(new ThreadPoolExecutor(
                THREAD_POOL_SIZE, THREAD_POOL_SIZE * 2,
                10, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(256),
                new ThreadPoolExecutor.CallerRunsPolicy()));
    }

    public Span getSpan() {
        return tracer.currentSpan();
    }

    public <T> T newSpan(String name, Function<ScopedSpan, T> function) {
        ScopedSpan span = tracer.startScopedSpan(name);
        span.annotate("start");
        try {
            return function.apply(span);
        } catch (RuntimeException e) {
            span.annotate("error");
            span.error(e);
            throw e;
        } finally {
            span.annotate("finish");
            span.finish();
        }
    }

    public <T> T newSpanRet(String name, Function<ScopedSpan, T> function) {
        return newSpan(name, function);
    }

    public void newSpan(String name, Consumer<ScopedSpan> consumer) {
        ScopedSpan span = tracer.startScopedSpan(name);
        span.annotate("start");
        try {
            consumer.accept(span);
        } catch (RuntimeException e) {
            span.annotate("error");
            span.error(e);
            throw e;
        } finally {
            span.annotate("finish");
            span.finish();
        }
    }

    public void newSpanNRet(String name, Consumer<ScopedSpan> consumer) {
        newSpan(name, consumer);
    }

    public void newSpanAsync(String name, Consumer<ScopedSpan> consumer) {
        threadPoolExecutor.execute(() -> newSpan(name, consumer));
    }

    public void newSpanAsync(String name, CountDownLatch countDownLatch, Consumer<ScopedSpan> consumer) {
        try {
            newSpanAsync(name, consumer);
        } finally {
            countDownLatch.countDown();
        }
    }

}
