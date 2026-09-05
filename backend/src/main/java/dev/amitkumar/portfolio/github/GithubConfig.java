package dev.amitkumar.portfolio.github;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(GithubProperties.class)
class GithubConfig {

    @Bean
    GithubClient githubClient(GithubProperties properties) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(properties.connectTimeout());
        factory.setReadTimeout(properties.readTimeout());
        RestClient restClient = RestClient.builder()
                .baseUrl(properties.apiBaseUrl())
                .requestFactory(factory)
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
                .defaultHeader(HttpHeaders.USER_AGENT, "amitkumar-portfolio-api")
                .build();
        return new GithubClient(properties, restClient);
    }
}
