/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evmapplication;

import dataBaseActivity.CurdOprationAgainstDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.sound.midi.SysexMessage;
import showAlerts.GetMessegeAsAlert;

/**
 *
 * @author mdsad
 */
public class FXMLDocumentController implements Initializable {

    public int idMatcher = 0;
    public static String dataAboutCandidate;
    

     @FXML
    private TextField Eirst_WinnerID;

    @FXML
    private TextField Second_WinnerID;
    
    @FXML
    private Button CantVote;

    @FXML
    private Button CastVote;
    @FXML
    private TextField getStudentId;

    @FXML
    private ComboBox<String> ListOfUmidwar;
    ArrayList<String> listOfCandidates = new ArrayList<>();

    @FXML
    private TableView<condidate> tabale_view;

    @FXML
    private TableColumn<condidate, Integer> candidate_id;

    @FXML
    private TableColumn<condidate, String> candidate_name;

    @FXML
    private TableColumn<condidate, String> candidate_party;

    @FXML
    private TableColumn<condidate, String> candidate_rating;

    ObservableList<condidate> listM;
    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    void ActionOnVoteSummition(ActionEvent event) throws SQLException {
        int totalNow = 1;
        String stdId = getStudentId.getText();
        String selectedCandidate = ListOfUmidwar.getValue();
        String dataOfVotedCandidate[] = selectedCandidate.split(",");
        System.out.println("to this candidate you have casted you vote:" + dataOfVotedCandidate[0] + " id:" + dataOfVotedCandidate[1]);
        int countFromDataBase = CurdOprationAgainstDatabase.selectDataFromDatabase("select vote_count from condidate_info where candidate_id=" + dataOfVotedCandidate[1]);

        totalNow = totalNow + countFromDataBase;
        System.err.println(totalNow + "  wefrwwe " + countFromDataBase);
        CurdOprationAgainstDatabase.updateDataToDatabase("update condidate_info set vote_count=" + totalNow + " where candidate_id=" + dataOfVotedCandidate[1]);
        CurdOprationAgainstDatabase.updateDataToDatabase("update voter_info set isVoted=false where voter_id=" + stdId);
        insertVotedCondidateDetails();

        System.out.println("88888888:::" + totalNow);

        ListOfUmidwar.setDisable(true);
        CastVote.setDisable(true);
        CantVote.setDisable(true);
        getStudentId.clear();
        ListOfUmidwar.setValue("SELECT YOUR CANDIDATE");

        GetMessegeAsAlert.getThanksMessageAfterVoteToCondidate(selectedCandidate);

    }

    @FXML
    void ActionOnCantVote(ActionEvent event) {
        ListOfUmidwar.setDisable(true);
        CastVote.setDisable(true);
        CantVote.setDisable(true);
    }

    @FXML
    void ActionOnValidateStudent(ActionEvent event) throws SQLException {
        Connection connection = MySQL_Coonection.ConnectDb();
        String studetIdTovalidate = getStudentId.getText();
        System.err.println("studetIdTovalidate" + studetIdTovalidate);
        PreparedStatement ps = connection.prepareStatement("select * from voter_info where voter_id='" + studetIdTovalidate.trim() + "\'");
        ResultSet rs = ps.executeQuery();
        ResultSet rs1 = rs;
        String data = null;
        while (rs1.next()) {
            idMatcher = rs1.getInt("voter_id");
            data = "ID: " + rs.getInt(1) + " NAME: " + rs.getString("voter_name") + " DEPT: " + rs.getString("votr_dpt") + " DEPT: " + rs.getString("voter_gender");
           dataAboutCandidate=rs.getInt(1) + "," + rs.getString("voter_name") + "," + rs.getString("votr_dpt") + "," + rs.getString("voter_gender");
            System.err.println("idMatcher" + idMatcher);
        }

        if ((idMatcher == Long.valueOf(studetIdTovalidate)) && isVoted()) {
            ListOfUmidwar.setDisable(false);
            CastVote.setDisable(false);
            CantVote.setDisable(false);
            GetMessegeAsAlert.getCondidateDetails(data);
        } else {

            GetMessegeAsAlert.getCondidatesNotFoundWarning("Sorry, you can't cast you vote. may be you have voted");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ListOfUmidwar.setDisable(true);
        CastVote.setDisable(true);
        CantVote.setDisable(true);
        candidate_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        candidate_name.setCellValueFactory(new PropertyValueFactory<>("condidateName"));
        candidate_party.setCellValueFactory(new PropertyValueFactory<>("candidateParty"));
        candidate_rating.setCellValueFactory(new PropertyValueFactory<>("condidateRating"));
        try {
            // TODO
            listM = MySQL_Coonection.getDatacondidate();
            System.out.println("ListOfData:" + listM.get(1).getCondidateName());
            for (condidate data : listM) {
                listOfCandidates.add(data.getCondidateName() + "," + data.getId());
            }
            ListOfUmidwar.getItems().addAll(listOfCandidates);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabale_view.setItems(listM);

    }

    private boolean isVoted() throws SQLException {
        boolean isCondidateVoted = false;
        Connection connection = MySQL_Coonection.ConnectDb();
        String studetIdTovalidate = getStudentId.getText();
        System.err.println("studetIdTovalidate" + studetIdTovalidate);
        PreparedStatement ps = connection.prepareStatement("select isVoted from voter_info where voter_id='" + studetIdTovalidate.trim() + "\'");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            isCondidateVoted = rs.getBoolean("isVoted");

        }
        return isCondidateVoted;
    }

    private  void insertVotedCondidateDetails() throws SQLException {
     String selectedCandidate = ListOfUmidwar.getValue();
     String umidwarData[]=selectedCandidate.split(",");
     String nameToWhomeIVoted=umidwarData[0];
     String idToWhomeIVoted=umidwarData[1];
     //id,name,dept,gender
     String detailsOfVoter[]=dataAboutCandidate.split(",");
     System.out.println("votedUmidwar: "+nameToWhomeIVoted+" votedUmidwarId:"+idToWhomeIVoted+" voteId:"+detailsOfVoter[0]+" voterName:"+detailsOfVoter[1]
     +" voterDept:"+detailsOfVoter[2]+" voterGender:"+detailsOfVoter[3]);
     CurdOprationAgainstDatabase.insertDataToDatabase("insert into votedCandidateDetails(votedCandidate,votedCandidateId,voterId,voterName,voterDept,voterGender) "
             +" values('"+nameToWhomeIVoted+"','"+idToWhomeIVoted+"','"+detailsOfVoter[0]+"','"+detailsOfVoter[1]+"','"+detailsOfVoter[2]+"','"+detailsOfVoter[3]+"')");
    }
    @FXML
    void ActionOnResultShow(ActionEvent event) throws SQLException {
        String FirstWinnerName=null;
        String SecondWinnerName=null;
        Connection connection = MySQL_Coonection.ConnectDb();
      
        PreparedStatement ps = connection.prepareStatement("select candidate_name from condidate_info where vote_count= (select max(vote_count) from condidate_info)");
        ResultSet rs = ps.executeQuery();
        
        PreparedStatement ps2 = connection.prepareStatement("select candidate_name from condidate_info where vote_count= (select max(vote_count) from condidate_info where vote_count < (select max(vote_count) from condidate_info))" );
        ResultSet rs2 = ps2.executeQuery();
        while(rs.next())
        {
            FirstWinnerName=rs.getString("candidate_name");
            
        }
        Eirst_WinnerID.setText("1ST WINNER:"+FirstWinnerName);
        while(rs2.next())
        {
            SecondWinnerName=rs2.getString("candidate_name");
            
        }
        Second_WinnerID.setText("2ND WINNER:"+SecondWinnerName);
        
        
        

    }

}
