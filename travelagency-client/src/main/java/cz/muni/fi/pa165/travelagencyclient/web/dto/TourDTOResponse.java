/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagencyclient.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David Simansky <d.simansky@gmail.com>
 */
public class TourDTOResponse {

    private TourDTO tourDTO;
    private List<TourDTO> listData = new ArrayList<>();

    public TourDTO getTourDTO() {
        return tourDTO;
    }

    public void setTourDTO(TourDTO tourDTO) {
        this.tourDTO = tourDTO;
    }

    public List<TourDTO> getListData() {
        return listData;
    }

    public void setListData(List<TourDTO> listData) {
        this.listData = listData;
    }
}
