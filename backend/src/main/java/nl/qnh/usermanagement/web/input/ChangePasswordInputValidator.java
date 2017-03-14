package nl.qnh.usermanagement.web.input;

import org.apache.commons.lang3.StringUtils;
import org.hawaiiframework.validation.ValidationResult;
import org.hawaiiframework.validation.Validator;
import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 */
@Component
public class ChangePasswordInputValidator implements Validator<ChangePasswordInput> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final ChangePasswordInput input, final ValidationResult validationResult) {
        if (StringUtils.isBlank(input.getCurrentPassword())) {
            validationResult.rejectValue("current_password", "required");
        }
        if (StringUtils.isBlank(input.getNewPassword())) {
            validationResult.rejectValue("new_password", "required");
        }
    }
}
