package ch.addere.osv.impl.fields.affected.ranges;

import static ch.addere.osv.impl.fields.affected.ranges.RepoImpl.REPO_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.fields.affected.ranges.Repo;
import java.net.MalformedURLException;
import org.junit.jupiter.api.Test;

class RepoImplTest {

  private static final String VALID_URL = "https://osv.dev";
  private static final String INVALID_URL = "not-a-valid-url";
  private static final String OTHER_VALID_URL = "https://github.com";

  @Test
  void testJsonKey() {
    assertThat(REPO_KEY).isEqualTo("repo");
  }

  @Test
  void testValidRepoValue() throws MalformedURLException {
    Repo repo = RepoImpl.of(VALID_URL);
    String url = repo.value();
    assertThat(url).isEqualTo(VALID_URL);
  }

  @Test
  void testInvalidRepoValue() {
    assertThatThrownBy(() -> RepoImpl.of(INVALID_URL))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("invalid URL: '" + INVALID_URL + "'");
  }

  @Test
  void testSameness() {
    Repo repo = RepoImpl.of(VALID_URL);
    Repo otherRepo = repo;
    assertThat(repo).isEqualTo(otherRepo);
  }

  @Test
  void testEquality() {
    Repo repo = RepoImpl.of(VALID_URL);
    Repo otherRepo = RepoImpl.of(VALID_URL);
    assertThat(repo).isEqualTo(otherRepo);
  }

  @Test
  void testNonEquality() {
    Repo repo = RepoImpl.of(VALID_URL);
    Repo otherRepo = RepoImpl.of(OTHER_VALID_URL);
    assertThat(repo).isNotEqualTo(otherRepo);
  }

  @Test
  void testHashCode() {
    Repo repo = RepoImpl.of(VALID_URL);
    Repo otherRepo = RepoImpl.of(VALID_URL);
    assertThat(repo.hashCode()).isEqualTo(otherRepo.hashCode());
  }

  @Test
  void testToString() {
    Repo repo = RepoImpl.of(VALID_URL);
    assertThat(repo.toString()).isEqualTo(REPO_KEY + ": " + VALID_URL);
  }
}