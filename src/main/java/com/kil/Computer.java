package com.kil;

import com.kil.event.Event;
import com.kil.event.EventHandler;
import com.kil.event.NewPartEvent;
import com.kil.event.StepCompletedEvent;
import com.kil.factory.FactoryModel;
import com.kil.factory.processor.Processor;
import com.kil.part.Part;
import lombok.Data;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Data
public class Computer implements EventHandler {
    private static final int FIRST_MACHINE_INDEX = 0;

    private final FactoryModel factoryModel;

    public FactoryModel compute() {
        createPartInputEvent();

        while (true) {
            Queue<Event> eventTimeLine = factoryModel.getEventTimeLine();
            if (eventTimeLine.isEmpty()) {
                return factoryModel;
            }
            eventTimeLine.remove().handleEvent(this);
        }
    }

    @Override
    public void handleEvent(NewPartEvent event) {
        Processor firstProcessor = factoryModel.getProcessorChain().get(FIRST_MACHINE_INDEX);
        putPartInMachine(event.getPart(), firstProcessor);
        createPartInputEvent();
    }

    @Override
    public void handleEvent(StepCompletedEvent event) {
        Processor usedProcessor = event.getProcessor();

        if (!hasNextMachine(usedProcessor)) {
            return;
        }

        Processor nextProcessor = getNextMachine(usedProcessor);
        putPartInMachine(usedProcessor.getProcessedPart(), nextProcessor);
    }

    private void putPartInMachine(Part part, Processor processor) {
        Optional<Event> partProcessedEvent = processor.handlePart(factoryModel.getCurrentTime(), part);
        partProcessedEvent.ifPresent(this::addEvent);
    }

    private boolean hasNextMachine(Processor processor) {
        List<Processor> processorChain = factoryModel.getProcessorChain();
        int machineIndex = processorChain.indexOf(processor);
        return processorChain.size() == machineIndex + 1;
    }

    private Processor getNextMachine(Processor processor) {
        List<Processor> processorChain = factoryModel.getProcessorChain();
        return processorChain.get(processorChain.indexOf(processor) + 1);
    }

    private void createPartInputEvent() {
        Duration partInputTime = factoryModel.getInputPartsTimeStream().getNextTime();
        addEvent(new NewPartEvent(partInputTime, new Part()));
    }

    private void addEvent(@Nonnull Event event) {
        boolean eventHitInModelingTime = event.getTime().compareTo(factoryModel.getTotalModelingTime()) <= 0;
        if (eventHitInModelingTime) {
            factoryModel.getEventTimeLine().add(event);
        }
    }

}
