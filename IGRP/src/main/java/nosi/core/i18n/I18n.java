package nosi.core.i18n;

/**
 * Marcel Iekiny
 * Oct 27, 2017
 */
import java.util.ResourceBundle;
import gnu.gettext.GettextResource;
import org.apache.commons.lang3.StringUtils;
import java.io.Serializable;
import java.text.MessageFormat;

public final class I18n implements Serializable {

	private static final long serialVersionUID = 1788683894002641641L;
	
	private ResourceBundle bundle;

	public I18n(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public final String t(String text) {
		if (bundle == null)
			return text;
		String result = GettextResource.gettext(bundle, StringUtils.stripAccents(text));
//		If the result is the normalize text, 
//		    return the result of text with special character
		if (result.equals(StringUtils.stripAccents(text)))			
			return GettextResource.gettext(bundle, text);		
		return result;
	}

	public final String t(String text, Object... objects) {
		String result = MessageFormat.format(this.t(text), objects);
		return result;
	}

	public final String t(String text, String pluralText, long n) {
		if (bundle == null)
			return text;
		String result = GettextResource.ngettext(bundle, text, pluralText, n);
		return result;
	}

	public final String t(String text, String pluralText, long n, Object... objects) {
		String result = MessageFormat.format(this.t(text, pluralText, n), objects);
		return result;
	}

	public ResourceBundle getBundle() {
		return this.bundle;
	}

}
