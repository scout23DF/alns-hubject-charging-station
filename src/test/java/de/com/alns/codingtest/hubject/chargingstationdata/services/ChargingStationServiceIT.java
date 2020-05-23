package de.com.alns.codingtest.hubject.chargingstationdata.services;

import de.com.alns.codingtest.hubject.chargingstationdata.HubjectChargingStationDataApplication;
import de.com.alns.codingtest.hubject.chargingstationdata.models.ChargingStation;
import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.PointLocationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Integration tests for {@link ChargingStation}.
 */
@SpringBootTest(classes = HubjectChargingStationDataApplication.class)
@Transactional
public class ChargingStationServiceIT {

    private static final String DEFAULT_MEANINGFULL_ID = "DE.BER.D007.S0001";
    private static final String DEFAULT_DESCRIPTION_CHARGING_STATION = "Station N# ";
    private static final Boolean DEFAULT_IS_ACTIVE = true;
    private static final Boolean DEFAULT_IS_AVAILABLE = true;
    private static final Double DEFAULT_PCT_ENERGY_LOADED = 100.0;
    private static final String DEFAULT_ZIPCODE_NUMBER = "12277";
    private static final PointLocationDTO DEFAULT_POINT_LOCATION_DTO = new PointLocationDTO(52.5234350, 13.411442);


    @Autowired
    private IChargingStationService chargingStationService;

    private ChargingStation chargingStationTmp;

    /*
    @BeforeEach
    public void init() {
        chargingStationTmp = new ChargingStation();
        chargingStationTmp.setLogin(DEFAULT_LOGIN);
        chargingStationTmp.setPassword(RandomStringUtils.random(60));
        chargingStationTmp.setActivated(true);
        chargingStationTmp.setEmail(DEFAULT_EMAIL);
        chargingStationTmp.setFirstName(DEFAULT_FIRSTNAME);
        chargingStationTmp.setLastName(DEFAULT_LASTNAME);
        chargingStationTmp.setImageUrl(DEFAULT_IMAGEURL);
        chargingStationTmp.setLangKey(DEFAULT_LANGKEY);

        when(dateTimeProvider.getNow()).thenReturn(Optional.of(LocalDateTime.now()));
        auditingHandler.setDateTimeProvider(dateTimeProvider);
    }

    @Test
    @Transactional
    public void assertThatUserMustExistToResetPassword() {
        userRepository.saveAndFlush(chargingStationTmp);
        Optional<User> maybeUser = userService.requestPasswordReset("invalid.login@localhost");
        assertThat(maybeUser).isNotPresent();

        maybeUser = userService.requestPasswordReset(chargingStationTmp.getEmail());
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.orElse(null).getEmail()).isEqualTo(chargingStationTmp.getEmail());
        assertThat(maybeUser.orElse(null).getResetDate()).isNotNull();
        assertThat(maybeUser.orElse(null).getResetKey()).isNotNull();
    }

    @Test
    @Transactional
    public void assertThatOnlyActivatedUserCanRequestPasswordReset() {
        chargingStationTmp.setActivated(false);
        userRepository.saveAndFlush(chargingStationTmp);

        Optional<User> maybeUser = userService.requestPasswordReset(chargingStationTmp.getLogin());
        assertThat(maybeUser).isNotPresent();
        userRepository.delete(chargingStationTmp);
    }

    @Test
    @Transactional
    public void assertThatResetKeyMustNotBeOlderThan24Hours() {
        Instant daysAgo = Instant.now().minus(25, ChronoUnit.HOURS);
        String resetKey = RandomUtil.generateResetKey();
        chargingStationTmp.setActivated(true);
        chargingStationTmp.setResetDate(daysAgo);
        chargingStationTmp.setResetKey(resetKey);
        userRepository.saveAndFlush(chargingStationTmp);

        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", chargingStationTmp.getResetKey());
        assertThat(maybeUser).isNotPresent();
        userRepository.delete(chargingStationTmp);
    }

    @Test
    @Transactional
    public void assertThatResetKeyMustBeValid() {
        Instant daysAgo = Instant.now().minus(25, ChronoUnit.HOURS);
        chargingStationTmp.setActivated(true);
        chargingStationTmp.setResetDate(daysAgo);
        chargingStationTmp.setResetKey("1234");
        userRepository.saveAndFlush(chargingStationTmp);

        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", chargingStationTmp.getResetKey());
        assertThat(maybeUser).isNotPresent();
        userRepository.delete(chargingStationTmp);
    }

    @Test
    @Transactional
    public void assertThatUserCanResetPassword() {
        String oldPassword = chargingStationTmp.getPassword();
        Instant daysAgo = Instant.now().minus(2, ChronoUnit.HOURS);
        String resetKey = RandomUtil.generateResetKey();
        chargingStationTmp.setActivated(true);
        chargingStationTmp.setResetDate(daysAgo);
        chargingStationTmp.setResetKey(resetKey);
        userRepository.saveAndFlush(chargingStationTmp);

        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", chargingStationTmp.getResetKey());
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.orElse(null).getResetDate()).isNull();
        assertThat(maybeUser.orElse(null).getResetKey()).isNull();
        assertThat(maybeUser.orElse(null).getPassword()).isNotEqualTo(oldPassword);

        userRepository.delete(chargingStationTmp);
    }

    @Test
    @Transactional
    public void assertThatNotActivatedUsersWithNotNullActivationKeyCreatedBefore3DaysAreDeleted() {
        Instant now = Instant.now();
        when(dateTimeProvider.getNow()).thenReturn(Optional.of(now.minus(4, ChronoUnit.DAYS)));
        chargingStationTmp.setActivated(false);
        chargingStationTmp.setActivationKey(RandomStringUtils.random(20));
        User dbUser = userRepository.saveAndFlush(chargingStationTmp);
        dbUser.setCreatedDate(now.minus(4, ChronoUnit.DAYS));
        userRepository.saveAndFlush(chargingStationTmp);
        Instant threeDaysAgo = now.minus(3, ChronoUnit.DAYS);
        List<User> users = userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(threeDaysAgo);
        assertThat(users).isNotEmpty();
        userService.removeNotActivatedUsers();
        users = userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(threeDaysAgo);
        assertThat(users).isEmpty();
    }

    @Test
    @Transactional
    public void assertThatNotActivatedUsersWithNullActivationKeyCreatedBefore3DaysAreNotDeleted() {
        Instant now = Instant.now();
        when(dateTimeProvider.getNow()).thenReturn(Optional.of(now.minus(4, ChronoUnit.DAYS)));
        chargingStationTmp.setActivated(false);
        User dbUser = userRepository.saveAndFlush(chargingStationTmp);
        dbUser.setCreatedDate(now.minus(4, ChronoUnit.DAYS));
        userRepository.saveAndFlush(chargingStationTmp);
        Instant threeDaysAgo = now.minus(3, ChronoUnit.DAYS);
        List<User> users = userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(threeDaysAgo);
        assertThat(users).isEmpty();
        userService.removeNotActivatedUsers();
        Optional<User> maybeDbUser = userRepository.findById(dbUser.getId());
        assertThat(maybeDbUser).contains(dbUser);
    }

    @Test
    @Transactional
    public void assertThatAnonymousUserIsNotGet() {
        chargingStationTmp.setLogin(Constants.ANONYMOUS_USER);
        if (!userRepository.findOneByLogin(Constants.ANONYMOUS_USER).isPresent()) {
            userRepository.saveAndFlush(chargingStationTmp);
        }
        final PageRequest pageable = PageRequest.of(0, (int) userRepository.count());
        final Page<UserDTO> allManagedUsers = userService.getAllManagedUsers(pageable);
        assertThat(allManagedUsers.getContent().stream()
            .noneMatch(user -> Constants.ANONYMOUS_USER.equals(user.getLogin())))
            .isTrue();
    }
    */
}
