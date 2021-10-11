package com.myself.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class ListValidContraintValidtator implements ConstraintValidator<ListValid,Integer> {

    Set<Integer> set=new HashSet<>();

    @Override
    public void initialize(ListValid constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();
        for(Integer val : vals){
            set.add(val);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return set.contains(value);
    }
}
