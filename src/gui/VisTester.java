package gui;

import algorithm.Processor;

import java.util.ArrayList;
import java.util.List;

public class VisTester {

    private ProcGraphController _controller;

    public VisTester(ProcGraphController visController) {
    _controller = visController;
    }


    public void passEmpty(){

        _controller.update(createProcessors(4));
    }

    public List<Processor> createProcessors(int n) {
        List<Processor> processorList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            processorList.add(new Processor(i));
        }
        return processorList;
    }
}
