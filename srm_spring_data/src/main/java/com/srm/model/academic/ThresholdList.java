package com.srm.model.academic;

import com.srm.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class ThresholdList extends BaseEntity {

    @Builder
    public ThresholdList(Long id, Set<Threshold> thresholds) {
        super(id);
        this.thresholds = thresholds;
    }

    @JoinTable(name = "thresholdList_threshold",
            joinColumns = @JoinColumn(name = "thresholdlist_id"), inverseJoinColumns = @JoinColumn(name = "threshold_id"))
    @ManyToMany
    private Set<Threshold> thresholds = new HashSet<>();
}
