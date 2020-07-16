package com.srm.model.academic;

import com.srm.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThresholdList extends BaseEntity {

    private List<Threshold> thresholdList;
}
