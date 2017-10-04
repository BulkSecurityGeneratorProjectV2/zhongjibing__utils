package com.icezhg.utils.file;

public class DirectoryCreateFailedException extends RuntimeException {

    private static final long serialVersionUID = 7188266722514663454L;

    /**
     * Constructs a <code>DirectoryCreateFailedException</code> with no detail  message.
     */
    public DirectoryCreateFailedException() {
        super();
    }

    /**
     * Constructs a <code>DirectoryCreateFailedException</code> with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public DirectoryCreateFailedException(String s) {
        super(s);
    }

    /**
     * Creates a <code>DirectoryCreateFailedException</code> with the specified
     * detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is permitted,
     *                and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public DirectoryCreateFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a <code>DirectoryCreateFailedException</code> with the specified cause
     * and a detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is permitted,
     *              and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public DirectoryCreateFailedException(Throwable cause) {
        super(cause);
    }
}
