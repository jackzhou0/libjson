package org.chinagcd.libjson.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Relation {
    AND("and"), OR("or");
    private String booleanOperator;
}