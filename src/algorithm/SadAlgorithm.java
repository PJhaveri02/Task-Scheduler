package algorithm;

import java.util.List;

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

}
