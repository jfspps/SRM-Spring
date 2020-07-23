package com.srm.model.academic;

import com.srm.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Threshold extends BaseEntity {
    //maps a numerical score with a A*/B-/D+/MERIT/DISTINCTION etc...

    @Builder
    public Threshold(Long id, int numerical, String alphabetical, Set<ThresholdList> thresholdLists) {
        super(id);
        this.numerical = numerical;
        this.alphabetical = alphabetical;
        this.thresholdLists = thresholdLists;
    }

    private int numerical;
    private String alphabetical;

    @ManyToMany(mappedBy = "thresholds")
    private Set<ThresholdList> thresholdLists = new HashSet<>();
}
