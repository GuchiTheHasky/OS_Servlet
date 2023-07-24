package the.husky.security;

import the.husky.entity.user.User;
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
     * Generates a security token based on the provided password.
     * The security token can be used for various security-related operations,
     * such as authentication or authorization.
     *
     * @param password The user's password.
     * @return The generated security token.
     */
    String generateToken(String password);
}
