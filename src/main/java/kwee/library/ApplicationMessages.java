package kwee.library;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;

import kwee.logger.MyLogger;

import java.util.logging.Level;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ApplicationMessages {
  // Load the resource bundle from the "translations" subfolder
  private static final Logger LOGGER = MyLogger.getLogger();

  private String m_baseName = "translations/app_messages";
  private String m_baseNameBck = "translations/messages";

  private Map<String, Locale> m_availableLanguages = new HashMap<String, Locale>();

  private ResourceBundle m_bundle;
  private Locale m_locale;

  // Private constructor to prevent instantiation from other classes
  private ApplicationMessages() {
    // java.util.MissingResourceException
    try {
      m_locale = Locale.getDefault();
      m_bundle = ResourceBundle.getBundle(m_baseName, m_locale);
      availableLanguages();
    } catch (Exception e) {
      m_locale = Locale.getDefault();
      m_bundle = ResourceBundle.getBundle(m_baseNameBck, m_locale);
      m_baseName = m_baseNameBck;
    }
    LOGGER.info("kwee.lib messages Locale: " + m_locale);
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
      Locale newLocale = Locale.of(languageCode);
      setLocale(newLocale);
    }
    LOGGER.info("kwee.lib messages Locale: " + m_locale);
  }

  public Set<String> getTranslations() {
    return m_availableLanguages.keySet();
  }

  public String getLanguageName(String languageCode) {
    String LanguageName = "";
    if (m_availableLanguages.get(languageCode) != null) {
      Locale newLocale = Locale.of(languageCode);
      LanguageName = newLocale.getDisplayLanguage(newLocale);
    }
    return LanguageName;
  }

  /**
   * getMessage format the message
   * 
   * @param a_MsgId Message identification
   * @param args    Arguments
   * @return Formatted message
   */
  public String getMessage(String a_MsgId, Object... args) {
    // Retrieve the message from the bundle
    String messageTemplate = m_bundle.getString(a_MsgId);

    // Optional validation: control number arguments
    int expectedArgs = countPlaceholders(messageTemplate);
    if (args.length != expectedArgs) {
      throw new IllegalArgumentException(
          String.format("Message %s expeted %d arguments, but provided %d", a_MsgId, expectedArgs, args.length));
    }

    String formattedMessage = MessageFormat.format(messageTemplate, args);
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

  /**
   * Helper count the placeholders
   * 
   * @param template Template String
   * @return number of placeholders
   */
  private int countPlaceholders(String template) {
    // Simple implementation -count the {} placeholders
    int count = 0;

    for (int i = 0; i < template.length(); i++) {
      if (template.charAt(i) == '{' && i + 1 < template.length() && Character.isDigit(template.charAt(i + 1))) {
        count++;
      }
    }
    return count;
  }

  public void dumpBundle() {
    Enumeration<String> keys = m_bundle.getKeys();
    Collections.list(keys).stream().forEach(key -> {
      String messageTemplate = m_bundle.getString(key);
      LOGGER.log(Level.INFO, "Key:" + key + " Message: " + messageTemplate);
    });
  }

  // Privates
  private void setLocale(Locale locale) {
    m_bundle = ResourceBundle.getBundle(m_baseName, locale);
    m_locale = locale;
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
