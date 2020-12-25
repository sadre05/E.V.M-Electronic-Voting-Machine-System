/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evmapplication;

/**
 *
 * @author mdsad
 */
public class condidate {

    private int id;
    private String condidateName, candidateParty, condidateRating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCondidateName() {
        return condidateName;
    }

    public void setCondidateName(String condidateName) {
        this.condidateName = condidateName;
    }

    public String getCandidateParty() {
        return candidateParty;
    }

    public void setCandidateParty(String candidateParty) {
        this.candidateParty = candidateParty;
    }

    public String getCondidateRating() {
        return condidateRating;
    }

    public void setCondidateRating(String condidateRating) {
        this.condidateRating = condidateRating;
    }

  

   

    public condidate(int id, String condidate_name, String candidate_party, String condidate_rating) {
        this.id = id;
        this.condidateName = condidate_name;
        this.candidateParty = candidate_party;
        this.condidateRating = condidate_rating;
    }

}
