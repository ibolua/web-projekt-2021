package de.hsrm.mi.web.projekt.validierung;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SiebzehnValidator 
        implements ConstraintValidator<Siebzehnhaft, String> {
    
            
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value.contains("17") || value.toLowerCase().contains("siebzehn");   
    }
    
}
