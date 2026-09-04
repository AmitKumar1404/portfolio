package dev.amitkumar.portfolio.profile;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.amitkumar.portfolio.common.exception.ResourceNotFoundException;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getProfile() {
        Profile profile = profileRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new ResourceNotFoundException("PROFILE_NOT_FOUND", "Profile not found"));
        return ProfileResponse.from(profile);
    }
}
