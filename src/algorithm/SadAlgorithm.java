package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SadAlgorithm implements algorithm {
    @Override
    public List<Processor> execute() {
        //Model decent = generateDecentShortModel()
        //return algrec(decent,blankstep(),baseModel);


        return null;
    }

    /**
     * model algrec(model shortmodel, step s, model m){
     * m.do(s);
     * if (m.len()<=shortmodel.len()){
     *   List<Step> steps = m.getavailablesteps();
     *   if steps.length = 0){
     *      return m
     *   }
     *   for(Step step: steps){
     *   Model model = algrec(shortmodel,s,m.clone());
     *   if(model.len()<=len){
     *   shortmodel = model;
     *   }
     *   }
     * }
     * return shortmodel();
     * }
     *
     *
     *
     **/

    /**
     * generateDecentShortModel(){
     *     use wong's greedy method to get a decent initial model.
     * }
     */

    /**
     * Method to crate a new partial schedule with the node assigning to the processor.
     * @param currentSchedule
     * @param processor
     * @param node
     * @return
     */
    public PartialSchedule createPartialSchedule(Schedule currentSchedule, Processor processor, Node node) {
        Map<Processor, List<String>> currentPToN = currentSchedule.getProcessorToScheduledNodes();
        List<String> scheduleNodeInProc = currentPToN.get(processor);
        int lengthOfSchNode = scheduleNodeInProc.size();
        String nodeName = node.getName();
        List<String> schPToN = new ArrayList<String>(scheduleNodeInProc);
        schPToN.add(nodeName);

        Map<Processor, List<String>> newPToN = new HashMap<Processor, List<String>>(currentPToN);
        newPToN.put(processor, schPToN);
        List<Node> newScheduledNodes = new ArrayList<Node>(currentSchedule.getAllScheduledNodes());

        return new PartialSchedule(newPToN, newScheduledNodes);
    }




}
