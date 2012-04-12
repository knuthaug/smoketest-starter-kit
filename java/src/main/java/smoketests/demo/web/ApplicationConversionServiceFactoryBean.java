package smoketests.demo.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import smoketests.demo.domain.Person;

/**
 * A central place to register application converters and formatters. 
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(new PersonConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }

	static class PersonConverter implements Converter<Person, String> {
        public String convert(Person person) {
            return new StringBuilder().append(person.getFirstName()).append(" ").append(person.getLastName()).append(" ").append(person.getBirthYear()).toString();
        }
        
    }
}
