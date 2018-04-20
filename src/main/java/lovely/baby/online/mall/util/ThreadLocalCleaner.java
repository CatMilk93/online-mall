package lovely.baby.online.mall.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ThreadLocalCleaner {

    private final ConcurrentHashMap<String, ThreadLocal<?>> threadLocals = new ConcurrentHashMap<>();

    public <T> ThreadLocal<T> register(ThreadLocal<T> threadLocal) {
        threadLocals.put(UUID.randomUUID().toString(), checkNotNull(threadLocal, "ThreadLocal对象不能为null"));
        return threadLocal;
    }

    public void clean() {
        if (threadLocals.isEmpty()) {
            return;
        }
        for (Entry<String, ThreadLocal<?>> entry : threadLocals.entrySet()) {
            entry.getValue().remove();
        }
    }
}
