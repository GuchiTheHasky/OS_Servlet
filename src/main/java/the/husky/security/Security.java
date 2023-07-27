package the.husky.security;

import the.husky.security.entity.Credentials;
import the.husky.security.entity.Session;

/**
 * The `Security` interface defines methods for managing security-related operations,
 * such as creating a session and generating a token.
 */

public interface Security {
    /**
     * Creates a session based on the provided credentials.
     * The session represents an authenticated user's session and contains relevant information.
     *
     * @param credentials The user's credentials for authentication.
     * @return The created session.
     */
    Session createSession(Credentials credentials);

    /**
     * Creates a guest session.
     * The guest session represents an unauthenticated user's session and may have limited access.
     * It is typically used to provide temporary access or partial functionality for guests.
     *
     * @return The created guest session.
     */
    Session createGuestSession();

    /**
     * Generates a security token based on the provided password.
     * The security token can be used for various security-related operations,
     * such as authentication or authorization.
     *
     * @param password The user's password.
     * @return The generated security token.
     */
    String generateToken(String password);
}
