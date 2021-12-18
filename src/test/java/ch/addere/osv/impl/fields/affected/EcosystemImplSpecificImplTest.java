package ch.addere.osv.impl.fields.affected;

import static ch.addere.osv.impl.fields.affected.EcosystemSpecificImpl.ECOSYSTEM_SPECIFIC_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.affected.EcosystemSpecific;
import org.junit.jupiter.api.Test;

class EcosystemImplSpecificImplTest {

  private static final String ECOSYSTEM_SPECIFIC1 = "someEcosystemSpecificValues";
  private static final String ECOSYSTEM_SPECIFIC2 =
      "{\"functions\":[\"http::header::HeaderMap::reserve\"],"
          + "\"keywords\":[\"http\",\"integer-overflow\",\"DoS\"],"
          + "\"categories\":[\"denial-of-service\"],\"severity\":\"HIGH\"}";

  @Test
  void testJsonKey() {
    assertThat(ECOSYSTEM_SPECIFIC_KEY)
        .isEqualTo("ecosystem_specific");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> EcosystemSpecificImpl.of(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument ecosystem specific must not be null");
  }

  @Test
  void testValidEcosystemSpecific() {
    EcosystemSpecific ecosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC1);
    assertThat(ecosystemSpecific.value()).isEqualTo(ECOSYSTEM_SPECIFIC1);
  }

  @Test
  void testEquality() {
    EcosystemSpecific ecosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC1);
    EcosystemSpecific otherEcosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC1);
    assertThat(ecosystemSpecific).isEqualTo(otherEcosystemSpecific);
  }

  @Test
  void testNonEquality() {
    EcosystemSpecific ecosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC1);
    EcosystemSpecific otherEcosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC2);
    assertThat(ecosystemSpecific).isNotEqualTo(otherEcosystemSpecific);
  }

  @Test
  void testHashCode() {
    EcosystemSpecific ecosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC1);
    EcosystemSpecific otherEcosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC1);
    assertThat(ecosystemSpecific).hasSameHashCodeAs(otherEcosystemSpecific);
  }

  @Test
  void testToString() {
    EcosystemSpecific ecosystemSpecific = EcosystemSpecificImpl.of(ECOSYSTEM_SPECIFIC2);
    assertThat(ecosystemSpecific).hasToString(ECOSYSTEM_SPECIFIC_KEY + ": " + ECOSYSTEM_SPECIFIC2);
  }
}
