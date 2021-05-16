package de.hsrm.mi.web.projekt.validierung;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD }) // Worauf ist Annotation anwendbar?
@Retention(RetentionPolicy.RUNTIME) // zur Laufzeit vorhanden
// Verweis auf Validator-Implementierung
@Constraint(validatedBy = SiebzehnValidator.class)
public @interface Siebzehnhaft {
    String message() default "Eingabe enthält weder 17 noch siebzehn";

    // optionale Zusatzinfos
    Class<? extends Payload>[] payload() default {};

    // Zuordnung zu Validierungsgruppen?
    Class<?>[] groups() default {};
}
