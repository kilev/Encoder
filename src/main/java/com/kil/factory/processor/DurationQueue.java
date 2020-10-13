package com.kil.factory.processor;

import com.google.common.collect.Queues;
import lombok.Data;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Data
public class DurationQueue<T> {
    private final Queue<T> queue = Queues.newLinkedBlockingQueue();

    /**
     * duration for each queue size
     */
    private final Map<Integer, Duration> queueSizeDuration = new HashMap<>();

    private Duration lastModifiedTime = Duration.ZERO;

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void put(Duration currentTime, T element) {
        updateModify(currentTime);
        queue.add(element);
    }

    public T get(Duration currentTime) {
        updateModify(currentTime);
        return queue.remove();
    }

    private void updateModify(Duration currentTime) {
        int size = queue.size();
        Duration sizeDuration = queueSizeDuration.getOrDefault(size, Duration.ZERO);
        queueSizeDuration.put(size, sizeDuration.plus(currentTime.minus(lastModifiedTime)));

        lastModifiedTime = currentTime;
    }
}

