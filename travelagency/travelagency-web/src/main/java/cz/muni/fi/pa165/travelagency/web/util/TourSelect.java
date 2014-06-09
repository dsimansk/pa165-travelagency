package cz.muni.fi.pa165.travelagency.web.util;

import java.util.List;

/**
 *
 * @author Michal Jurc
 */
public class TourSelect {
    
    List<Long> selectedTourIds;

    public List<Long> getSelectedTourIds() {
        return selectedTourIds;
    }

    public void setSelectedTourIds(List<Long> selectedTourIds) {
        this.selectedTourIds = selectedTourIds;
    }
    
}
