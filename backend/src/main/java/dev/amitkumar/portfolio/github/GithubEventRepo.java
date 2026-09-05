package dev.amitkumar.portfolio.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
record GithubEventRepo(String name, String url) {
}
