package algorithm;

import java.util.ArrayList;

public interface algorithm {

    /**
     *
     * @param an array list of tasks which has been made from the dot file
     * @return an array list of tasks which now contains start time and processor
     */
    public ArrayList<Tasks> execute(ArrayList<Tasks> theTask );

}
