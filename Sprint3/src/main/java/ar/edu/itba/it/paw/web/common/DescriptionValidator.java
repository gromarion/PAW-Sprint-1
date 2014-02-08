package ar.edu.itba.it.paw.web.common;

import org.apache.wicket.validation.CompoundValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class DescriptionValidator extends CompoundValidator<String> {

	private static final long serialVersionUID = 1L;
	private static final int MIN_LENGTH = 1;
	private static final int MAX_LENGTH = 140;

	public DescriptionValidator() {
		add(StringValidator.lengthBetween(MIN_LENGTH, MAX_LENGTH));
	}
}