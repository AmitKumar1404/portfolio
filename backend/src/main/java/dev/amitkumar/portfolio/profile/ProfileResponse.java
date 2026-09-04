package dev.amitkumar.portfolio.profile;

public record ProfileResponse(
        String name,
        String headline,
        String shortBio,
        String longBio,
        String location,
        String email,
        String githubUrl,
        String linkedinUrl,
        String websiteUrl,
        String resumeUrl,
        String avatarUrl,
        Availability availability) {

    static ProfileResponse from(Profile profile) {
        return new ProfileResponse(
                profile.getName(),
                profile.getHeadline(),
                profile.getShortBio(),
                profile.getLongBio(),
                profile.getLocation(),
                profile.getEmail(),
                profile.getGithubUrl(),
                profile.getLinkedinUrl(),
                profile.getWebsiteUrl(),
                profile.getResumeUrl(),
                profile.getAvatarUrl(),
                profile.getAvailability());
    }
}
