package kwee.library;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ApplicationMessages {
  // Load the resource bundle from the "translations" subfolder
  private String m_baseName = "translations/messages";
  private Map<String, Locale> m_availableLanguages = new HashMap<String, Locale>();

  private ResourceBundle m_bundle;
  private Locale m_locale;

  // Private constructor to prevent instantiation from other classes
  private ApplicationMessages() {
    // java.util.MissingResourceException
    m_locale = Locale.getDefault();
    m_bundle = ResourceBundle.getBundle(m_baseName, m_locale);
    availableLanguages();
  }

  // Private static inner class that holds the Singleton instance
  private static class SingletonHelper {
    private static final ApplicationMessages INSTANCE = new ApplicationMessages();
  }

  // Public method to provide access to the Singleton instance
  public static ApplicationMessages getInstance() {
    return SingletonHelper.INSTANCE;
  }

  // Other methods and fields can be added as needed
  public Locale getLocale() {
    return m_locale;
  }

  public void changeLanguage(String languageCode) {
    if (m_availableLanguages.get(languageCode) != null) {
      Locale newLocale = new Locale(languageCode);
      setLocale(newLocale);
    }
  }

  public Set<String> getTranslations() {
    return m_availableLanguages.keySet();
  }

  public String getLanguageName(String languageCode) {
    String LanguageName = "";
    if (m_availableLanguages.get(languageCode) != null) {
      Locale newLocale = new Locale(languageCode);
      LanguageName = newLocale.getDisplayLanguage(newLocale);
    }
    return LanguageName;
  }

  public String getMessage(String a_MsgId) {
    String messageTemplate = m_bundle.getString(a_MsgId);
    return messageTemplate;
  }

  public String getMessage(String a_MsgId, String a_arg1) {
    // Retrieve the message from the bundle
    String messageTemplate = m_bundle.getString(a_MsgId);
    String formattedMessage = MessageFormat.format(messageTemplate, a_arg1);
    return formattedMessage;
  }

  public String getMessage(String a_MsgId, LocalDate a_arg1) {
    // Retrieve the message from the bundle
    String messageTemplate = m_bundle.getString(a_MsgId);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM YYYY", m_locale);
    String formattedDate = a_arg1.format(formatter);
    String formattedMessage = MessageFormat.format(messageTemplate, formattedDate);
    return formattedMessage;
  }

  public String getMessage(String a_MsgId, int a_arg1) {
    // Retrieve the message from the bundle
    String messageTemplate = m_bundle.getString(a_MsgId);
    String formattedMessage = MessageFormat.format(messageTemplate, a_arg1);
    return formattedMessage;
  }

  public String getMessage(String a_MsgId, int a_arg1, int a_arg2) {
    // Retrieve the message from the bundle
    String messageTemplate = m_bundle.getString(a_MsgId);
    String formattedMessage = MessageFormat.format(messageTemplate, a_arg1, a_arg2);
    return formattedMessage;
  }

  public String getMessage(String a_MsgId, int a_arg1, int a_arg2, int a_arg3) {
    // Retrieve the message from the bundle
    String messageTemplate = m_bundle.getString(a_MsgId);
    String formattedMessage = MessageFormat.format(messageTemplate, a_arg1, a_arg2, a_arg3);
    return formattedMessage;
  }

  public String getMessage(String a_MsgId, String a_arg1, String a_arg2) {
    // Retrieve the message from the bundle
    String messageTemplate = m_bundle.getString(a_MsgId);
    String formattedMessage = MessageFormat.format(messageTemplate, a_arg1, a_arg2);
    return formattedMessage;
  }

  public String getMessage(String a_MsgId, String a_arg1, int a_arg2) {
    // Retrieve the message from the bundle
    String messageTemplate = m_bundle.getString(a_MsgId);
    String formattedMessage = MessageFormat.format(messageTemplate, a_arg1, a_arg2);
    return formattedMessage;
  }

  public String getMessage(String a_MsgId, String a_arg1, String a_arg2, String a_arg3) {
    // Retrieve the message from the bundle
    String messageTemplate = m_bundle.getString(a_MsgId);
    String formattedMessage = MessageFormat.format(messageTemplate, a_arg1, a_arg2, a_arg3);
    return formattedMessage;
  }

  // Privates
  private void setLocale(Locale locale) {
    m_bundle = ResourceBundle.getBundle("translations/messages", locale);
    Locale.setDefault(locale);
  }

  private void availableLanguages() {
    // Get an array of available locales
    Locale[] availableLocales = Locale.getAvailableLocales();

    // Iterate through available locales and check for resource bundles
    for (Locale locale : availableLocales) {
      ResourceBundle bundle = ResourceBundle.getBundle(m_baseName, locale);
      if (bundle.getLocale().equals(locale)) {
        m_availableLanguages.put(locale.getLanguage(), locale);
      }
    }
  }

}
