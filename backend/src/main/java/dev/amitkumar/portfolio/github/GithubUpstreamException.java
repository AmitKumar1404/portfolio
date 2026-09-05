package dev.amitkumar.portfolio.github;

class GithubUpstreamException extends RuntimeException {

    GithubUpstreamException(String message) {
        super(message);
    }

    GithubUpstreamException(String message, Throwable cause) {
        super(message, cause);
    }
}
