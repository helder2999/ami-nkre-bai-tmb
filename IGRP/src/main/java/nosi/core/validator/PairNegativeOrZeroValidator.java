package nosi.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nosi.core.gui.components.IGRPSeparatorList.Pair;
import nosi.core.validator.constraints.PairNegativeOrZero;
import nosi.core.webapp.Core;

/**
 * emerson
 * 26/07/2019
 */
public class PairNegativeOrZeroValidator implements ConstraintValidator<PairNegativeOrZero, Pair>{

	@Override
	public void initialize(PairNegativeOrZero constraintAnnotation) {
		
	}
	
	@Override
	public boolean isValid(Pair pair, ConstraintValidatorContext context) {
		if(pair!=null && Core.isNotNull(pair.getKey())) {
			return Validation.validateNegativeOrZero(pair.getKey());
		}
		return true;
	}

}
 