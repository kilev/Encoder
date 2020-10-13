package com.kil.factory.processor;

import com.kil.part.Part;
import com.kil.stream.RandomDurationStream;
import lombok.Data;

import java.time.Duration;

@Data
public class ProcessorSlot {
    private final RandomDurationStream randomDurationStream;

    private Part processingPart;

    /**
     * @return time of processing complete
     */
    public Duration putPart(Duration currentTime, Part part) {
        assert processingPart == null;

        processingPart = part;
        return currentTime.plus(randomDurationStream.getNext());
    }

    public boolean isEmpty() {
        return processingPart == null;
    }
}
