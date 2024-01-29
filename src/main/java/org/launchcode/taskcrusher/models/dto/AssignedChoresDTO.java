package org.launchcode.taskcrusher.models.dto;

import org.launchcode.taskcrusher.models.Chore;
import org.launchcode.taskcrusher.models.Kid;

import java.util.List;

public class AssignedChoresDTO {

    private Kid kid;

    private List<Chore> chores;

    public Kid getKid() {
        return kid;
    }

    public void setKid(Kid kid) {
        this.kid = kid;
    }

    public List<Chore> getChores() {
        return chores;
    }

    public void setChores(List<Chore> chores) {
        this.chores = chores;
    }


}
