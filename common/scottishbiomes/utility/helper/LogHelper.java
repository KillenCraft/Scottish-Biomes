
package scottishbiomes.utility.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import scottishbiomes.Loader;

import com.google.common.base.Optional;

/**
 * Helper methods for output to the logger
 */
public enum LogHelper
{
    INSTANCE;

    /**
     * <pre>
     * static void fine({@link String} format,
     *                  {@link Object}... args);
     * </pre>
     * 
     * Write tracing information to the log at level <code>FINE</code>.
     * <p>
     * 
     * @param format
     *            - A format string
     * @param args
     *            - Arguments referenced by the format specifiers in the
     *            format string
     * @see java.util.logging.Level#FINE Level.FINE
     * @see java.util.Formatter Formatter
     */
    public static void fine(final String format, final Object... args)
    {
        INSTANCE.log(Level.FINE, format, args);
    }

    /**
     * <pre>
     * static void finer({@link String} format,
     *                   {@link Object}... args);
     * </pre>
     * 
     * Write tracing information to the log at level <code>FINER</code>.
     * <p>
     * 
     * @param format
     *            - A format string
     * @param args
     *            - Arguments referenced by the format specifiers in the
     *            format string
     * @see java.util.logging.Level#FINER Level.FINER
     * @see java.util.Formatter Formatter
     */
    public static void finer(final String format, final Object... args)
    {
        INSTANCE.log(Level.FINER, format, args);
    }

    /**
     * <pre>
     * static void finest({@link String} format,
     *                    {@link Object}... args);
     * </pre>
     * 
     * Write tracing information to the log at level <code>FINEST</code>
     * .
     * <p>
     * 
     * @param format
     *            - A format string
     * @param args
     *            - Arguments referenced by the format specifiers in the
     *            format string
     * @see java.util.logging.Level#FINEST Level.FINEST
     * @see java.util.Formatter Formatter
     */
    public static void finest(final String format, final Object... data)
    {
        INSTANCE.log(Level.FINEST, format, data);
    }

    /**
     * <pre>
     * static void info({@link String} format,
     *                  {@link Object}... args);
     * </pre>
     * 
     * Write an informational message to the console and log.
     * <p>
     * 
     * @param format
     *            - A format string
     * @param args
     *            - Arguments referenced by the format specifiers in the
     *            format string
     * @see java.util.logging.Level#INFO Level.INFO
     * @see java.util.Formatter Formatter
     */
    public static void info(final String format, final Object... args)
    {
        INSTANCE.log(Level.INFO, format, args);
    }

    /**
     * <pre>
     * static void log({@link Level} level,
     *                 {@link Throwable} exception,
     *                 {@link String} format,
     *                 {@link Object}... args);
     * </pre>
     * 
     * Write a message to the log, with associated
     * <code>Throwable</code> information.
     * <p>
     * 
     * @param level
     *            - a {@link java.util.logging.Level log level}
     * @param exception
     *            - a {@link Throwable} error or exception
     * @param format
     *            - A format string
     * @param args
     *            - Arguments referenced by the format specifiers in the
     *            format string
     * @see java.util.Formatter Formatter
     */
    public static void log(final Level level, final Throwable exception, final String format,
            final Object... args)
    {
        INSTANCE.getLogger().log(level, String.format(format, args), exception);
    }

    /**
     * <pre>
     * static void severe({@link String} format,
     *                    {@link Object}... args);
     * </pre>
     * 
     * Write message indicating a severe failure to the log.
     * <p>
     * 
     * @param format
     *            - A format string
     * @param args
     *            - Arguments referenced by the format specifiers in the
     *            format string
     * @see java.util.logging.Level#SEVERE Level.SEVERE
     * @see java.util.Formatter Formatter
     */
    public static void severe(final String format, final Object... args)
    {
        INSTANCE.log(Level.SEVERE, format, args);
    }

    /**
     * <pre>
     * static void warning({@link String} format,
     *                     {@link Object}... args);
     * </pre>
     * 
     * Write message indicating a potential problem to the log.
     * <p>
     * 
     * @param format
     *            - A format string
     * @param args
     *            - Arguments referenced by the format specifiers in the
     *            format string
     * @see java.util.logging.Level#WARNING Level.WARNING
     * @see java.util.Formatter Formatter
     */
    public static void warning(final String format, final Object... args)
    {
        INSTANCE.log(Level.WARNING, format, args);
    }

    private Optional<Logger> logger = Optional.absent();

    private Logger getLogger()
    {
        if (!logger.isPresent())
        {
            init();
        }

        return logger.get();
    }

    private void init()
    {
        if (logger.isPresent()) return;

        logger = Optional.of(Logger.getLogger("SimpleBiomes"));
        logger.get().setParent(Loader.proxy.getFMLLogger());
    }

    private void log(final Level level, final String format, final Object... data)
    {
        getLogger().log(level, String.format(format, data));
    }
}
