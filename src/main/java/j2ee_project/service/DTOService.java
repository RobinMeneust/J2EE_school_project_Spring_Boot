package j2ee_project.service;

import j2ee_project.dto.AddressDTO;
import j2ee_project.dto.ContactDTO;
import j2ee_project.dto.UserDTO;
import j2ee_project.model.Address;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Manages DTO classes
 */
public class DTOService {
	/**
	 * Validate a user data transfer object
	 *
	 * @param userDTO the dto to validate
	 * @return a map with error message
	 */
	public static Map<String, String> userDataValidation(UserDTO userDTO){
		Map<String, String> violationsMap = dataValidation(userDTO);
		if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
			violationsMap.put("confirmPassword", "Password and Confirm Password must match.");
		}
		return violationsMap;
	}


	/**
	 * Validate a contact data transfer object
	 *
	 * @param contactDTO the dto to validate
	 * @return a map with error message
	 */
	public static Map<String, String> contactDataValidation(ContactDTO contactDTO){
		return dataValidation(contactDTO);
	}

	/**
	 * Validate an address data transfer object
	 *
	 * @param addressDTO the address dto
	 * @return a map with error message
	 */
	public static Map<String, String> addressDataValidation(AddressDTO addressDTO){
		return dataValidation(addressDTO);
	}

	/**
	 * Validate an address data transfer object whose type is unknown (E)
	 *
	 * @param objectTested the dto
	 * @return a map with error message
	 * @param <E> Type of the DTO
	 */
	private static<E> Map<String, String> dataValidation(E objectTested) {
		Map<String, String> violationsMap = new HashMap<>();
		try {
			ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
			Validator validator = validatorFactory.getValidator();
			Set<ConstraintViolation<E>> violations = validator.validate(objectTested);
			for (ConstraintViolation<E> violation : violations) {
				violationsMap.put(violation.getPropertyPath().toString(), violation.getMessage());
			}
			validatorFactory.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return violationsMap;
	}
}
