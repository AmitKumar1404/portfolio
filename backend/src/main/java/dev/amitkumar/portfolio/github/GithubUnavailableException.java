package dev.amitkumar.portfolio.github;

public class GithubUnavailableException extends RuntimeException {

    public GithubUnavailableException() {
        super("GitHub activity is temporarily unavailable");
    }
}
