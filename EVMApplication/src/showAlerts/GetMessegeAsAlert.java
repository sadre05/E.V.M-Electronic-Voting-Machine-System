/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package showAlerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author mdsad
 */
public class GetMessegeAsAlert {

    public static void getCondidatesNotFoundWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("YOU ARE NOT ELIGIBLE CANDIDATE:");
        alert.showAndWait();
    }

    public static void getCondidateDetails(String condidateDetails) {
        System.err.println("Data :"+condidateDetails);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, condidateDetails, ButtonType.APPLY);
        alert.setTitle("CANDIDATE DETAILS:");
        alert.showAndWait();

    }
     public static void getThanksMessageAfterVoteToCondidate(String CandidateName) {
       
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "This for voting to "+CandidateName, ButtonType.FINISH);
        alert.setTitle("VOTE CASTED SUCCESSFULLY:");
        alert.showAndWait();

    }

}
